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

package ru.touchin.roboswag.components.navigation.fragments;

import android.animation.Animator;
import android.annotation.SuppressLint;
import androidx.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import java.lang.reflect.Constructor;

import ru.touchin.roboswag.components.navigation.BuildConfig;
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController;
import ru.touchin.roboswag.core.log.Lc;
import ru.touchin.roboswag.core.log.LcGroup;
import ru.touchin.roboswag.core.utils.ShouldNotHappenException;

/**
 * Created by Gavriil Sitnikov on 21/10/2015.
 * Fragment instantiated in specific activity of {@link TActivity} type that is holding {@link ViewController} inside.
 *
 * @param <TState>    Type of object which is representing it's fragment state;
 * @param <TActivity> Type of {@link FragmentActivity} where fragment could be attached to.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class ViewControllerFragment<TActivity extends FragmentActivity, TState extends Parcelable> extends Fragment {

    private static final String VIEW_CONTROLLER_CLASS_EXTRA = "VIEW_CONTROLLER_CLASS_EXTRA";
    private static final String VIEW_CONTROLLER_STATE_EXTRA = "VIEW_CONTROLLER_STATE_EXTRA";

    private static long acceptableUiCalculationTime = 100;

    /**
     * Sets acceptable UI calculation time so there will be warnings in logs if ViewController's inflate/layout actions will take more than that time.
     * It's 100ms by default.
     */
    public static void setAcceptableUiCalculationTime(final long acceptableUiCalculationTime) {
        ViewControllerFragment.acceptableUiCalculationTime = acceptableUiCalculationTime;
    }

    @NonNull
    private static <T extends Parcelable> T reserialize(@NonNull final T parcelable) {
        Parcel parcel = Parcel.obtain();
        parcel.writeParcelable(parcelable, 0);
        final byte[] serializableBytes = parcel.marshall();
        parcel.recycle();
        parcel = Parcel.obtain();
        parcel.unmarshall(serializableBytes, 0, serializableBytes.length);
        parcel.setDataPosition(0);
        final T result = parcel.readParcelable(Thread.currentThread().getContextClassLoader());
        parcel.recycle();
        return result;
    }

    /**
     * Creates {@link Bundle} which will store state.
     *
     * @param state State to use into ViewController.
     * @return Returns bundle with state inside.
     */
    @NonNull
    public static Bundle args(@NonNull final Class<? extends ViewController> viewControllerClass, @Nullable final Parcelable state) {
        final Bundle result = new Bundle();
        result.putSerializable(VIEW_CONTROLLER_CLASS_EXTRA, viewControllerClass);
        result.putParcelable(VIEW_CONTROLLER_STATE_EXTRA, state);
        return result;
    }

    @Nullable
    private ViewController viewController;
    private Class<ViewController<TActivity, TState>> viewControllerClass;
    private TState state;
    @Nullable
    private ActivityResult pendingActivityResult;

    private boolean appeared;

    /**
     * Returns specific {@link Parcelable} which contains state of fragment and it's {@link ViewController}.
     *
     * @return Object represents state.
     */
    @NonNull
    public TState getState() {
        return state;
    }

    @NonNull
    public Class<ViewController<TActivity, TState>> getViewControllerClass() {
        return viewControllerClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        viewControllerClass = (Class<ViewController<TActivity, TState>>) getArguments().getSerializable(VIEW_CONTROLLER_CLASS_EXTRA);
        state = savedInstanceState != null
                ? savedInstanceState.getParcelable(VIEW_CONTROLLER_STATE_EXTRA)
                : (getArguments() != null ? getArguments().getParcelable(VIEW_CONTROLLER_STATE_EXTRA) : null);
        if (state != null) {
            if (BuildConfig.DEBUG) {
                state = reserialize(state);
            }
        } else {
            Lc.assertion("State is required and null");
        }
    }

    @NonNull
    @Override
    public final View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState
    ) {
        viewController = createViewController(
                new ViewController.CreationContext(requireActivity(), this, inflater, container), savedInstanceState);
        viewController.onCreate();
        return viewController.getView();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (viewController != null && pendingActivityResult != null) {
            viewController.onActivityResult(pendingActivityResult.requestCode, pendingActivityResult.resultCode, pendingActivityResult.data);
            pendingActivityResult = null;
        }
    }

    @Nullable
    @Override
    public Animation onCreateAnimation(final int transit, final boolean enter, final int nextAnim) {
        if (viewController != null) {
            return viewController.onCreateAnimation(transit, enter, nextAnim);
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public Animator onCreateAnimator(final int transit, final boolean enter, final int nextAnim) {
        if (viewController != null) {
            return viewController.onCreateAnimator(transit, enter, nextAnim);
        } else {
            return null;
        }
    }

    @Override
    public void onViewStateRestored(@Nullable final Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (viewController != null) {
            viewController.onViewStateRestored(savedInstanceState);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStart() {
        super.onStart();
        if (!appeared && isMenuVisible()) {
            onAppear();
        }
        if (viewController != null) {
            viewController.onStart();
        }
    }

    /**
     * Called when fragment is moved in started state and it's {@link #isMenuVisible()} sets to true.
     * Usually it is indicating that user can't see fragment on screen and useful to track analytics events.
     */
    @CallSuper
    protected void onAppear() {
        appeared = true;
        if (viewController != null) {
            viewController.onAppear();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewController != null) {
            viewController.onResume();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (viewController != null) {
            viewController.onLowMemory();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (viewController != null) {
            viewController.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        return (viewController != null && viewController.onOptionsItemSelected(item)) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (viewController != null) {
            viewController.onPause();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (viewController != null) {
            viewController.onSaveInstanceState(savedInstanceState);
        }
        savedInstanceState.putParcelable(VIEW_CONTROLLER_STATE_EXTRA, state);
    }

    /**
     * Called when fragment is moved in stopped state or it's {@link #isMenuVisible()} sets to false.
     * Usually it is indicating that user can't see fragment on screen and useful to track analytics events.
     */
    @CallSuper
    protected void onDisappear() {
        appeared = false;
        if (viewController != null) {
            viewController.onDisappear();
        }
    }

    @Override
    public void onStop() {
        if (appeared) {
            onDisappear();
        }
        if (viewController != null) {
            viewController.onStop();
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (viewController != null) {
            viewController.onDestroy();
            viewController = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        if (viewController != null) {
            viewController.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        if (viewController != null) {
            viewController.onActivityResult(requestCode, resultCode, data);
        } else {
            pendingActivityResult = new ActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void setMenuVisibility(final boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getActivity() != null && getView() != null) {
            final boolean started = getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
            if (!appeared && menuVisible && started) {
                onAppear();
            }
            if (appeared && (!menuVisible || !started)) {
                onDisappear();
            }
        }
    }

    @NonNull
    private ViewController createViewController(
            @NonNull final ViewController.CreationContext creationContext,
            @Nullable final Bundle savedInstanceState
    ) {
        if (viewControllerClass.getConstructors().length != 1) {
            throw new ShouldNotHappenException("There should be single constructor for " + viewControllerClass);
        }
        final Constructor<?> constructor = viewControllerClass.getConstructors()[0];
        final long creationTime = BuildConfig.DEBUG ? SystemClock.elapsedRealtime() : 0;
        try {
            switch (constructor.getParameterTypes().length) {
                case 2:
                    return (ViewController) constructor.newInstance(creationContext, savedInstanceState);
                case 3:
                    return (ViewController) constructor.newInstance(this, creationContext, savedInstanceState);
                default:
                    throw new ShouldNotHappenException("Wrong constructor parameters count: " + constructor.getParameterTypes().length);
            }
        } catch (@NonNull final Exception exception) {
            throw new ShouldNotHappenException(exception);
        } finally {
            checkCreationTime(creationTime);
        }
    }

    private void checkCreationTime(final long creationTime) {
        if (BuildConfig.DEBUG) {
            final long creationPeriod = SystemClock.elapsedRealtime() - creationTime;
            if (creationPeriod > acceptableUiCalculationTime) {
                LcGroup.UI_METRICS.w("Creation of %s took too much: %dms", viewControllerClass, creationPeriod);
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + " ViewController: " + getViewControllerClass();
    }

    private static class ActivityResult {
        public final int requestCode;
        public final int resultCode;
        @Nullable
        public final Intent data;

        ActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }
    }

}
