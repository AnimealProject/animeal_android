package com.epmedu.animeal.messages.domain.model

import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.users.domain.model.User
import java.util.Date

data class Message(
    val id: String,
    val feedingPointId: String,
    val sender: User?,
    val content: String,
    val createdAt: Date,
    val photos: List<NetworkFile>,
)
