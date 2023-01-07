package com.epmedu.animeal.extensions

import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.Model
import com.amplifyframework.core.model.query.predicate.QueryPredicate
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.CancellationException

/**
 * Creates a query for list of models of type [GraphQLModel] with provided [predicate]
 * and on successful responses returns flow with mapped by [map] function items of type [MappedModel].
 * On failure flow closes.
 * @param modelType class of the GraphQL model
 * @param map function to map models from response
 * @param predicate predicate for filtering. By default `null`
 */
inline fun <GraphQLModel : Model, MappedModel> getModelList(
    modelType: Class<GraphQLModel>,
    crossinline map: GraphQLModel.() -> MappedModel,
    predicate: QueryPredicate? = null,
) = callbackFlow {
    Amplify.API.query(
        predicate?.let { ModelQuery.list(modelType, it) } ?: ModelQuery.list(modelType),
        { response ->
            response.data?.items?.map { model ->
                model.map()
            }?.let { trySendBlocking(it) }
        },
        { exception ->
            cancel(CancellationException(exception.message))
        }
    )
    awaitClose()
}