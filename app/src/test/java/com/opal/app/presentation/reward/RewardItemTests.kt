package com.opal.app.presentation.reward

import android.content.Context
import com.opal.app.DomainFixtures
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class RewardItemTests {

    @Test
    fun `test reward item mapping progress`() {
        // given
        val rewardList = DomainFixtures.DomainRewardUtil.createList()
        val user = DomainFixtures.DomainUserUtils.create()
        val context = mockk<Context>(relaxed = true)

        // when
        val rewardItemList = rewardList.toRewardItemList(context, user)

        // then
        val expectedProgressForFirstReward =
            (user.referredFriendList.size.toFloat() / rewardList.first().referralNumber).coerceAtMost(1f)
        assertEquals(rewardItemList.first().progress, expectedProgressForFirstReward)
    }

    @Test
    fun `test reward item mapping to claim button state`() {
        // given
        val rewardList = DomainFixtures.DomainRewardUtil.createList()
        val user = DomainFixtures.DomainUserUtils.create()
        val context = mockk<Context>(relaxed = true)

        // when
        val rewardItemList = rewardList.toRewardItemList(context, user)

        //then
        // assert that the first reward item has the correct claim button state
        val toClaimIndex = 2
        assertEquals(rewardItemList[toClaimIndex].claimButtonState, RewardItem.ClaimButtonState.TO_CLAIM)
    }

    @Test
    fun `test reward item mapping claimed button state`() {
        // given
        val rewardList = DomainFixtures.DomainRewardUtil.createList()
        val user = DomainFixtures.DomainUserUtils.create()
        val context = mockk<Context>(relaxed = true)

        // when
        val rewardItemList = rewardList.toRewardItemList(context, user)

        //then
        // assert that the first reward item has the correct claim button state
        val claimedIndex = 0
        assertEquals(rewardItemList[claimedIndex].claimButtonState, RewardItem.ClaimButtonState.CLAIMED)
    }

    @Test
    fun `test reward item mapping hidden button state`() {
        // given
        val rewardList = DomainFixtures.DomainRewardUtil.createList()
        val user = DomainFixtures.DomainUserUtils.create()
        val context = mockk<Context>(relaxed = true)

        // when
        val rewardItemList = rewardList.toRewardItemList(context, user)

        //then
        // assert that the first reward item has the correct claim button state
        val hiddenIndex = 5
        assertEquals(rewardItemList[hiddenIndex].claimButtonState, RewardItem.ClaimButtonState.HIDDEN)
    }
}
