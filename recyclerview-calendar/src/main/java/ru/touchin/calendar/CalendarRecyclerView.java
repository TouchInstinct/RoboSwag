/*
 *  Copyright (c) 2016 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
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

package ru.touchin.calendar;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import ru.touchin.roboswag.core.log.Lc;

/**
 * Created by Ilia Kurtov on 17/03/2016.
 * Specific {@link RecyclerView} that works with {@link CalendarAdapter}. It optimizes speed of the calendar.
 */
public class CalendarRecyclerView extends RecyclerView {

    private static final int HEADER_MAX_ELEMENTS_IN_A_ROW = 1;
    private static final int EMPTY_MAX_ELEMENTS_IN_A_ROW = 6;
    private static final int DAY_MAX_ELEMENTS_IN_A_ROW = 7;

    public CalendarRecyclerView(@NonNull final Context context) {
        this(context, null);
    }

    public CalendarRecyclerView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    //ConstructorCallsOverridableMethod: it's OK
    public CalendarRecyclerView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        setupCacheForMonthsOnScreenCount(3);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        setLayoutManager(layoutManager);
        setItemAnimator(null);
    }

    /**
     * Setups recycler cache for smooth scroll without lagging based on month that could be displayed on screen.
     *
     * @param maxMonthOnScreen Maximum months count on screen.
     */
    public void setupCacheForMonthsOnScreenCount(final int maxMonthOnScreen) {
        getRecycledViewPool().setMaxRecycledViews(CalendarAdapter.HEADER_ITEM_TYPE, HEADER_MAX_ELEMENTS_IN_A_ROW * (maxMonthOnScreen + 1));
        getRecycledViewPool().setMaxRecycledViews(CalendarAdapter.EMPTY_ITEM_TYPE, EMPTY_MAX_ELEMENTS_IN_A_ROW * (maxMonthOnScreen * 2 + 1));
        // we need such much views to prevent cache/gap animations of StaggeredGridLayoutManager
        getRecycledViewPool().setMaxRecycledViews(CalendarAdapter.DAY_ITEM_TYPE, DAY_MAX_ELEMENTS_IN_A_ROW * (maxMonthOnScreen * 5 + 1));
        setItemViewCacheSize(0);
    }

    /**
     * Used to set adapter that extends from {@link CalendarAdapter}.
     *
     * @param calendarAdapter Adapter that extends from {@link CalendarAdapter}.
     */
    // This suppress needed for using only specific CalendarAdapter}
    @SuppressWarnings("PMD.UselessOverridingMethod")
    public void setAdapter(@NonNull final CalendarAdapter calendarAdapter) {
        super.setAdapter(calendarAdapter);
    }

    @Override
    @Deprecated
    public void setAdapter(@NonNull final Adapter adapter) {
        Lc.assertion("Unsupported adapter class. Use CalendarAdapter instead.");
    }

}
