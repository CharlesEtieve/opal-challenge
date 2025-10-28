package com.opal.app.domain.repository

import com.opal.app.domain.model.DomainReward
import kotlinx.coroutines.flow.Flow

interface RewardRepository {
    fun getRewardList(): Flow<List<DomainReward>>

    suspend fun claimReward(rewardId: String)
}
