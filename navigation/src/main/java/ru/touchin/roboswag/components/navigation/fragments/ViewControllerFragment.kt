/*
 *  Copyright (c) 2015 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
 *
 *  This file is part of RoboSwag library.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package ru.touchin.roboswag.components.navigation.fragments

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import ru.touchin.roboswag.components.navigation.BuildConfig
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController
import ru.touchin.roboswag.core.log.LcGroup
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

/**
 * Created by Gavriil Sitnikov on 21/10/2015.
 * Fragment instantiated in specific activity of [TActivity] type that is holding [ViewController] inside.
 *
 * @param <TState>    Type of object which is representing it's fragment state;
 * @param <TActivity> Type of [FragmentActivity] where fragment could be attached to.
</TActivity></TState> */
@Suppress("UNCHECKED_CAST")
class ViewControllerFragment<TActivity : FragmentActivity, TState : Parcelable> : Fragment() {

    companion object {

        private const val VIEW_CONTROLLER_CLASS_EXTRA = "VIEW_CONTROLLER_CLASS_EXTRA"
        private const val VIEW_CONTROLLER_STATE_EXTRA = "VIEW_CONTROLLER_STATE_EXTRA"

        private var acceptableUiCalculationTime: Long = 100

        /**
         * Sets acceptable UI calculation time so there will be warnings in logs if ViewController's inflate/layout actions will take more than that time.
         * It's 100ms by default.
         */
        fun setAcceptableUiCalculationTime(acceptableUiCalculationTime: Long) {
            ViewControllerFragment.acceptableUiCalculationTime = acceptableUiCalculationTime
        }

        /**
         * Creates [Bundle] which will store state.
         *
         * @param state State to use into ViewController.
         * @return Returns bundle with state inside.
         */
        fun args(viewControllerClass: Class<out ViewController<*, *>>, state: Parcelable?): Bundle = Bundle().apply {
            putSerializable(VIEW_CONTROLLER_CLASS_EXTRA, viewControllerClass)
            putParcelable(VIEW_CONTROLLER_STATE_EXTRA, state)
        }

        private fun <T : Parcelable> reserialize(parcelable: T): T {
            var parcel = Parcel.obtain()
            parcel.writeParcelable(parcelable, 0)
            val serializableBytes = parcel.marshall()
            parcel.recycle()
            parcel = Parcel.obtain()
            parcel.unmarshall(serializableBytes, 0, serializableBytes.size)
            parcel.setDataPosition(0)
            val result = parcel.readParcelable<T>(Thread.currentThread().contextClassLoader) ?: throw ShouldNotHappenException("It must not be null")
            parcel.recycle()
            return result
        }
    }

    lateinit var state: TState
        private set

    private var viewController: ViewController<out TActivity, out TState>? = null
    private var pendingActivityResult: ActivityResult? = null
    private var appeared: Boolean = false
    private val viewControllerClass: Class<ViewController<TActivity, TState>> = arguments?.getSerializable(VIEW_CONTROLLER_CLASS_EXTRA)
            as? Class<ViewController<TActivity, TState>> ?: throw ShouldNotHappenException("View controller class must be not-null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        state = when {
            savedInstanceState != null -> {
                savedInstanceState.getParcelable(VIEW_CONTROLLER_STATE_EXTRA) ?: throw ShouldNotHappenException("State is required and null")
            }
            arguments != null -> {
                arguments?.getParcelable(VIEW_CONTROLLER_STATE_EXTRA) ?: throw ShouldNotHappenException("State is required and null")
            }
            else -> throw ShouldNotHappenException("State is required and null")
        }

        if (BuildConfig.DEBUG) {
            state = reserialize(state)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val newViewController = createViewController(
                ViewController.CreationContext(requireActivity(), this, inflater, container),
                savedInstanceState
        )
        viewController = newViewController
        newViewController.onCreate()
        return newViewController.view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewController != null && pendingActivityResult != null) {
            viewController!!.onActivityResult(pendingActivityResult!!.requestCode, pendingActivityResult!!.resultCode, pendingActivityResult!!.data)
            pendingActivityResult = null
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? = viewController?.onCreateAnimation(transit, enter, nextAnim)

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? = viewController?.onCreateAnimator(transit, enter, nextAnim)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        viewController?.onViewStateRestored(savedInstanceState)
    }

    @SuppressLint("RestrictedApi")
    override fun onStart() {
        super.onStart()
        if (!appeared && isMenuVisible) {
            onAppear()
        }
        viewController?.onStart()
    }

    /**
     * Called when fragment is moved in started state and it's [.isMenuVisible] sets to true.
     * Usually it is indicating that user can't see fragment on screen and useful to track analytics events.
     */
    @CallSuper
    protected fun onAppear() {
        appeared = true
        viewController?.onAppear()
    }

    override fun onResume() {
        super.onResume()
        viewController?.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        viewController?.onLowMemory()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        viewController?.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = viewController != null
            && viewController!!.onOptionsItemSelected(item)
            || super.onOptionsItemSelected(item)

    override fun onPause() {
        super.onPause()
        viewController?.onPause()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        viewController?.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putParcelable(VIEW_CONTROLLER_STATE_EXTRA, state)
    }

    /**
     * Called when fragment is moved in stopped state or it's [.isMenuVisible] sets to false.
     * Usually it is indicating that user can't see fragment on screen and useful to track analytics events.
     */
    @CallSuper
    protected fun onDisappear() {
        appeared = false
        viewController?.onDisappear()
    }

    override fun onStop() {
        if (appeared) {
            onDisappear()
        }
        viewController?.onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        if (viewController != null) {
            viewController!!.onDestroy()
            viewController = null
        }
        super.onDestroyView()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        viewController?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewController?.onActivityResult(requestCode, resultCode, data) ?: let {
            pendingActivityResult = ActivityResult(requestCode, resultCode, data)
        }

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (activity != null && view != null) {
            val started = lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
            if (!appeared && menuVisible && started) {
                onAppear()
            }
            if (appeared && (!menuVisible || !started)) {
                onDisappear()
            }
        }
    }

    private fun createViewController(
            creationContext: ViewController.CreationContext,
            savedInstanceState: Bundle?
    ): ViewController<out TActivity, out TState> {
        if (viewControllerClass.constructors.size != 1) {
            throw ShouldNotHappenException("There should be single constructor for $viewControllerClass")
        }
        val constructor = viewControllerClass.constructors[0]
        try {
            return when (constructor.parameterTypes.size) {
                2 -> constructor.newInstance(creationContext, savedInstanceState) as ViewController<out TActivity, out TState>
                3 -> constructor.newInstance(this, creationContext, savedInstanceState) as ViewController<out TActivity, out TState>
                else -> throw ShouldNotHappenException("Wrong constructor parameters count: ${constructor.parameterTypes.size}")
            }
        } catch (exception: Exception) {
            throw ShouldNotHappenException(exception)
        } finally {
            val creationTime = if (BuildConfig.DEBUG) SystemClock.elapsedRealtime() else 0
            checkCreationTime(creationTime)
        }
    }

    private fun checkCreationTime(creationTime: Long) {
        if (BuildConfig.DEBUG) {
            val creationPeriod = SystemClock.elapsedRealtime() - creationTime
            if (creationPeriod > acceptableUiCalculationTime) {
                LcGroup.UI_METRICS.w("Creation of %s took too much: %dms", viewControllerClass, creationPeriod)
            }
        }
    }

    override fun toString(): String = "${super.toString()} ViewController: $viewControllerClass"

    private data class ActivityResult(val requestCode: Int, val resultCode: Int, val data: Intent?)

}
