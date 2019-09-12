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

package ru.touchin.roboswag.components.navigation_new

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.components.navigation_new.fragments.BaseFragment
import ru.touchin.roboswag.components.navigation_new.viewcontrollers.EmptyState
import kotlin.reflect.KClass

/**
 * Created by Gavriil Sitnikov on 07/03/2016.
 * Navigation which is controlling fragments on activity using [FragmentManager].
 * Basically there are 4 main actions to add fragments to activity.
 * 1) [.setInitial] means to set fragment on top and remove all previously added fragments from stack;
 * 2) [.push] means to simply add fragment on top of the stack;
 * 3) [.setAsTop] means to push fragment on top of the stack with specific [.TOP_FRAGMENT_TAG_MARK] tag.
 * It is useful to realize up/back navigation: if [.up] method will be called then stack will go to nearest fragment with TOP tag.
 * If [.back] method will be called then stack will go to previous fragment.
 * Usually such logic using to set as top fragments from sidebar and show hamburger when some of them appeared;
 * 4) [.pushForResult] means to push fragment with target fragment. It is also adding [.WITH_TARGET_FRAGMENT_TAG_MARK] tag.
 * Also if such up/back navigation logic is not OK then [.backTo] method could be used with any condition to back to.
 * In that case in any stack-change method it is allowed to setup fragment transactions.
 */
open class FragmentNavigation(
        private val context: Context,
        private val fragmentManager: FragmentManager,
        @IdRes private val containerViewId: Int,
        private val transition: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN
) {

    companion object {
        const val TOP_FRAGMENT_TAG_MARK = "TOP_FRAGMENT"
    }

    /**
     * Returns if last fragment in stack is top (added by [.setAsTop] or [.setInitial]) like fragment from sidebar menu.
     *
     * @return True if last fragment on stack has TOP_FRAGMENT_TAG_MARK.
     */
    fun isCurrentFragmentTop(): Boolean = if (fragmentManager.backStackEntryCount == 0) {
        true
    } else {
        fragmentManager
                .getBackStackEntryAt(fragmentManager.backStackEntryCount - 1)
                .name
                ?.contains(TOP_FRAGMENT_TAG_MARK) ?: false
    }

    /**
     * Allowed to react on [android.app.Activity]'s menu item selection.
     *
     * @param item Selected menu item;
     * @return True if reaction fired.
     */
    fun onOptionsItemSelected(item: MenuItem): Boolean = item.itemId == android.R.id.home && back()

    /**
     * Base method which is adding fragment to stack.
     *
     * @param fragmentClass    Class of [Fragment] to instantiate;
     * @param targetFragment   Target fragment to be set as [Fragment.getTargetFragment] of instantiated [Fragment];
     * @param addToStack       Flag to add this transaction to the back stack;
     * @param args             Bundle to be set as [Fragment.getArguments] of instantiated [Fragment];
     * @param backStackName    Name of [Fragment] in back stack;
     * @param transactionSetup Function to setup transaction before commit. It is useful to specify transition animations or additional info.
     */
    fun addToStack(
            fragmentClass: Class<out Fragment>,
            targetFragment: Fragment?,
            targetRequestCode: Int,
            addToStack: Boolean,
            args: Bundle?,
            backStackName: String?,
            transactionSetup: ((FragmentTransaction) -> Unit)?
    ) {
        if (fragmentManager.isDestroyed) {
            Lc.assertion("FragmentManager is destroyed")
            return
        }

        val fragment = Fragment.instantiate(context, fragmentClass.name, args)
        fragment.setTargetFragment(targetFragment, targetRequestCode)

        val fragmentTransaction = fragmentManager.beginTransaction()
        transactionSetup?.invoke(fragmentTransaction)
        fragmentTransaction.replace(containerViewId, fragment, null)
        if (addToStack) {
            fragmentTransaction
                    .addToBackStack(backStackName)
                    .setTransition(transition)
        }
        fragmentTransaction
                .setPrimaryNavigationFragment(fragment)
                .commit()
    }

    /**
     * Simply calls [FragmentManager.popBackStack].
     *
     * @return True if it have back to some entry in stack.
     */
    fun back(): Boolean {
        if (fragmentManager.backStackEntryCount >= 1) {
            fragmentManager.popBackStack()
            return true
        }
        return false
    }

    /**
     * Backs to fragment with specific [.TOP_FRAGMENT_TAG_MARK] tag.
     * This tag is adding if fragment added to stack via [.setInitial] or [.setAsTop] methods.
     * It can be used to create simple up/back navigation.
     *
     * @return True if it have back to some entry in stack.
     */
    fun up(name: String? = null, inclusive: Boolean = false) {
        fragmentManager.popBackStack(name, if (inclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0)
    }

    /**
     * Pushes [Fragment] on top of stack with specific arguments and transaction setup.
     *
     * @param fragmentClass    Class of [Fragment] to instantiate;
     * @param args             Bundle to be set as [Fragment.getArguments] of instantiated [Fragment];
     * @param transactionSetup Function to setup transaction before commit. It is useful to specify transition animations or additional info.
     */
    fun push(
            fragmentClass: Class<out Fragment>,
            args: Bundle? = null,
            addToStack: Boolean = true,
            backStackName: String? = null,
            transactionSetup: ((FragmentTransaction) -> Unit)? = null
    ) {
        addToStack(fragmentClass, null, 0, addToStack, args, backStackName, transactionSetup)
    }

    /**
     * Pushes [Fragment] on top of stack with specific target fragment, arguments and transaction setup.
     *
     * @param fragmentClass    KClass of [Fragment] to instantiate;
     * @param state            State of instantiated [Fragment];
     * @param transactionSetup Function to setup transaction before commit. It is useful to specify transition animations or additional info.
     */
    fun <T: Parcelable> push(
            fragmentClass: KClass<out BaseFragment<*, out T>>,
            state: T? = null,
            addToStack: Boolean = true,
            backStackName: String? = null,
            transactionSetup: ((FragmentTransaction) -> Unit)? = null
    ) {
        push(fragmentClass.java, BaseFragment.args(state ?: EmptyState), addToStack, backStackName, transactionSetup)
    }

    /**
     * Pushes [Fragment] on top of stack with specific target fragment, arguments and transaction setup.
     *
     * @param fragmentClass    Class of [Fragment] to instantiate;
     * @param targetFragment   Target fragment to be set as [Fragment.getTargetFragment] of instantiated [Fragment];
     * @param args             Bundle to be set as [Fragment.getArguments] of instantiated [Fragment];
     * @param transactionSetup Function to setup transaction before commit. It is useful to specify transition animations or additional info.
     */
    fun pushForResult(
            fragmentClass: Class<out Fragment>,
            targetFragment: Fragment,
            targetRequestCode: Int,
            args: Bundle? = null,
            transactionSetup: ((FragmentTransaction) -> Unit)? = null
    ) {
        addToStack(
                fragmentClass,
                targetFragment,
                targetRequestCode,
                true,
                args,
                null,
                transactionSetup
        )
    }

    /**
     * Pushes [Fragment] on top of stack with specific target fragment, arguments and transaction setup.
     *
     * @param fragmentClass    KClass of [Fragment] to instantiate;
     * @param targetFragment   Target fragment to be set as [Fragment.getTargetFragment] of instantiated [Fragment];
     * @param state            State of instantiated [Fragment];
     * @param transactionSetup Function to setup transaction before commit. It is useful to specify transition animations or additional info.
     */
    fun <T: Parcelable> pushForResult(
            fragmentClass: KClass<out BaseFragment<*, out T>>,
            targetFragment: Fragment,
            targetRequestCode: Int,
            state: T? = null,
            transactionSetup: ((FragmentTransaction) -> Unit)? = null
    ) {
        pushForResult(fragmentClass.java, targetFragment, targetRequestCode, BaseFragment.args(state ?: EmptyState), transactionSetup)
    }

    /**
     * Pushes [Fragment] on top of stack with specific transaction setup, arguments
     * and with [.TOP_FRAGMENT_TAG_MARK] tag used for simple up/back navigation.
     *
     * @param fragmentClass    Class of [Fragment] to instantiate;
     * @param args             Bundle to be set as [Fragment.getArguments] of instantiated [Fragment];
     * @param transactionSetup Function to setup transaction before commit. It is useful to specify transition animations or additional info.
     */
    fun setAsTop(
            fragmentClass: Class<out Fragment>,
            args: Bundle? = null,
            addToStack: Boolean = true,
            transactionSetup: ((FragmentTransaction) -> Unit)? = null
    ) {
        addToStack(fragmentClass, null, 0, addToStack, args, TOP_FRAGMENT_TAG_MARK, transactionSetup)
    }

    /**
     * Pops all [Fragment]s and places new initial [Fragment] on top of stack with specific transaction setup and arguments.
     *
     * @param fragmentClass    Class of [Fragment] to instantiate;
     * @param args             Bundle to be set as [Fragment.getArguments] of instantiated [Fragment];
     * @param transactionSetup Function to setup transaction before commit. It is useful to specify transition animations or additional info.
     */
    @JvmOverloads
    fun setInitial(
            fragmentClass: Class<out Fragment>,
            args: Bundle? = null,
            transactionSetup: ((FragmentTransaction) -> Unit)? = null
    ) {
        beforeSetInitialActions()
        setAsTop(fragmentClass, args, false, transactionSetup)
    }

    /**
     * Pops all [Fragment]s and places new initial [Fragment] on top of stack with specific transaction setup and arguments.
     *
     * @param fragmentClass    Class of [Fragment] to instantiate;
     * @param state            State of instantiated [Fragment];
     * @param transactionSetup Function to setup transaction before commit. It is useful to specify transition animations or additional info.
     */
    fun <T: Parcelable> setInitial(
            fragmentClass: KClass<out BaseFragment<*, out T>>,
            state: T? = null,
            transactionSetup: ((FragmentTransaction) -> Unit)? = null
    ) {
        beforeSetInitialActions()
        setAsTop(fragmentClass.java, BaseFragment.args(state ?: EmptyState), false, transactionSetup)
    }

    /**
     * Method calls every time before initial [Fragment] will be placed.
     */
    protected fun beforeSetInitialActions() {
        if (fragmentManager.isDestroyed) {
            Lc.assertion("FragmentManager is destroyed")
            return
        }

        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

}
