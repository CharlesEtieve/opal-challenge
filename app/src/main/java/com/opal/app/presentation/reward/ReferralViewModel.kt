package com.opal.app.presentation.reward

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.opal.app.R
import com.opal.app.domain.model.DomainReward
import com.opal.app.domain.model.DomainUser
import com.opal.app.domain.repository.RewardRepository
import com.opal.app.domain.repository.UserRepository
import com.opal.app.presentation.BaseViewModel
import com.opal.app.presentation.reward.CurrentReward.ClaimButtonState
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
                rewardItemList.value = rewardList.toRewardItemList(context, user)
                //computeCurrentReward(user, rewardList)
                currentReward.value = rewardList.toCurrentReward(context, user)
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

    fun onCurrentRewardButtonClicked() {
        viewModelScope.launch {
            try {
                // set loading
                currentReward.value?.let {
                    currentReward.value = it.copy(claimButtonState = ClaimButtonState.IS_LOADING)
                    rewardRepository.claimReward(it.id)
                }
            } catch (exception: Exception) {
                handleException(exception)
            } finally {
                currentReward.value = currentReward.value?.copy(claimButtonState = ClaimButtonState.IS_LOADING)
            }
        }
    }
}
