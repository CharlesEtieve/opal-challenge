package com.opal.app.data.service

import com.opal.app.domain.service.AuthService

class LiveAuthService : AuthService {
    override suspend fun isUserAuthenticated(): Boolean {
        // check if user token exists in secure storage
        return TODO()
    }
}
