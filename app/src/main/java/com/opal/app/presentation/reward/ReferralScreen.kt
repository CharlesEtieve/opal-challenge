package com.opal.app.presentation.reward

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.opal.app.R
import com.opal.app.koinPreviewDeclaration
import com.opal.app.presentation.collectAsState
import com.opal.app.presentation.dashedBorder
import com.opal.app.presentation.error.ErrorPopup
import com.opal.app.presentation.theme.Background
import com.opal.app.presentation.theme.Blue
import com.opal.app.presentation.theme.OpalButton
import com.opal.app.presentation.theme.OpalButtonState
import com.opal.app.presentation.theme.OpalFont
import com.opal.app.presentation.theme.OpalText
import com.opal.app.presentation.theme.Purple
import com.opal.app.presentation.theme.White10
import com.opal.app.presentation.theme.White15
import com.opal.app.presentation.theme.White40
import com.opal.app.presentation.theme.White60
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Serializable
data object ReferralRoute

@Composable
fun ReferralScreen(viewModel: ReferralViewModel = koinViewModel()) {
    ErrorPopup(viewModel.errorPopupViewModel)
    val rewardItemList by viewModel.rewardItemList.collectAsState()
    LazyColumn(
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.horizontal_margin)),
        contentPadding = PaddingValues(bottom = 32.dp),
    ) {
        item(key = "header") {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                Header()
                ExplanationLabel()
                val currentReward by viewModel.currentReward.collectAsState()
                currentReward?.let {
                    CurrentRewardView(it, viewModel)
                }
                Buttons(viewModel)
            }
            Spacer(Modifier.height(32.dp))
        }
        rewardItemList.forEachIndexed { index, rewardItem ->
            item(rewardItem.id) {
                val isLast = index == rewardItemList.size - 1
                RewardItem(
                    viewModel = viewModel,
                    rewardItem = rewardItem,
                    showArrowIcon = isLast.not(),
                )
            }
        }
    }
}

@Composable
private fun Header() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(182.dp)
                .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_purple_gem),
            contentDescription = "Radial Gradient Background",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleX = 2f
                        scaleY = 2f
                    }.align(Alignment.Center),
            contentScale = ContentScale.Crop,
        )

        // Background seal image
        Image(
            painter = painterResource(id = R.drawable.img_opal_seal),
            contentDescription = "Opal Seal Background",
            modifier =
                Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center),
            contentScale = ContentScale.Fit,
        )

        // Centered logo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_opal),
                contentDescription = "Opal Logo",
                modifier = Modifier.width(85.8.dp),
            )
            OpalText(
                text = stringResource(R.string.referral_subtitle),
                font = OpalFont.Caption,
            )
        }
    }
}

@Composable
private fun ExplanationLabel() {
    OpalText(
        text = stringResource(R.string.referral_explanation),
        font = OpalFont.CalloutRegular,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun CurrentRewardView(
    currentReward: CurrentReward,
    viewModel: ReferralViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier =
            Modifier
                .dashedBorder()
                .padding(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OpalText(
                text = stringResource(R.string.referred_friends),
                font = OpalFont.CalloutSemibold,
            )
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile Icon",
                modifier = Modifier.width(15.dp),
                tint = Color.White,
            )
            OpalText(
                text = "${currentReward.referredCount}",
                font = OpalFont.CalloutSemibold,
                modifier = Modifier.padding(start = 3.dp),
            )
        }
        Canvas(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(1.dp),
        ) {
            drawLine(
                color = White15,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx(),
                pathEffect =
                    PathEffect.dashPathEffect(
                        intervals = floatArrayOf(4.dp.toPx(), 2.dp.toPx()),
                        phase = 0f,
                    ),
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AsyncImage(
                model = currentReward.rewardImage,
                contentDescription = "Reward Image",
                modifier =
                    Modifier
                        .size(72.dp)
                        .clip(
                            RoundedCornerShape(16.dp),
                        ),
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    OpalText(
                        text = currentReward.unlockLabel,
                        font = OpalFont.FootNoteSemibold,
                        color = White60,
                    )
                    Row {
                        OpalText(
                            text = currentReward.title,
                            font = OpalFont.CalloutSemibold,
                        )
                        Spacer(Modifier.weight(1f))

                        if (currentReward.claimButtonState != CurrentReward.ClaimButtonState.HIDDEN) {
                            OpalButton(
                                icon = R.drawable.ic_checkmark,
                                title =
                                    if (currentReward.claimButtonState == CurrentReward.ClaimButtonState.TO_CLAIM) {
                                        stringResource(R.string.claim_button)
                                    } else {
                                        stringResource(R.string.claimed_button)
                                    },
                                state = OpalButtonState.Secondary,
                                isEnabled = currentReward.claimButtonState == CurrentReward.ClaimButtonState.TO_CLAIM,
                                isLoading = currentReward.claimButtonState == CurrentReward.ClaimButtonState.IS_LOADING
                            ) {
                                viewModel.onCurrentRewardButtonClicked()
                            }
                        }
                        if (currentReward.countLabel != null) {
                            OpalText(
                                text = currentReward.countLabel,
                                font = OpalFont.FootNoteSemibold,
                                color = White60,
                            )
                        }
                    }
                }
                if (currentReward.progress != null) {
                    ProgressBar(currentReward.progress)
                }
            }
        }
    }
}

@Composable
private fun ProgressBar(progress: Float) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(White10),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        brush =
                            Brush.horizontalGradient(
                                colors = listOf(Purple, Blue),
                            ),
                    ),
        )
    }
}

@Composable
private fun Buttons(viewModel: ReferralViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OpalButton(
            title = stringResource(R.string.add_friends_button),
            state = OpalButtonState.Primary,
            isWrapContentSize = false,
            icon = R.drawable.ic_invite,
        ) {
            viewModel.onShareButtonClicked()
        }
        OpalButton(
            title = stringResource(R.string.share_referral_link_button),
            state = OpalButtonState.Secondary,
            isWrapContentSize = false,
            icon = R.drawable.ic_share,
        ) {
            viewModel.onShareButtonClicked()
        }
    }
}

@Composable
private fun RewardItem(
    rewardItem: RewardItem,
    showArrowIcon: Boolean,
    viewModel: ReferralViewModel,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, White10, RoundedCornerShape(16.dp))
                    .padding(16.dp),
        ) {
            AsyncImage(
                model = rewardItem.image,
                contentDescription = "Reward Image",
                modifier =
                    Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(120.dp),
            ) {
                Text(
                    text = rewardItem.friendTitle,
                    style =
                        TextStyle(
                            brush =
                                Brush.horizontalGradient(
                                    colors = listOf(Purple, Blue),
                                ),
                        ),
                )
                Spacer(Modifier.weight(1f))
                OpalText(
                    text = rewardItem.rewardTitle,
                    font = OpalFont.BodyMedium,
                )
                Spacer(Modifier.weight(0.5f))
                OpalText(
                    text = rewardItem.subtitle,
                    font = OpalFont.Body,
                    color = White40,
                )
                Spacer(Modifier.weight(1f))
                if (rewardItem.claimButtonState != RewardItem.ClaimButtonState.HIDDEN) {
                    OpalButton(
                        icon = R.drawable.ic_checkmark,
                        title =
                            if (rewardItem.claimButtonState == RewardItem.ClaimButtonState.TO_CLAIM) {
                                stringResource(R.string.claim_button)
                            } else {
                                stringResource(R.string.claimed_button)
                            },
                        state = OpalButtonState.Secondary,
                        isEnabled = rewardItem.claimButtonState == RewardItem.ClaimButtonState.TO_CLAIM,
                        isLoading = rewardItem.isLoading,
                    ) {
                        viewModel.onRewardButtonClicked(rewardItem)
                    }
                } else {
                    ProgressBar(rewardItem.progress)
                }
            }
        }
        if (showArrowIcon) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "Arrow Icon",
                modifier = Modifier.height(32.dp),
                tint = Color.White,
            )
            Spacer(Modifier.height(4.dp))
        }
    }
}

@Preview
@Composable
private fun ReferralPreview() {
    KoinApplication(koinPreviewDeclaration(LocalContext.current)) {
        Column(Modifier.background(Background)) {
            ReferralScreen()
        }
    }
}
