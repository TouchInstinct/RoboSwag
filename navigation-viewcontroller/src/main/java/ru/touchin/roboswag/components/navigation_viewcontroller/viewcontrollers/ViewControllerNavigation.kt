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

package ru.touchin.roboswag.components.navigation_viewcontroller.viewcontrollers

import android.content.Context
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import ru.touchin.roboswag.components.navigation_viewcontroller.fragments.ViewControllerFragment
import ru.touchin.roboswag.navigation_base.FragmentNavigation

/**
 * Created by Gavriil Sitnikov on 07/03/2016.
 * Navigation based on [ViewController]s which are creating by [Fragment]s.
 * So basically it is just [FragmentNavigation] where most of fragments should be inherited from [ViewControllerFragment].
 *
 * @param TActivity Type of activity where [ViewController]s should be showed.
 */
open class ViewControllerNavigation<TActivity : FragmentActivity>(
        context: Context,
        fragmentManager: FragmentManager,
        @IdRes containerViewId: Int,
        transition: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN
) : FragmentNavigation(context, fragmentManager, containerViewId, transition) {

    /**
     * Pushes [ViewController] on top of stack with specific [ViewControllerFragment.getState] and with specific transaction setup.
     *
     * @param viewControllerClass Class of [ViewController] to be pushed;
     * @param state               [Parcelable] of [ViewController]'s fragment;
     * @param addToStack          Flag to add this transaction to the back stack;
     * @param backStackName       Name of [Fragment] in back stack;
     * @param tag                 Optional tag name for the [Fragment];
     * @param transactionSetup    Function to setup transaction before commit. It is useful to specify transition animations or additional info;
     * @param TState              Type of state of fragment.
     */
    fun <TState : Parcelable> pushViewController(
            viewControllerClass: Class<out ViewController<out TActivity, TState>>,
            state: TState,
            addToStack: Boolean = true,
            backStackName: String? = null,
            tag: String? = null,
            transactionSetup: ((FragmentTransaction) -> Unit)? = null
    ) {
        addToStack(
                fragmentClass = ViewControllerFragment::class.java,
                targetFragment = null,
                targetRequestCode = 0,
                addToStack = addToStack,
                args = ViewControllerFragment.args(viewControllerClass, state),
                backStackName = backStackName,
                tag = tag,
                transactionSetup = transactionSetup
        )
    }

    /**
     * Pushes [ViewController] on top of stack with specific [ViewControllerFragment.getState]
     * and with specific [TTargetFragment] and transaction setup.
     *
     * @param viewControllerClass Class of [ViewController] to be pushed;
     * @param targetFragment      [ViewControllerFragment] to be set as target;
     * @param state               [Parcelable] of [ViewController]'s fragment;
     * @param backStackName       Name of [Fragment] in back stack;
     * @param tag                 Optional tag name for the [Fragment];
     * @param transactionSetup    Function to setup transaction before commit. It is useful to specify transition animations or additional info;
     * @param TState              Type of state of fragment;
     * @param TTargetFragment     Type of target fragment.
     */
    fun <TState : Parcelable, TTargetFragment : Fragment> pushViewControllerForResult(
            viewControllerClass: Class<out ViewController<out TActivity, TState>>,
            state: TState,
            targetFragment: TTargetFragment,
            targetRequestCode: Int,
            backStackName: String? = null,
            tag: String? = null,
            transactionSetup: ((FragmentTransaction) -> Unit)? = null
    ) {
        addToStack(
                fragmentClass = ViewControllerFragment::class.java,
                targetFragment = targetFragment,
                targetRequestCode = targetRequestCode,
                addToStack = true,
                args = ViewControllerFragment.args(viewControllerClass, state),
                backStackName = backStackName,
                tag = tag,
                transactionSetup = transactionSetup
        )
    }

    /**
     * Pushes [ViewController] on top of stack with specific [ViewControllerFragment.getState] and with specific transaction setup
     * and with [.TOP_FRAGMENT_TAG_MARK] tag used for simple up/back navigation.
     *
     * @param viewControllerClass Class of [ViewController] to be pushed;
     * @param state               [Parcelable] of [ViewController]'s fragment;
     * @param tag                 Optional tag name for the [Fragment];
     * @param transactionSetup    Function to setup transaction before commit. It is useful to specify transition animations or additional info;
     * @param TState              Type of state of fragment.
     */
    fun <TState : Parcelable> setViewControllerAsTop(
            viewControllerClass: Class<out ViewController<out TActivity, TState>>,
            state: TState,
            addToStack: Boolean = true,
            tag: String? = null,
            transactionSetup: ((FragmentTransaction) -> Unit)? = null
    ) {
        addToStack(
                fragmentClass = ViewControllerFragment::class.java,
                targetFragment = null,
                targetRequestCode = 0,
                addToStack = addToStack,
                args = ViewControllerFragment.args(viewControllerClass, state),
                backStackName = TOP_FRAGMENT_TAG_MARK,
                tag = tag,
                transactionSetup = transactionSetup
        )
    }

    /**
     * Pops all [Fragment]s and places new initial [ViewController] on top of stack
     * with specific [ViewControllerFragment.getState] and specific transaction setup.
     *
     * @param viewControllerClass Class of [ViewController] to be pushed;
     * @param state               [Parcelable] of [ViewController]'s fragment;
     * @param tag                 Optional tag name for the [Fragment];
     * @param transactionSetup    Function to setup transaction before commit. It is useful to specify transition animations or additional info;
     * @param TState              Type of state of fragment.
     */
    fun <TState : Parcelable> setInitialViewController(
            viewControllerClass: Class<out ViewController<out TActivity, TState>>,
            state: TState,
            tag: String? = null,
            transactionSetup: ((FragmentTransaction) -> Unit)? = null
    ) {
        beforeSetInitialActions()
        setViewControllerAsTop(viewControllerClass, state, false, tag, transactionSetup)
    }

}
