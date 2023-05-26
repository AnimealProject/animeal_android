package com.epmedu.animeal.api

import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.SubscriptionType
import com.amplifyframework.api.rest.RestOptions
import com.amplifyframework.core.model.Model
import com.amplifyframework.core.model.query.predicate.QueryPredicate
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.epmedu.animeal.api.wrapper.ResponseError
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow

/**
 * Common interface for making different types of requests
 */
interface AnimealApi {

    /**
     * Invokes GET Http request and deserializes response to given [R] type.
     * On successful response and deserialization, returns [ApiResult.Success],
     * otherwise returns [ApiResult.Failure] with corresponding exception.
     */
    suspend fun <R : Any> launchGetRequest(
        restOptions: RestOptions,
        responseClass: Class<R>
    ): ApiResult<R>

    /**
     * Creates a query for list of models of type [GraphQLModel] with provided [predicate]
     * and on successful responses returns flow with the list of the models.
     * On failure flow closes.
     * @param predicate predicate for filtering. By default `null`
     */
    fun <GraphQLModel : Model> getModelList(
        modelClass: Class<GraphQLModel>,
        predicate: QueryPredicate? = null
    ): Flow<List<GraphQLModel>>

    /**
     * Performs a GraphQL query.
     *
     * On success, returns [ApiResult.Success] if the response data is not null,
     * otherwise - [ApiResult.Failure] with [ResponseError] with a list of response errors.
     *
     * On failure, returns [ApiResult.Failure] with [ApiException].
     */
    suspend fun <D : Operation.Data, T, V : Operation.Variables> launchQuery(
        query: Query<D, T, V>,
        responseClass: Class<D>
    ): ApiResult<D>

    /**
     * Performs a GraphQL mutation.
     *
     * On success, returns [ApiResult.Success] if the response data is not null,
     * otherwise - [ApiResult.Failure] with [ResponseError] with a list of response errors.
     *
     * On failure, returns [ApiResult.Failure] with [ApiException].
     */
    suspend fun <R, D : Operation.Data, T, V : Operation.Variables> launchMutation(
        mutation: Mutation<D, T, V>,
        responseClass: Class<R>
    ): ApiResult<R>

    /**
     * Launches a GraphQL subscription.
     * @param subscriptionType Type of subscription.
     * @param GraphQLModel Type of model to return.
     */
    fun <GraphQLModel : Model> launchSubscription(
        subscriptionType: SubscriptionType,
        modelClass: Class<GraphQLModel>
    ): Flow<GraphQLModel>
}