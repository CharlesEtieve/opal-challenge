package com.opal.app.presentation.reward

import android.content.Context
import com.opal.app.BaseViewModelTests
import com.opal.app.DomainFixtures
import com.opal.app.domain.repository.RewardRepository
import com.opal.app.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ReferralViewModelTests: BaseViewModelTests() {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test loading`() = runTest {
        // given
        val rewardRepository = mockk<RewardRepository>()
        val userRepository = mockk<UserRepository>()
        val context = mockk<Context>(relaxed = true)
        val user = DomainFixtures.DomainUserUtils.create()
        val rewardList = DomainFixtures.DomainRewardUtil.createList()
        coEvery { rewardRepository.getRewardList() } returns flowOf(rewardList)
        coEvery { userRepository.getCurrentUser() } returns flowOf(user)

        // when
        val viewModel = getViewModel(rewardRepository, userRepository)
        val expectedRewardItemList = rewardList.toRewardItemList(context, user)
        val expectedCurrentReward = rewardList.toCurrentReward(context, user)

        // then
        assertEquals(viewModel.rewardItemList.value, expectedRewardItemList)
        assertEquals(viewModel.currentReward.value, expectedCurrentReward)
    }

    private fun getViewModel(
        rewardRepository: RewardRepository,
        userRepository: UserRepository
    ): ReferralViewModel {
        return ReferralViewModel(
            rewardRepository,
            userRepository
        )
    }
}
