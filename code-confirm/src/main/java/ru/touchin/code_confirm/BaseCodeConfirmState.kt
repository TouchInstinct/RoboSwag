package ru.touchin.code_confirm

import ru.touchin.roboswag.mvi_arch.marker.ViewState

abstract class BaseCodeConfirmState(
        open var codeLifetime: String,
        open var isLoadingState: Boolean,
        open var isWrongCode: Boolean,
        open var isExpired: Boolean,
        open var isRefreshCodeLoading: Boolean = false
) : ViewState {

    abstract fun <T : BaseCodeConfirmState> copyWith(updateBlock: T.() -> Unit): T
}
