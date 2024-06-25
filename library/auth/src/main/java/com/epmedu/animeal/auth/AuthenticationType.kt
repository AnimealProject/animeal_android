package com.epmedu.animeal.auth

sealed interface AuthenticationType {
    data object Mobile : AuthenticationType
    data class Facebook(val isPhoneNumberVerified: Boolean) : AuthenticationType
}