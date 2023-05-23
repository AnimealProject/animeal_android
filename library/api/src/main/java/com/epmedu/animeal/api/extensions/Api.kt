package com.epmedu.animeal.api.extensions

import android.util.Log
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.aws.GsonVariablesSerializer
import com.amplifyframework.api.graphql.SimpleGraphQLRequest
import com.amplifyframework.api.graphql.SubscriptionType
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.api.graphql.model.ModelSubscription
import com.amplifyframework.api.rest.RestOptions
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.Model
import com.amplifyframework.core.model.query.predicate.QueryPredicate
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.internal.json.SortedInputFieldMapWriter
import com.epmedu.animeal.api.wrapper.ResponseError
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.extensions.suspendCancellableCoroutine
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume

const val LOG_TAG = "AnimealApi"

/**
 * Invokes GET Http request and deserializes response to given [R] type.
 * On successful response and deserialization, returns [ApiResult.Success],
 * otherwise returns [ApiResult.Failure] with corresponding exception.
 */
suspend inline fun <reified R : Any> RestOptions.get(): ApiResult<R> = suspendCancellableCoroutine {
    val path = this@get.path

    Amplify.API.get(
        this@get,
        { restResponse ->
            resume(
                if (restResponse.code.isSuccessful) {
                    try {
                        val deserializedResponse =
                            Gson().fromJson(restResponse.data.asString(), R::class.java)
                        Log.d(LOG_TAG, "Query $path deserialized response $deserializedResponse")
                        ApiResult.Success(deserializedResponse)
                    } catch (jsonSyntaxException: JsonSyntaxException) {
                        Log.e(LOG_TAG, "Query $path failed with $jsonSyntaxException")
                        ApiResult.Failure(jsonSyntaxException)
                    }
                } else {
                    val responseError = ResponseError(listOf(restResponse.data.asString()))
                    Log.e(LOG_TAG, "Query $path failed with $responseError")
                    ApiResult.Failure(responseError)
                }
            )
        },
        { apiException ->
            Log.e(LOG_TAG, "Query $path failed with $apiException")
            resume(ApiResult.Failure(apiException))
        }
    )
}

/**
 * Creates a query for list of models of type [GraphQLModel] with provided [predicate]
 * and on successful responses returns flow with the list of the models.
 * On failure flow closes.
 * @param predicate predicate for filtering. By default `null`
 */
internal inline fun <reified GraphQLModel : Model> getModelList(
    predicate: QueryPredicate? = null,
): Flow<List<GraphQLModel>> {
    return callbackFlow {
        val graphQLOperation = Amplify.API.query(
            predicate?.let {
                ModelQuery.list(GraphQLModel::class.java, it)
            } ?: ModelQuery.list(GraphQLModel::class.java),
            { response ->
                Log.i(
                    LOG_TAG,
                    "Model query for ${GraphQLModel::class.simpleName} received response $response"
                )
                val itemsList = response.data?.items?.filterNotNull()

                if (itemsList != null && itemsList.isNotEmpty()) {
                    trySendBlocking(itemsList)
                }
            },
            { exception ->
                Log.i(
                    LOG_TAG,
                    "Model query for ${GraphQLModel::class.simpleName} failed with $exception"
                )
                cancel(CancellationException(exception.message))
            }
        )
        awaitClose { graphQLOperation?.cancel() }
    }
}

/**
 * Performs a GraphQL query.
 *
 * On success, returns [ApiResult.Success] if the response data is not null,
 * otherwise - [ApiResult.Failure] with [ResponseError] with a list of response errors.
 *
 * On failure, returns [ApiResult.Failure] with [ApiException].
 */
internal suspend inline fun <reified D : Operation.Data, T, V : Operation.Variables> Query<D, T, V>.launch(): ApiResult<D> {
    return suspendCancellableCoroutine {
        val queryVariables = SortedInputFieldMapWriter { o1, o2 -> o1.compareTo(o2) }.also {
            variables().marshaller().marshal(it)
        }.map()

        Amplify.API.query(
            SimpleGraphQLRequest(
                queryDocument(),
                queryVariables,
                D::class.java,
                GsonVariablesSerializer()
            ),
            { response ->
                Log.i(LOG_TAG, "Query ${this@launch} received response $response")
                response.data?.let { data ->
                    resume(ApiResult.Success(data))
                } ?: resume(ApiResult.Failure(ResponseError(response.errors)))
            },
            { apiException ->
                Log.e(LOG_TAG, "Query ${this@launch} failed with $apiException")
                resume(ApiResult.Failure(apiException))
            }
        )
    }
}

/**
 * Performs a GraphQL mutation.
 *
 * On success, returns [ApiResult.Success] if the response data is not null,
 * otherwise - [ApiResult.Failure] with [ResponseError] with a list of response errors.
 *
 * On failure, returns [ApiResult.Failure] with [ApiException].
 */
internal suspend inline fun <reified R, D : Operation.Data, T, V : Operation.Variables> Mutation<D, T, V>.launch(): ApiResult<R> {
    return suspendCancellableCoroutine {
        Amplify.API.mutate(
            SimpleGraphQLRequest(
                queryDocument(),
                variables().valueMap(),
                R::class.java,
                GsonVariablesSerializer()
            ),
            { response ->
                Log.i(LOG_TAG, "Mutation ${this@launch} received response $response")
                resume(
                    response.data?.let { data ->
                        ApiResult.Success(data)
                    } ?: ApiResult.Failure(ResponseError(response.errors))
                )
            },
            { apiException ->
                Log.e(LOG_TAG, "Mutation ${this@launch} failed with $apiException")
                resume(ApiResult.Failure(apiException))
            }
        )
    }
}

/**
 * Launches a GraphQL subscription.
 * @param subscriptionType Type of subscription.
 * @param GraphQLModel Type of model to return.
 */
internal inline fun <reified GraphQLModel : Model> subscribe(
    subscriptionType: SubscriptionType
): Flow<GraphQLModel> {
    return callbackFlow {
        var subscriptionId = ""

        val graphQLOperation = Amplify.API.subscribe(
            ModelSubscription.of(GraphQLModel::class.java, subscriptionType),
            { id ->
                subscriptionId = id
                Log.i(
                    LOG_TAG,
                    "Subscription $subscriptionId with type $subscriptionType " +
                        "for ${GraphQLModel::class.simpleName} has been established"
                )
            },
            { response ->
                Log.i(LOG_TAG, "Subscription $subscriptionId received response: $response")
                response.data?.let {
                    trySendBlocking(response.data)
                }
            },
            { apiException ->
                Log.i(LOG_TAG, "Subscription $subscriptionId terminates with $apiException")
                cancel(CancellationException(apiException.message, apiException))
            },
            {
                Log.i(LOG_TAG, "Subscription $subscriptionId ended")
                channel.close()
            }
        )

        awaitClose {
            Log.i(LOG_TAG, "Flow with subscription $subscriptionId is closed")
            graphQLOperation?.cancel()
        }
    }
}
