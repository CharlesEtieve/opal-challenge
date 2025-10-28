package com.opal.app.domain.repository

import com.opal.app.domain.model.DomainUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<DomainUser>
}
