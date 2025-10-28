package com.opal.app.presentation.theme

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.opal.app.R
import com.opal.app.presentation.`if`

@Composable
fun OpalButton(
    title: String,
    modifier: Modifier = Modifier,
    size: JaspButtonSize = JaspButtonSize.Large,
    isWrapContentSize: Boolean = true,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    state: OpalButtonState = OpalButtonState.Primary,
    icon: Int? = null,
    iconSize: Dp = 18.dp,
    iconColor: Color? = null,
    content: (@Composable () -> Unit)? = null,
    buttonAction: () -> Unit,
) {
    val updatedIconColor =
        iconColor ?: when (isEnabled) {
            true ->
                when (state) {
                    OpalButtonState.Primary -> Color.White
                    OpalButtonState.Secondary -> Color.Black
                }

            false -> White40
        }

    // vertical padding would not match design
    val buttonHeight =
        dimensionResource(
            when (size) {
                JaspButtonSize.Large -> R.dimen.large_button_height
                JaspButtonSize.Small -> R.dimen.small_button_height
            },
        )
    val horizontalContentPadding =
        dimensionResource(
            when (size) {
                JaspButtonSize.Large -> R.dimen.large_button_horizontal_content_padding
                JaspButtonSize.Small -> R.dimen.small_button_horizontal_content_padding
            },
        )
    val minWidth: Dp =
        when {
            isLoading -> buttonHeight
            isWrapContentSize -> 0.dp
            else -> dimensionResource(R.dimen.button_width)
        }
    val loaderDim: Dp = dimensionResource(R.dimen.button_loader)

    val containerColor by animateColorAsState(
        targetValue =
            when (state) {
                OpalButtonState.Primary -> Blue
                OpalButtonState.Secondary -> Color.White
            },
        label = "containerColor",
    )

    val disabledContainerColor by animateColorAsState(
        targetValue = White10,
        label = "disabledContainerColor",
    )

    Button(
        onClick =
            if (isLoading) {
                { }
            } else {
                buttonAction
            },
        // disable click when isLoading
        modifier =
            modifier
                .height(buttonHeight)
                .`if`(isLoading) {
                    this.width(buttonHeight)
                }.`if`(!isLoading && isWrapContentSize) {
                    this.widthIn(min = minWidth)
                }.`if`(!isLoading && !isWrapContentSize) {
                    this.fillMaxWidth()
                }.animateContentSize(),
        shape = CircleShape,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = containerColor,
                disabledContainerColor = disabledContainerColor,
            ),
        contentPadding =
            PaddingValues(
                horizontal =
                    when (isLoading) {
                        true -> 0.dp
                        false -> horizontalContentPadding
                    },
                vertical = 0.dp,
            ),
        enabled = isEnabled,
    ) {
        AnimatedContent(
            targetState = isLoading,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "",
            contentAlignment = Alignment.Center,
        ) { isLoading ->
            if (isLoading) {
                Box(
                    modifier = Modifier.size(buttonHeight),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(loaderDim),
                        color = Color.Black,
                    )
                }
            } else {
                content?.let {
                    it()
                } ?: run {
                    Box(contentAlignment = Alignment.Center) {
                        Row(
                            modifier = Modifier.align(Alignment.Center),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (icon != null) {
                                Image(
                                    modifier = Modifier.size(iconSize),
                                    painter = painterResource(id = icon),
                                    colorFilter = ColorFilter.tint(updatedIconColor),
                                    contentDescription = null,
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            OpalText(
                                maxLines = 1,
                                modifier =
                                    Modifier
                                        .animateContentSize()
                                        .align(Alignment.CenterVertically),
                                text = title,
                                font =
                                    when (size) {
                                        JaspButtonSize.Large -> OpalFont.LargeButtonTitle
                                        JaspButtonSize.Small -> OpalFont.SmallButtonTitle
                                    },
                                color =
                                    when (isEnabled) {
                                        true ->
                                            when (state) {
                                                OpalButtonState.Primary -> Color.White
                                                OpalButtonState.Secondary -> Color.Black
                                            }

                                        false -> White40
                                    },
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class JaspButtonSize {
    Large,
    Small,
}

enum class OpalButtonState {
    Primary,
    Secondary,
}
