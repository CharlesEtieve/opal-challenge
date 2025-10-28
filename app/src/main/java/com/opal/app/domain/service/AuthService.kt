package com.opal.app.domain.service

interface AuthService {
    suspend fun isUserAuthenticated(): Boolean
}
