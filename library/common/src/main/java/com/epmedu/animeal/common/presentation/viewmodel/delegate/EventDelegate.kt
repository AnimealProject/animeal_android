package com.epmedu.animeal.common.presentation.viewmodel.delegate

import kotlinx.coroutines.flow.SharedFlow

@Deprecated(
    message = """ViewModel events is an antipattern.
            Usages of this interface should be replaced with StateDelegate or moved to UI if there is no business logic.
            Please read the following article to learn more: 
            https://medium.com/androiddevelopers/viewmodel-one-off-event-antipatterns-16a1da869b95""",
    replaceWith = ReplaceWith("StateDelegate"),
    level = DeprecationLevel.WARNING
)
interface EventDelegate<Event> {

    val events: SharedFlow<Event>

    suspend fun sendEvent(event: Event)
}