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
 * and on successful responses returns flow with the list of the models.
 * On failure flow closes.
 * @param modelType class of the GraphQL model
 * @param predicate predicate for filtering. By default `null`
 */
fun <GraphQLModel : Model> getModelList(
    modelType: Class<GraphQLModel>,
    predicate: QueryPredicate? = null,
) = callbackFlow {
    Amplify.API.query(
        predicate?.let { ModelQuery.list(modelType, it) } ?: ModelQuery.list(modelType),
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