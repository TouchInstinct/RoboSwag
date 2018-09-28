package ru.touchin.roboswag.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionManager;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.NoSuchElementException;

import ru.touchin.roboswag.components.views.R;

public class Switcher extends FrameLayout {

    @IdRes
    private final int defaultChild;

    @NonNull
    private Transition transition;

    public Switcher(@NonNull final Context context) {
        this(context, null);
    }

    public Switcher(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Switcher(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.Switcher, defStyleAttr, 0);
        defaultChild = array.getResourceId(R.styleable.Switcher_defaultChild, View.NO_ID);
        final int transitionId = array.getResourceId(R.styleable.Switcher_transition, 0);
        if (transitionId != 0) {
            transition = TransitionInflater.from(context).inflateTransition(transitionId);
        } else {
            transition = new Fade();
        }
        array.recycle();
    }

    @Override
    public void addView(@NonNull final View child, final int index, @Nullable final ViewGroup.LayoutParams params) {
        if (child.getId() == defaultChild || defaultChild == View.NO_ID && getChildCount() == 0) {
            child.setVisibility(View.VISIBLE);
        } else {
            child.setVisibility(View.GONE);
        }
        super.addView(child, index, params);
    }

    @NonNull
    public Transition getTransition() {
        return transition;
    }

    public void setTransition(@NonNull final Transition transition) {
        this.transition = transition;
    }

    public void showChild(@IdRes final int... ids) {
        if (ViewCompat.isLaidOut(this)) {
            TransitionManager.beginDelayedTransition(this, transition);
        }
        boolean found = false;
        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            for (final int id : ids) {
                if (child.getId() == id) {
                    found = true;
                    child.setVisibility(View.VISIBLE);
                } else {
                    child.setVisibility(View.GONE);
                }
            }
        }
        if (!found) {
            throw new NoSuchElementException();
        }
    }

}
