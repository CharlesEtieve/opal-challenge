package com.opal.app.data.repository

import com.opal.app.domain.model.DomainReward
import com.opal.app.domain.repository.RewardRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class LiveRewardRepository : RewardRepository {
    override fun getRewardList(): Flow<List<DomainReward>> = rewardListFlow

    override suspend fun claimReward(rewardId: String) {
        delay(3000) // simulate api call
        rewardListFlow.value =
            rewardListFlow.value.map { reward ->
                if (reward.id == rewardId) {
                    reward.copy(claimed = true)
                } else {
                    reward
                }
            }
    }

    val rewardListFlow =
        MutableStateFlow(
            listOf(
                DomainReward(
                    id = "1",
                    referralNumber = 1,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "Loyal Gem",
                    subtitle = "Unlock this special milestone",
                    claimed = true,
                ),
                DomainReward(
                    id = "3",
                    referralNumber = 3,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "Soulful Gem",
                    subtitle = "Unlock this special milestone",
                    claimed = false,
                ),
                DomainReward(
                    id = "5",
                    referralNumber = 5,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "1 Year of Opal Pro",
                    subtitle = "Unlock one whole year of Opal Pro for Free",
                    claimed = false,
                ),
                DomainReward(
                    id = "10",
                    referralNumber = 10,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "Popular Gem",
                    subtitle = "Unlock this special milestone",
                    claimed = false,
                ),
                DomainReward(
                    id = "20",
                    referralNumber = 20,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "Special Gift",
                    subtitle = "Contact us to receive a special gift from the Opal Team",
                    claimed = false,
                ),
                DomainReward(
                    id = "100",
                    referralNumber = 100,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "Mystery Gift",
                    subtitle = "Contact us to receive a life changing gift from Opal",
                    claimed = false,
                ),
            ),
        )
}
