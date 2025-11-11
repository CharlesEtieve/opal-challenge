package com.opal.app.presentation.reward

import android.content.Context
import com.opal.app.R
import com.opal.app.domain.model.DomainReward
import com.opal.app.domain.model.DomainUser

data class CurrentReward(
    val id: String,
    val referredCount: Int,
    val friendPictureList: List<String>,
    val rewardImage: String,
    val unlockLabel: String,
    val title: String,
    val countLabel: String?,
    val progress: Float?,
    val claimButtonState: ClaimButtonState,
) {
    enum class ClaimButtonState {
        TO_CLAIM,
        CLAIMED,
        IS_LOADING,
        HIDDEN,
    }
}

fun List<DomainReward>.toCurrentReward(
    context: Context,
    user: DomainUser
): CurrentReward? {
    val referredCount = user.referredFriendList.size
    sortedBy { it.referralNumber }
        .forEachIndexed { index, reward ->
            val isLastReward = index == size - 1
            if (referredCount < reward.referralNumber || isLastReward) {
                val hasNextUnlock = index != size - 1
                val isUnlocked = referredCount >= reward.referralNumber
                val displayClaimButton = hasNextUnlock.not() && isUnlocked
                return CurrentReward(
                    id = reward.id,
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
                    countLabel = if (displayClaimButton.not()) {
                        "$referredCount/${reward.referralNumber}"
                    } else null,
                    progress = if (displayClaimButton.not()) {
                        (referredCount.toFloat() / reward.referralNumber).coerceAtMost(1f)
                    } else null,
                    claimButtonState =
                        if (displayClaimButton) {
                            if (reward.claimed) {
                                CurrentReward.ClaimButtonState.CLAIMED
                            } else {
                                CurrentReward.ClaimButtonState.TO_CLAIM
                            }
                        } else {
                            CurrentReward.ClaimButtonState.HIDDEN
                        }
                )
            }
        }
    return null
}
