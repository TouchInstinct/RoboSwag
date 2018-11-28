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

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.touchin.roboswag.core.log.Lc;

/**
 * Created by Ilia Kurtov on 17/03/2016.
 * Utility class to simplify working with {@link CalendarAdapter}.
 */
public final class CalendarUtils {

    private static final int DAYS_IN_WEEK = 7;
    private static final long ONE_DAY = TimeUnit.DAYS.toMillis(1);

    /**
     * Method finds CalendarItem for specified position. Find process is optimized and use binary search algorithm.
     *
     * @param calendarItems List of {@link CalendarItem} where need to find specific element;
     * @param position      Position of adapter;
     * @return CalendarItem for specified position.
     */
    @Nullable
    public static CalendarItem findItemByPosition(@NonNull final List<CalendarItem> calendarItems, final long position) {
        return find(calendarItems, position, false);
    }

    /**
     * Method finds position of Header that respond to requested position.
     *
     * @param calendarItems List of {@link CalendarItem} where need to find specific element;
     * @param position      Position of adapter;
     * @return Position of Header that respond to requested position.
     * Returns null if Header or related CalendarItem was not found for specified position.
     */
    @Nullable
    public static Integer findPositionOfSelectedMonth(@NonNull final List<CalendarItem> calendarItems, final long position) {
        final CalendarItem calendarItem = find(calendarItems, position, true);
        if (calendarItem != null) {
            return calendarItem.getStartRange();
        }
        return null;
    }

    /**
     * Method finds position of calendar cell that respond to specified date.
     *
     * @param calendarItems List of {@link CalendarItem} where need to find specific element;
     * @param date          Requested date in milliseconds.
     * @return Position of Calendar cell that that has specific date.
     * Returns null if CalendarItem was not found for specified position.
     */
    @Nullable
    public static Integer findPositionByDate(@NonNull final List<CalendarItem> calendarItems, final long date) {
        int low = 0;
        int high = calendarItems.size() - 1;
        int addition = 0;
        float count = 0;
        while (true) {
            final int mid = (low + high) / 2 + addition;
            if (calendarItems.get(mid) instanceof CalendarDayItem) {
                if (date < ((CalendarDayItem) calendarItems.get(mid)).getDateOfFirstDay()) {
                    if (mid == 0) {
                        Lc.assertion("Selected date smaller then min date in calendar");
                        return null;
                    }
                    high = mid - 1;
                } else {
                    final long endDate = ((CalendarDayItem) calendarItems.get(mid)).getDateOfFirstDay()
                            + (calendarItems.get(mid).getEndRange() - calendarItems.get(mid).getStartRange()) * ONE_DAY;
                    if (date > endDate) {
                        if (mid == calendarItems.size()) {
                            Lc.assertion("Selected date bigger then max date in calendar");
                            return null;
                        }
                        low = mid + 1;
                    } else {
                        return (int) (calendarItems.get(mid).getStartRange()
                                + (date - (((CalendarDayItem) calendarItems.get(mid)).getDateOfFirstDay())) / ONE_DAY);
                    }
                }
                count = 0;
                addition = 0;
            } else {
                count++;
                addition = ((int) Math.ceil(count / 2)) * ((int) StrictMath.pow(-1, count - 1));
            }
        }
    }

    /**
     * Create list of {@link CalendarItem} according to start and end Dates.
     *
     * @param startDate Start date of the range;
     * @param endDate   End date of the range;
     * @return List of CalendarItems that could be one of these: {@link CalendarHeaderItem}, {@link CalendarDayItem} or {@link CalendarEmptyItem}.
     */
    @NonNull
    public static List<CalendarItem> fillRanges(@NonNull final DateTime startDate, @NonNull final DateTime endDate) {
        final DateTime cleanStartDate = startDate.withTimeAtStartOfDay();
        final DateTime cleanEndDate = endDate.plusDays(1).withTimeAtStartOfDay();
        final DateTime todayDate = DateTime.now().withTimeAtStartOfDay();

        DateTime tempTime = cleanStartDate;

        final List<CalendarItem> calendarItems = fillCalendarTillCurrentDate(todayDate, tempTime);

        tempTime = todayDate.plusDays(1);

        int shift = calendarItems.get(calendarItems.size() - 1).getEndRange();
        calendarItems.addAll(fillRangesUntilDate(tempTime, cleanEndDate, shift, ComparingToToday.AFTER_TODAY));

        return calendarItems;
    }

    @SuppressWarnings("checkstyle:MethodLength")
    private static List<CalendarItem> fillRangesUntilDate(@NonNull final DateTime startDate, @NonNull final DateTime endDate,
                                                          final int startShift, @NonNull final ComparingToToday comparingToToday) {
        DateTime tempTime = startDate;
        final List<CalendarItem> calendarItems = new ArrayList<>();
        final int totalDaysCount = Days.daysBetween(tempTime, endDate).getDays();
        int shift = startShift;
        int firstDate = tempTime.getDayOfMonth() - 1;
        int daysEnded = 1;

        while (true) {
            final int daysInCurrentMonth = tempTime.dayOfMonth().getMaximumValue();
            final long firstRangeDate = tempTime.getMillis();

            if ((daysEnded + (daysInCurrentMonth - firstDate)) <= totalDaysCount) {
                tempTime = tempTime.plusMonths(1).withDayOfMonth(1);

                calendarItems.add(new CalendarDayItem(firstRangeDate, firstDate + 1, shift + daysEnded,
                        shift + daysEnded + (daysInCurrentMonth - firstDate) - 1, comparingToToday));
                daysEnded += daysInCurrentMonth - firstDate;
                if (daysEnded == totalDaysCount) {
                    break;
                }
                firstDate = 0;

                final int firstDayInWeek = tempTime.getDayOfWeek() - 1;

                if (firstDayInWeek != 0) {
                    calendarItems.add(new CalendarEmptyItem(shift + daysEnded, shift + daysEnded + (DAYS_IN_WEEK - firstDayInWeek - 1)));
                    shift += DAYS_IN_WEEK - firstDayInWeek;
                }

                calendarItems.add(new CalendarHeaderItem(tempTime.getYear(), tempTime.getMonthOfYear() - 1, shift + daysEnded, shift + daysEnded));
                shift += 1;

                if (firstDayInWeek != 0) {
                    calendarItems.add(new CalendarEmptyItem(shift + daysEnded, shift + daysEnded + firstDayInWeek - 1));
                    shift += firstDayInWeek;
                }
            } else {
                calendarItems.add(new CalendarDayItem(firstRangeDate, firstDate + 1, shift + daysEnded, shift + totalDaysCount,
                        comparingToToday));
                break;
            }
        }
        return calendarItems;
    }

    @Nullable
    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity", "PMD.StdCyclomaticComplexity"})
    private static CalendarItem find(@NonNull final List<CalendarItem> calendarItems, final long position, final boolean getHeaderPosition) {
        int low = 0;
        int high = calendarItems.size() - 1;
        while (true) {
            final int mid = (low + high) / 2;
            if (position < calendarItems.get(mid).getStartRange()) {
                if (mid == 0 || position > calendarItems.get(mid - 1).getEndRange()) {
                    Lc.assertion("CalendarAdapter cannot find item with that position");
                    return null;
                }
                high = mid - 1;
            } else if (position > calendarItems.get(mid).getEndRange()) {
                if (mid == calendarItems.size() || position < calendarItems.get(mid + 1).getStartRange()) {
                    Lc.assertion("CalendarAdapter cannot find item with that position");
                    return null;
                }
                low = mid + 1;
            } else {
                if (getHeaderPosition) {
                    int calendarShift = mid;
                    while (true) {
                        calendarShift--;
                        if (calendarShift == -1) {
                            return null;
                        }
                        if (calendarItems.get(calendarShift) instanceof CalendarHeaderItem) {
                            return calendarItems.get(calendarShift);
                        }
                    }
                }
                return calendarItems.get(mid);
            }
        }
    }

    @NonNull
    private static List<CalendarItem> fillCalendarTillCurrentDate(@NonNull final DateTime todayDate, @NonNull final DateTime startDate) {
        DateTime tempDate = startDate;
        final List<CalendarItem> calendarItems = new ArrayList<>();
        int shift = 0;

        // add first month header
        calendarItems.add(new CalendarHeaderItem(tempDate.getYear(),
                tempDate.get(DateTimeFieldType.monthOfYear()) - 1, shift, shift)); // is Month starts from 1 or 0 ?
        tempDate = tempDate.withDayOfMonth(1);
        shift += 1;

        final int dayOfWeek = tempDate.getDayOfWeek() - 1;

        // check if first day is Monday. If not - add empty items. Otherwise do nothing
        if (dayOfWeek != 0) {
            calendarItems.add(new CalendarEmptyItem(shift, shift + dayOfWeek - 1));
        }
        shift += dayOfWeek - 1;

        // add range with days before today
        calendarItems.addAll(fillRangesUntilDate(tempDate, todayDate, shift, ComparingToToday.BEFORE_TODAY));
        shift = calendarItems.get(calendarItems.size() - 1).getEndRange() + 1;

        // add today item
        tempDate = todayDate;
        calendarItems.add(new CalendarDayItem(tempDate.getMillis(), tempDate.getDayOfMonth(), shift, shift, ComparingToToday.TODAY));

        //add empty items and header if current day the last day in the month
        if (tempDate.getDayOfMonth() == tempDate.dayOfMonth().getMaximumValue()) {
            addItemsIfCurrentDayTheLastDayInTheMonth(startDate, calendarItems);
        }

        return calendarItems;
    }

    private static void addItemsIfCurrentDayTheLastDayInTheMonth(@NonNull final DateTime dateTime,
                                                                 @NonNull final List<CalendarItem> calendarItems) {

        int shift = calendarItems.get(calendarItems.size() - 1).getEndRange();
        final DateTime nextMonthFirstDay = dateTime.plusDays(1);
        final int firstDayInNextMonth = nextMonthFirstDay.getDayOfWeek() - 1;
        calendarItems.add(new CalendarEmptyItem(shift + 1, shift + (7 - firstDayInNextMonth)));
        shift += 7 - firstDayInNextMonth + 1;
        calendarItems.add(new CalendarHeaderItem(nextMonthFirstDay.getYear(), nextMonthFirstDay.getMonthOfYear() - 1, shift, shift));
        shift += 1;
        calendarItems.add(new CalendarEmptyItem(shift, shift + firstDayInNextMonth - 1));
    }

    private CalendarUtils() {
    }

}
