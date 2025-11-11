package com.opal.app.presentation.reward

import android.content.Context
import com.opal.app.R
import com.opal.app.domain.model.DomainReward
import com.opal.app.domain.model.DomainUser

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

fun List<DomainReward>.toRewardItemList(
    context: Context,
    user: DomainUser
): List<RewardItem> {
    val referredCount = user.referredFriendList.size
    return sortedBy { it.referralNumber }
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
