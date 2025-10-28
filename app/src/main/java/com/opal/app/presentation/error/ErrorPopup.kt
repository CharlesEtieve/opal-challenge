package com.opal.app.presentation.error

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.opal.app.R
import com.opal.app.presentation.collectAsState

@Composable
fun ErrorPopup(errorPopupViewModel: ErrorPopupViewModel) {
    val popupState by errorPopupViewModel.popUpState.collectAsState()

    popupState?.let { state ->
        AlertDialog(
            onDismissRequest = { state.callback() },
            text = { Text(text = state.message ?: "") },
            confirmButton = {
                TextButton(onClick = { state.callback() }) {
                    Text(text = stringResource(R.string.ok))
                }
            },
        )
    }
}
