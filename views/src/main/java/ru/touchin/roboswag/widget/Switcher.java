package ru.touchin.roboswag.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import java.util.NoSuchElementException;

import ru.touchin.roboswag.components.views.R;

public class Switcher extends ViewAnimator {

    @IdRes
    private final int defaultChild;

    public Switcher(@NonNull final Context context) {
        this(context, null);
    }

    public Switcher(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.Switcher, 0, R.style.Switcher);
        setInAnimation(context, array.getResourceId(R.styleable.Switcher_android_inAnimation, View.NO_ID));
        setOutAnimation(context, array.getResourceId(R.styleable.Switcher_android_outAnimation, View.NO_ID));
        defaultChild = array.getResourceId(R.styleable.Switcher_defaultChild, View.NO_ID);
        array.recycle();
    }

    @Override
    public void addView(@NonNull final View child, final int index, @Nullable final ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getId() == defaultChild || defaultChild == View.NO_ID && getChildCount() == 1) {
            child.setVisibility(View.VISIBLE);
        } else {
            child.setVisibility(View.GONE);
        }
    }

    public void showChild(@IdRes final int id) {
        boolean found = false;
        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            if (child.getId() == id) {
                found = true;
                if (child.getVisibility() != View.VISIBLE) {
                    setDisplayedChild(index);
                }
                break;
            }
        }
        if (!found) {
            throw new NoSuchElementException();
        }
    }

}
