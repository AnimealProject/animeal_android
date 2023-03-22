package com.epmedu.animeal.api.extensions

import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers.CACHE_AND_NETWORK
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.aws.GsonVariablesSerializer
import com.amplifyframework.api.graphql.SimpleGraphQLRequest
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.Model
import com.amplifyframework.core.model.query.predicate.QueryPredicate
import com.apollographql.apollo.GraphQLCall
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation.Data
import com.apollographql.apollo.api.Operation.Variables
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.Subscription
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.fetcher.ResponseFetcher
import com.epmedu.animeal.api.wrapper.ResponseError
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.extensions.suspendCancellableCoroutine
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.CancellationException
import kotlin.coroutines.resume

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
                val itemsList = response.data?.items?.filterNotNull()

                if (itemsList != null && itemsList.isNotEmpty()) {
                    trySendBlocking(itemsList)
                }
            },
            { exception ->
                cancel(CancellationException(exception.message))
            }
        )
        awaitClose { graphQLOperation?.cancel() }
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
internal suspend inline fun <reified R, D : Data, T, V : Variables> Mutation<D, T, V>.performMutation(): ApiResult<R> {
    return suspendCancellableCoroutine {
        Amplify.API.mutate(
            SimpleGraphQLRequest(
                queryDocument(),
                variables().valueMap(),
                R::class.java,
                GsonVariablesSerializer()
            ),
            { response ->
                resume(
                    response.data?.let { data ->
                        ApiResult.Success(data)
                    } ?: ApiResult.Failure(ResponseError(response.errors))
                )
            },
            { apiException ->
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
 * On failure, returns [ApiResult.Failure] with [ApolloException].
 */
internal suspend inline fun <D : Data, T, V : Variables> AWSAppSyncClient.performMutation(
    mutation: Mutation<D, T, V>
): ApiResult<Unit> {
    return suspendCancellableCoroutine {
        val mutationCall = mutate(mutation)

        mutationCall.enqueue(
            object : GraphQLCall.Callback<T>() {
                override fun onResponse(response: Response<T>) {
                    resume(
                        response.data()?.let {
                            ApiResult.Success(Unit)
                        } ?: ApiResult.Failure(ResponseError(response.errors()))
                    )
                }

                override fun onFailure(e: ApolloException) {
                    resume(ApiResult.Failure(e))
                }
            }
        )
    }
}

/**
 * Performs a GraphQL query.
 *
 * On success, returns [ApiResult.Success] if the response data is not null,
 * otherwise - [ApiResult.Failure] with [ResponseError] with a list of response errors.
 *
 * On failure, returns [ApiResult.Failure] with [ApolloException].
 * @param query [Query] to perform.
 * @param getData method to get required data from response.
 * @param responseFetcher cache control strategy for query. By default [CACHE_AND_NETWORK]
 */
internal suspend fun <D : Data, T, V : Variables, R> AWSAppSyncClient.query(
    query: Query<D, T, V>,
    getData: T.() -> R?,
    responseFetcher: ResponseFetcher = CACHE_AND_NETWORK
): ApiResult<R> {
    return suspendCancellableCoroutine {
        val queryCall = query(query).responseFetcher(responseFetcher)

        queryCall.enqueue(
            object : GraphQLCall.Callback<T>() {
                override fun onResponse(response: Response<T>) {
                    resume(
                        response.data()?.getData()?.let {
                            ApiResult.Success(it)
                        } ?: ApiResult.Failure(ResponseError(response.errors()))
                    )
                }

                override fun onFailure(e: ApolloException) {
                    resume(ApiResult.Failure(e))
                }
            }
        )
    }
}

/**
 * Launches a GraphQL subscription.
 * @param subscription [Subscription] to be subscribed to.
 * @param getData method to get required data from response.
 */
internal fun <D : Data, T, V : Variables, R> AWSAppSyncClient.subscribe(
    subscription: Subscription<D, T, V>,
    getData: T.() -> R?,
): Flow<R> {
    return callbackFlow {
        val subscriptionCall = subscribe(subscription)

        subscriptionCall.execute(
            object : AppSyncSubscriptionCall.Callback<T> {
                override fun onResponse(response: Response<T>) {
                    response.data()?.getData()?.let {
                        trySendBlocking(it)
                    }
                }

                override fun onFailure(e: ApolloException) {
                    cancel(CancellationException(e.message))
                }

                override fun onCompleted() {
                    channel.close()
                }
            }
        )

        awaitClose { subscriptionCall.cancel() }
    }
}
