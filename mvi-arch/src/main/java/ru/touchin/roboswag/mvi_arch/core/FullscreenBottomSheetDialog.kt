package ru.touchin.roboswag.mvi_arch.core

import android.app.Dialog
import android.os.Parcelable
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import ru.touchin.roboswag.components.utils.setResizableListener
import ru.touchin.roboswag.mvi_arch.marker.ViewAction
import ru.touchin.roboswag.mvi_arch.marker.ViewState

abstract class FullscreenBottomSheetDialog<NavArgs, State, Action, VM>(
        @LayoutRes layoutId: Int
) : MviBottomSheet<NavArgs, State, Action, VM>(layoutId)
        where NavArgs : Parcelable,
              Action : ViewAction,
              State : ViewState,
              VM : MviViewModel<NavArgs, Action, State> {

    protected var bottomSheet: FrameLayout? = null

    override fun onStart() {
        super.onStart()

        bottomSheet = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        dialog.setResizableListener()
    }

}
