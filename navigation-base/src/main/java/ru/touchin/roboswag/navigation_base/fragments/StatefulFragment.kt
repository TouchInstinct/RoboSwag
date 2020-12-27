package ru.touchin.roboswag.navigation_base.fragments

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import ru.touchin.roboswag.navigation_base.BuildConfig
import ru.touchin.roboswag.navigation_base.extensions.reserialize

open class StatefulFragment<TActivity : FragmentActivity, TState : Parcelable>(
        @LayoutRes layoutRes: Int
) : BaseFragment<TActivity>(layoutRes) {

    companion object {
        private const val BASE_FRAGMENT_STATE_EXTRA = "BASE_FRAGMENT_STATE_EXTRA"

        fun args(state: Parcelable?): Bundle = Bundle().also { it.putParcelable(BASE_FRAGMENT_STATE_EXTRA, state) }

    }

    protected lateinit var state: TState
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        state = savedInstanceState?.getParcelable<TState>(BASE_FRAGMENT_STATE_EXTRA)
                ?: arguments?.getParcelable(BASE_FRAGMENT_STATE_EXTRA)
                        ?: throw IllegalStateException("Fragment state can't be null")

        if (BuildConfig.DEBUG) {
            state = state.reserialize()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BASE_FRAGMENT_STATE_EXTRA, state)
    }

}
