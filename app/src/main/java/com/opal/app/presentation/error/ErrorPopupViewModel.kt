package com.opal.app.presentation.error

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class ErrorPopupViewModel :
    ViewModel(),
    KoinComponent {
    val popUpState: MutableStateFlow<ErrorPopupState?> = MutableStateFlow(null)

    fun showErrorMessage(message: String) {
        popUpState.update {
            ErrorPopupState(
                message = message,
                callback = {
                    popUpState.value = null
                },
            )
        }
    }

    data class ErrorPopupState(
        val message: String?,
        val callback: () -> Unit = {},
    )
}
