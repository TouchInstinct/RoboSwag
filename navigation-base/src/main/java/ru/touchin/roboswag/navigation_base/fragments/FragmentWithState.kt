package ru.touchin.roboswag.navigation_base.fragments

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import ru.touchin.roboswag.navigation_base.BuildConfig

open class FragmentWithState<TActivity : FragmentActivity, TState : Parcelable>(
        @LayoutRes layoutRes: Int
) : BaseFragment<TActivity>(layoutRes) {

    companion object {
        private const val BASE_FRAGMENT_STATE_EXTRA = "BASE_FRAGMENT_STATE_EXTRA"

        fun args(state: Parcelable?): Bundle = Bundle().also { it.putParcelable(BASE_FRAGMENT_STATE_EXTRA, state) }

        // This method used to check unique state of each fragment.
        // If two fragments share same class for state, you should not pass state instance of current fragment to the one you transition to
        private fun <T : Parcelable> reserialize(parcelable: T): T {
            var parcel = Parcel.obtain()
            parcel.writeParcelable(parcelable, 0)
            val serializableBytes = parcel.marshall()
            parcel.recycle()
            parcel = Parcel.obtain()
            parcel.unmarshall(serializableBytes, 0, serializableBytes.size)
            parcel.setDataPosition(0)
            val result = parcel.readParcelable<T>(Thread.currentThread().contextClassLoader) ?: throw IllegalStateException("It must not be null")
            parcel.recycle()
            return result
        }
    }

    protected lateinit var state: TState
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        state = savedInstanceState?.getParcelable<TState>(BASE_FRAGMENT_STATE_EXTRA)
                ?: arguments?.getParcelable(BASE_FRAGMENT_STATE_EXTRA)
                        ?: throw IllegalStateException("Fragment state can't be null")

        if (BuildConfig.DEBUG) {
            state = reserialize(state)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BASE_FRAGMENT_STATE_EXTRA, state)
    }

}
