package com.epmedu.animeal.auth

sealed interface AuthenticationType {
    object Mobile : AuthenticationType
    data class Facebook(val isPhoneNumberVerified: Boolean) : AuthenticationType
}