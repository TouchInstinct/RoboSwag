package ru.touchin.code_confirm

import android.os.CountDownTimer
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.touchin.code_confirm.LifeTimer.Companion.getFormattedCodeLifetimeString
import ru.touchin.lifecycle.extensions.toImmutable
import ru.touchin.lifecycle.livedata.SingleLiveEvent
import ru.touchin.roboswag.mvi_arch.core.MviViewModel
import ru.touchin.roboswag.mvi_arch.marker.ViewAction

@SuppressWarnings("detekt.TooGenericExceptionCaught")
abstract class BaseCodeConfirmViewModel<NavArgs : Parcelable, Action : ViewAction, State : BaseCodeConfirmState>(
        initialState: State,
        savedStateHandle: SavedStateHandle
) : MviViewModel<NavArgs, Action, State>(initialState, savedStateHandle) {

    /** [requireCodeId] uses for auto-filling */
    protected open var requireCodeId: String? = null

    private var timer: CountDownTimer? = null

    private var currentConfirmationCode: String? = null

    private val _updateCodeEvent = SingleLiveEvent<String>()
    val updateCodeEvent = _updateCodeEvent.toImmutable()

    init {
        _state.value = currentState.copyWith {
            codeLifetime = getFormattedCodeLifetimeString(getTimerDuration().toLong())
        }

        startTimer(seconds = getTimerDuration())
    }

    protected abstract fun getTimerDuration(): Int
    protected abstract suspend fun requestNewCode(): BaseCodeResponse
    protected abstract suspend fun requestCodeConfirmation(code: String)

    protected open fun onRefreshCodeRequestError(e: Throwable) {}
    protected open fun onCodeConfirmationError(e: Throwable) {}
    protected open fun onSuccessCodeConfirmation(code: String) {}

    override fun dispatchAction(action: Action) {
        super.dispatchAction(action)
        when (action) {
            is CodeConfirmAction -> {
                if (currentState.needSendCode) confirmCode()
            }
            is GetRefreshCodeAction -> {
                getRefreshCode()
            }
            is UpdatedCodeInputAction -> {
                val confirmationCodeChanged = currentConfirmationCode != action.code

                _state.value = currentState.copyWith {
                    isWrongCode = isWrongCode && !confirmationCodeChanged
                    needSendCode = confirmationCodeChanged
                }
                currentConfirmationCode = action.code
            }
        }
    }

    protected open fun startTimer(seconds: Int) {
        timer?.cancel()
        timer = LifeTimer(
                seconds = seconds,
                tickAction = { millis ->
                    _state.value = currentState.copyWith {
                        codeLifetime = getFormattedCodeLifetimeString(millis)
                        isExpired = false
                    }
                },
                finishAction = {
                    _state.value = currentState.copyWith {
                        isExpired = true
                    }
                }
        )
        timer?.start()
    }

    protected open fun getRefreshCode() {
        viewModelScope.launch {
            try {
                _state.value = currentState.copyWith {
                    isRefreshCodeLoading = true
                    isWrongCode = false
                }
                val confirmationData = requestNewCode()
                requireCodeId = confirmationData.codeId

                startTimer(seconds = confirmationData.codeLifetime)
            } catch (throwable: Throwable) {
                _state.value = currentState.copyWith { needSendCode = false }
                onRefreshCodeRequestError(throwable)
            } finally {
                _state.value = currentState.copyWith { isRefreshCodeLoading = false }
            }
        }
    }

    protected open fun confirmCode() {
        currentConfirmationCode?.let { code ->
            _state.value = currentState.copyWith { isLoadingState = true }
            viewModelScope.launch {
                try {
                    requestCodeConfirmation(code)
                    onSuccessCodeConfirmation(code)
                } catch (throwable: Throwable) {
                    _state.value = currentState.copyWith { needSendCode = false }
                    onCodeConfirmationError(throwable)
                } finally {
                    _state.value = currentState.copyWith { isLoadingState = false }
                }
            }
        }
    }

    protected open fun autofillCode(code: String, codeId: String? = null) {
        if (codeId == requireCodeId) {
            _updateCodeEvent.setValue(code)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

}
