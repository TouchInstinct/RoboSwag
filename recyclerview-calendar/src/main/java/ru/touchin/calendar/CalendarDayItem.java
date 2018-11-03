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


import androidx.annotation.NonNull;

/**
 * Created by Ilia Kurtov on 17/03/2016.
 * Calendar header item for showing headers for months in calendar.
 */
public class CalendarDayItem implements CalendarItem {

    private final long dateOfFirstDay;
    private final int positionOfFirstDate;
    private final int startRange;
    private final int endRange;
    @NonNull
    private final ComparingToToday comparingToToday;

    public CalendarDayItem(final long dateOfFirstDay,
                           final int positionOfFirstDate,
                           final int startRange,
                           final int endRange,
                           @NonNull final ComparingToToday comparingToToday) {
        this.dateOfFirstDay = dateOfFirstDay;
        this.positionOfFirstDate = positionOfFirstDate;
        this.startRange = startRange;
        this.endRange = endRange;
        this.comparingToToday = comparingToToday;
    }

    /**
     * Returns date of the first date in millis in this calendar range.
     *
     * @return Date of first date in this item in millis.
     */
    public long getDateOfFirstDay() {
        return dateOfFirstDay;
    }

    /**
     * Returns position of calendar cell for the first date.
     *
     * @return Position of calendar cell for the first date.
     */
    public int getPositionOfFirstDay() {
        return positionOfFirstDate;
    }

    @Override
    public int getStartRange() {
        return startRange;
    }

    @Override
    public int getEndRange() {
        return endRange;
    }

    /**
     * Returns comparison of current item to today.
     *
     * @return comparison of current item to today.
     */
    @NonNull
    public ComparingToToday getComparingToToday() {
        return comparingToToday;
    }

}
