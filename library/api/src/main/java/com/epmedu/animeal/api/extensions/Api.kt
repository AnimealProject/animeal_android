package com.epmedu.animeal.api.extensions

import com.amplifyframework.api.ApiException
import com.amplifyframework.api.aws.GsonVariablesSerializer
import com.amplifyframework.api.graphql.SimpleGraphQLRequest
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.Model
import com.amplifyframework.core.model.query.predicate.QueryPredicate
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation.Data
import com.apollographql.apollo.api.Operation.Variables
import com.epmedu.animeal.api.wrapper.ApiResult
import com.epmedu.animeal.api.wrapper.ResponseError
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.CancellationException

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
        Amplify.API.query(
            predicate?.let {
                ModelQuery.list(GraphQLModel::class.java, it)
            } ?: ModelQuery.list(GraphQLModel::class.java),
            { response ->
                response.data?.items?.let {
                    trySendBlocking(it.toList())
                }
            },
            { exception ->
                cancel(CancellationException(exception.message))
            }
        )
        awaitClose()
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
    return suspendCancellableCoroutine { continuation ->
        Amplify.API.mutate(
            SimpleGraphQLRequest(
                queryDocument(),
                variables().valueMap(),
                R::class.java,
                GsonVariablesSerializer()
            ),
            { response ->
                response.data?.let { data ->
                    continuation.resumeWith(Result.success(ApiResult.Success(data)))
                } ?: continuation.resumeWith(
                    Result.success(
                        ApiResult.Failure(ResponseError(response.errors))
                    )
                )
            },
            { apiException ->
                continuation.resumeWith(Result.success(ApiResult.Failure(apiException)))
            }
        )
    }
}