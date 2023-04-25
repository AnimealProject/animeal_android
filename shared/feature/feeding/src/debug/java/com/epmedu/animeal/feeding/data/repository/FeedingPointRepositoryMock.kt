package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import kotlin.random.Random
import kotlin.random.nextInt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class FeedingPointRepositoryMock(
    dispatchers: Dispatchers,
    private val favouriteRepository: FavouriteRepository
) : FeedingPointRepository {

    private val cities = setOf("Batumi", "Tbilisi", "Suhumi")
    private val stubData = List(25) { index ->
        FeedingPoint(
            id = index.toString(),
            title = "$index - Near to Bukia Garden M.S Technical University",
            description = "Ordered list : <ol> <li>first item</li> <li>second item</li> " +
                    "<li>third item</li> </ol><h1> Header1 </h1> <h2> Header2 </h2> <h3> Header3 </h3>" +
                    "<h4> Header4 </h4> <h5> Header5 </h5> <h6> Header6 </h6> Text <br> separated by" +
                    " <br> breaklines. <b> Bold text </b> <i> Italic text </i> <p> Text in paragraph " +
                    "</p> <s> Strikethrough text </s> <mark> Highlighted text </mark> and " +
                    "<a href=\"https://www.google.com/\"> text with link </a> and just <u> Underlined" +
                    " text </u> unordered list: <ul> <li>first item</li> <li>second item</li>" +
                    " <li>third item</li> </ul> Text outside markup tags",
            city = cities.random(),
            animalStatus = AnimalState.values().random(),
            animalType = AnimalType.values().random(),
            isFavourite = Random.nextBoolean(),
            location = MapLocation(
                Random.nextDouble(41.6752, 41.7183),
                Random.nextDouble(44.7724, 44.8658)
            ),
            lastFeeder = Feeder(
                id = index.toString(),
                name = "$index - Giorgi Abutidze",
                time = "${Random.nextInt(0..24)} hours ago"
            ),
            images = listOf(
                "https://fastly.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI",
                "https://fastly.picsum.photos/id/237/200/300.jpg?hmac=TmmQSbShHz9CdQm0NkEjx1Dyh_Y984R9LpNrpvH2D_U",
                "https://fastly.picsum.photos/id/719/200/200.jpg?hmac=WkMnZveCKylVzw33Ui-BNFbah8IQWImYq68wVKznlEo",
            )
        )
    }

    private val coroutineScope = CoroutineScope(dispatchers.IO)

    private val feedingPointsFlow = MutableStateFlow(stubData)

    init {
        coroutineScope.launch {
            favouriteRepository.getFavouriteFeedingPointIds().collect { favouriteIds ->
                feedingPointsFlow.value = stubData.map { feedingPoint ->
                    feedingPoint.copy(
                        isFavourite = favouriteIds.any { it == feedingPoint.id }
                    )
                }
            }
        }
    }

    override fun getAllFeedingPoints(): Flow<List<FeedingPoint>> {
        return feedingPointsFlow.asStateFlow()
    }

    override fun getFeedingPointsBy(predicate: (FeedingPoint) -> Boolean): Flow<List<FeedingPoint>> {
        return getAllFeedingPoints().map { feedingPoints -> feedingPoints.filter(predicate) }
    }
}
