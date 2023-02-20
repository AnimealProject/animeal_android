package com.epmedu.animeal.api.wrapper

import com.amplifyframework.api.graphql.GraphQLResponse.Error

data class ResponseError(val causes: List<Error>) : Throwable()