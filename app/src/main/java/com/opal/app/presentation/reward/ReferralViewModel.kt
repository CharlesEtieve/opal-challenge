package com.opal.app.presentation.reward

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.opal.app.R
import com.opal.app.domain.model.DomainReward
import com.opal.app.domain.model.DomainUser
import com.opal.app.domain.repository.RewardRepository
import com.opal.app.domain.repository.UserRepository
import com.opal.app.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class ReferralViewModel(
    val rewardRepository: RewardRepository,
    userRepository: UserRepository,
) : BaseViewModel() {
    val rewardItemList = MutableStateFlow<List<RewardItem>>(emptyList())
    val currentReward = MutableStateFlow<CurrentReward?>(null)

    init {
        userRepository
            .getCurrentUser()
            .combine(rewardRepository.getRewardList()) { user, rewardList ->
                computeRewardItemList(user, rewardList)
                computeCurrentReward(user, rewardList)
            }.handleException()
            .launchIn(viewModelScope)
    }

    fun onShareButtonClicked() {
        try {
            val message = context.getString(R.string.message_shared)

            val shareIntent =
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, message)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }

            val chooserIntent =
                Intent.createChooser(shareIntent, null).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }

            context.startActivity(chooserIntent)
        } catch (exception: Exception) {
            handleException(exception)
        }
    }

    fun onRewardButtonClicked(rewardItem: RewardItem) {
        viewModelScope.launch {
            try {
                // set loading
                rewardItemList.value =
                    rewardItemList.value.map {
                        if (it.id == rewardItem.id) {
                            it.copy(isLoading = true)
                        } else {
                            it
                        }
                    }
                rewardRepository.claimReward(rewardItem.id)
            } catch (exception: Exception) {
                handleException(exception)
            } finally {
                rewardItemList.value =
                    rewardItemList.value.map {
                        it.copy(isLoading = false)
                    }
            }
        }
    }

    private fun computeRewardItemList(
        user: DomainUser,
        rewardList: List<DomainReward>,
    ) {
        val referredCount = user.referredFriendList.size
        rewardItemList.value =
            rewardList
                .sortedBy { it.referralNumber }
                .map { reward ->
                    val progress =
                        (referredCount.toFloat() / reward.referralNumber).coerceAtMost(1f)
                    val isUnlocked = referredCount >= reward.referralNumber
                    RewardItem(
                        id = reward.id,
                        friendTitle =
                            when (reward.referralNumber > 1) {
                                true ->
                                    context.getString(
                                        R.string.reward_item_list_friend_title_several,
                                        reward.referralNumber,
                                    )
                                false -> context.getString(R.string.reward_item_list_friend_title_single)
                            }.uppercase(),
                        rewardTitle = reward.title,
                        subtitle = reward.subtitle,
                        progress = progress,
                        claimButtonState =
                            if (isUnlocked) {
                                if (reward.claimed) {
                                    RewardItem.ClaimButtonState.CLAIMED
                                } else {
                                    RewardItem.ClaimButtonState.TO_CLAIM
                                }
                            } else {
                                RewardItem.ClaimButtonState.HIDDEN
                            },
                        image = reward.image,
                        isLoading = false,
                    )
                }
    }

    private fun computeCurrentReward(
        user: DomainUser,
        rewardList: List<DomainReward>,
    ) {
        val referredCount = user.referredFriendList.size
        rewardList
            .sortedByDescending { it.referralNumber }
            .forEachIndexed { index, reward ->
                if (referredCount < reward.referralNumber) {
                    val hasNextUnlock = index == rewardList.size - 1
                    currentReward.value =
                        CurrentReward(
                            referredCount = referredCount,
                            friendPictureList = user.referredFriendList.map { it.picture },
                            rewardImage = reward.image,
                            unlockLabel =
                                context.getString(
                                    when (hasNextUnlock) {
                                        true -> R.string.referral_next_unlock
                                        false -> R.string.referral_new_unlock
                                    },
                                ),
                            title = reward.title,
                            countLabel = "$referredCount/${reward.referralNumber}",
                            progress = (referredCount.toFloat() / reward.referralNumber).coerceAtMost(1f),
                            isClaimedButtonShown = reward.claimed.not(),
                        )
                }
            }
    }
}

data class RewardItem(
    val id: String,
    val friendTitle: String,
    val rewardTitle: String,
    val subtitle: String,
    val progress: Float,
    val claimButtonState: ClaimButtonState,
    val image: String,
    val isLoading: Boolean,
) {
    enum class ClaimButtonState {
        TO_CLAIM,
        CLAIMED,
        HIDDEN,
    }
}

data class CurrentReward(
    val referredCount: Int,
    val friendPictureList: List<String>,
    val rewardImage: String,
    val unlockLabel: String,
    val title: String,
    val countLabel: String,
    val progress: Float,
    val isClaimedButtonShown: Boolean,
)
