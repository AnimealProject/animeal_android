package com.epmedu.animeal.permissions.presentation.handler

import com.epmedu.animeal.permissions.presentation.PermissionsEvent
import com.epmedu.animeal.permissions.presentation.PermissionsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface PermissionsHandler {

    val permissionsStateFlow: StateFlow<PermissionsState>

    fun CoroutineScope.handlePermissionEvent(event: PermissionsEvent)
}