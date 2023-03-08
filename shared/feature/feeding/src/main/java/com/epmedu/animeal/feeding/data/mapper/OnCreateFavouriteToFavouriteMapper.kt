package com.epmedu.animeal.feeding.data.mapper

import OnCreateFavouriteSubscription.OnCreateFavourite
import com.amplifyframework.datastore.generated.model.Favourite

internal fun OnCreateFavourite.toFavourite() = Favourite.builder()
    .userId(userId())
    .feedingPointId(feedingPointId())
    .id(id())
    .build()