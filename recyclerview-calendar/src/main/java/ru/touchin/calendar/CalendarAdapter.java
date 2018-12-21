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

import android.view.ViewGroup;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by Ilia Kurtov on 17/03/2016.
 * Adapter for Calendar view. Use with {@link CalendarRecyclerView}.
 *
 * @param <TDayViewHolder>    Type of ViewHolders of a day with a date;
 * @param <THeaderViewHolder> Type of ViewHolders of a months header;
 * @param <TEmptyViewHolder>  Type of ViewHolders of an empty cell.
 */
public abstract class CalendarAdapter<TDayViewHolder extends RecyclerView.ViewHolder, THeaderViewHolder extends RecyclerView.ViewHolder,
        TEmptyViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int HEADER_ITEM_TYPE = 0;
    public static final int EMPTY_ITEM_TYPE = 1;
    public static final int DAY_ITEM_TYPE = 2;

    public static final int MONTHS_IN_YEAR = 12;
    public static final long ONE_DAY_LENGTH = TimeUnit.DAYS.toMillis(1);

    private List<CalendarItem> calendarItems;
    @Nullable
    private Integer startSelectionPosition;
    @Nullable
    private Integer endSelectionPosition;
    @Nullable
    private String[] monthsNames;

    /**
     * Constructor that takes all necessary data to initialize.
     *
     * @param startDate   First date in the calendar range;
     * @param endDate     Last date (not inclusive) in the calendar range;
     * @param monthsNames String array of months names where #0 is January and #11 is December.
     */
    public CalendarAdapter(@NonNull final DateTime startDate, @NonNull final DateTime endDate, @Nullable final String... monthsNames) {
        super();
        if (monthsNames != null && monthsNames.length == MONTHS_IN_YEAR) {
            this.monthsNames = monthsNames;
        }
        updateCalendarItems(startDate, endDate);
    }

    public final void updateCalendarItems(@NonNull final DateTime startDate, @NonNull final DateTime endDate) {
        calendarItems = CalendarUtils.fillRanges(startDate, endDate);
        if (calendarItems.isEmpty()) {
            throw new IllegalStateException("There is no items in calendar with startDate: " + DateTimeFormat.fullDate().print(startDate)
                    + ", and endDate: " + DateTimeFormat.fullDate().print(endDate));
        }
    }

    /**
     * Set selected dates range in calendar. Call this method before attaching this adapter to {@link CalendarRecyclerView}.
     *
     * @param startSelectionDate First date that should be selected;
     * @param endSelectionDate   Last date that should be selected (inclusive).
     */
    public void setSelectedRange(@Nullable final DateTime startSelectionDate, @Nullable final DateTime endSelectionDate) {
        startSelectionPosition = startSelectionDate != null
                ? CalendarUtils.findPositionByDate(calendarItems, startSelectionDate.withTimeAtStartOfDay().getMillis())
                : null;
        endSelectionPosition = endSelectionDate != null
                ? CalendarUtils.findPositionByDate(calendarItems, endSelectionDate.withTimeAtStartOfDay().getMillis())
                : null;

        notifySelectedDaysChanged();
    }

    /**
     * Method finds the number of the first cell of selected range.
     *
     * @param departure Pass true if {@link CalendarRecyclerView} connected with this adapter should select departure (pass true) date
     *                  or arrival (pass false).
     * @return position of the cell to scroll to at the calendar view opening.
     */
    @Nullable
    public Integer getPositionToScroll(final boolean departure) {
        if (departure && startSelectionPosition != null) {
            return CalendarUtils.findPositionOfSelectedMonth(calendarItems, startSelectionPosition);
        }
        if (!departure && endSelectionPosition != null) {
            return CalendarUtils.findPositionOfSelectedMonth(calendarItems, endSelectionPosition);
        }
        if (!departure && startSelectionPosition != null) {
            return CalendarUtils.findPositionOfSelectedMonth(calendarItems, startSelectionPosition);
        }
        return null;
    }

    private void notifySelectedDaysChanged() {
        if (startSelectionPosition == null && endSelectionPosition == null) {
            return;
        }
        if (startSelectionPosition == null) {
            notifyItemRangeChanged(endSelectionPosition, 1);
            return;
        }
        if (endSelectionPosition == null) {
            notifyItemRangeChanged(startSelectionPosition, 1);
            return;
        }
        notifyItemRangeChanged(startSelectionPosition, endSelectionPosition - startSelectionPosition + 1);
    }

    @NonNull
    protected List<CalendarItem> getCalendarItems() {
        return calendarItems;
    }

    @NonNull
    protected String getMonthsNameByHeaderCalendarItem(@NonNull final CalendarHeaderItem item) {
        return monthsNames != null ? monthsNames[item.getMonth()] : String.valueOf(item.getMonth());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        switch (viewType) {
            case HEADER_ITEM_TYPE:
                return createHeaderViewHolder(parent);
            case EMPTY_ITEM_TYPE:
                return createEmptyViewHolder(parent);
            case DAY_ITEM_TYPE:
                return createDayViewHolder(parent);
            default:
                throw new IllegalArgumentException("Unexpected viewType " + viewType);
        }
    }

    /**
     * Method that creates Header ViewHolder with type of THeaderViewHolder.
     *
     * @param parent {@link ViewGroup} for inflating ViewHolder;
     * @return New THeaderViewHolder;
     */
    @NonNull
    protected abstract THeaderViewHolder createHeaderViewHolder(@NonNull final ViewGroup parent);

    /**
     * Method that creates Empty ViewHolder with type of TEmptyViewHolder.
     *
     * @param parent {@link ViewGroup} for inflating ViewHolder;
     * @return New TEmptyViewHolder;
     */
    @NonNull
    protected abstract TEmptyViewHolder createEmptyViewHolder(@NonNull final ViewGroup parent);

    /**
     * Method that creates Day ViewHolder with type of TDayViewHolder.
     *
     * @param parent {@link ViewGroup} for inflating ViewHolder;
     * @return New TDayViewHolder;
     */
    @NonNull
    protected abstract TDayViewHolder createDayViewHolder(@NonNull final ViewGroup parent);

    /**
     * Bind data to a Header ViewHolder.
     *
     * @param viewHolder ViewHolder for binding;
     * @param year       year;
     * @param monthName  Name of month;
     * @param firstMonth True if bind called for the first month in calendar.
     */
    protected abstract void bindHeaderItem(@NonNull final THeaderViewHolder viewHolder,
                                           final int year,
                                           @NonNull final String monthName,
                                           final boolean firstMonth);

    /**
     * Bind data to an Empty ViewHolder.
     *
     * @param viewHolder    ViewHolder for binding;
     * @param selectionMode Either {@link SelectionMode#SELECTED_MIDDLE} or {@link SelectionMode#NOT_SELECTED} can be here.
     */
    protected abstract void bindEmptyItem(@NonNull final TEmptyViewHolder viewHolder, @NonNull final SelectionMode selectionMode);

    /**
     * Bind data to a Day ViewHolder.
     *
     * @param viewHolder    ViewHolder for binding;
     * @param day           Text with number of a day. Eg "1" or "29";
     * @param date          Date of current day;
     * @param selectionMode Selection mode for this item;
     * @param dateState     Shows calendar date state for this item.
     */
    protected abstract void bindDayItem(@NonNull final TDayViewHolder viewHolder,
                                        @NonNull final String day,
                                        @NonNull final DateTime date,
                                        @NonNull final SelectionMode selectionMode,
                                        @NonNull final ComparingToToday dateState);

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final CalendarItem calendarItem = CalendarUtils.findItemByPosition(calendarItems, position);

        if (calendarItem instanceof CalendarHeaderItem) {
            final StaggeredGridLayoutManager.LayoutParams layoutParams =
                    new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setFullSpan(true);
            holder.itemView.setLayoutParams(layoutParams);
            final CalendarHeaderItem calendarHeaderItem = (CalendarHeaderItem) calendarItem;
            final String monthName = monthsNames != null ? monthsNames[calendarHeaderItem.getMonth()]
                    : String.valueOf(calendarHeaderItem.getMonth());
            bindHeaderItem((THeaderViewHolder) holder, calendarHeaderItem.getYear(), monthName, position == 0);
        } else if (calendarItem instanceof CalendarEmptyItem) {
            if (startSelectionPosition != null && endSelectionPosition != null
                    && position >= startSelectionPosition && position <= endSelectionPosition) {
                bindEmptyItem((TEmptyViewHolder) holder, SelectionMode.SELECTED_MIDDLE);
            } else {
                bindEmptyItem((TEmptyViewHolder) holder, SelectionMode.NOT_SELECTED);
            }
        } else if (calendarItem instanceof CalendarDayItem) {
            bindDay((TDayViewHolder) holder, position, calendarItem);
        }
    }

    //TODO fix suppress
    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    private void bindDay(@NonNull final TDayViewHolder holder, final int position, @NonNull final CalendarItem calendarItem) {
        final String currentDay = String.valueOf(((CalendarDayItem) calendarItem).getPositionOfFirstDay()
                + position - calendarItem.getStartRange());
        final DateTime currentDate = new DateTime(((CalendarDayItem) calendarItem).getDateOfFirstDay()
                + (position - calendarItem.getStartRange()) * ONE_DAY_LENGTH);
        final ComparingToToday dateState = ((CalendarDayItem) calendarItem).getComparingToToday();
        if (startSelectionPosition != null && position == startSelectionPosition) {
            if (endSelectionPosition == null || endSelectionPosition.equals(startSelectionPosition)
                    || startSelectionPosition > endSelectionPosition) {
                bindDayItem(holder, currentDay, currentDate, SelectionMode.SELECTED_ONE_ONLY, dateState);
                return;
            }
            bindDayItem(holder, currentDay, currentDate, SelectionMode.SELECTED_FIRST, dateState);
            return;
        }
        if (startSelectionPosition != null && endSelectionPosition != null && startSelectionPosition > endSelectionPosition) {
            bindDayItem(holder, currentDay, currentDate, SelectionMode.NOT_SELECTED, dateState);
            return;
        }
        if (endSelectionPosition != null && position == endSelectionPosition) {
            bindDayItem(holder, currentDay, currentDate, SelectionMode.SELECTED_LAST, dateState);
            return;
        }
        if (startSelectionPosition != null && endSelectionPosition != null
                && position >= startSelectionPosition && position <= endSelectionPosition) {
            bindDayItem(holder, currentDay, currentDate, SelectionMode.SELECTED_MIDDLE, dateState);
            return;
        }

        bindDayItem(holder, currentDay, currentDate, SelectionMode.NOT_SELECTED, dateState);
    }

    @Override
    public int getItemViewType(final int position) {
        final CalendarItem calendarItem = CalendarUtils.findItemByPosition(calendarItems, position);

        if (calendarItem instanceof CalendarHeaderItem) {
            return HEADER_ITEM_TYPE;
        } else if (calendarItem instanceof CalendarEmptyItem) {
            return EMPTY_ITEM_TYPE;
        } else if (calendarItem instanceof CalendarDayItem) {
            return DAY_ITEM_TYPE;
        }

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return calendarItems.isEmpty() ? 0 : calendarItems.get(calendarItems.size() - 1).getEndRange() + 1;
    }

    protected boolean isEndPositionExist() {
        return endSelectionPosition != null;
    }

    /**
     * Selection mode that shows the type of selection of a calendar cell.
     */
    public enum SelectionMode {

        /**
         * Selection mode for the case when first date in the calendar range selected
         * (not first and last simultaneously; for this purpose see {@link #SELECTED_ONE_ONLY}).
         */
        SELECTED_FIRST,
        /**
         * Selection mode for the case when date in a middle of the calendar range selected.
         */
        SELECTED_MIDDLE,
        /**
         * Selection mode for the case when last date in the calendar range selected
         * (not last and first simultaneously; for this purpose see {@link #SELECTED_ONE_ONLY}).
         */
        SELECTED_LAST,
        /**
         * Selection mode for the case when only one date selected.
         */
        SELECTED_ONE_ONLY,
        /**
         * Selection mode for the case when nothing selected.
         */
        NOT_SELECTED

    }

}
