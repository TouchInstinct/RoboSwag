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

/**
 * Created by Ilia Kurtov on 17/03/2016.
 * Calendar header item for showing headers for months in calendar.
 */
public class CalendarHeaderItem implements CalendarItem {

    private final int year;
    private final int month;
    private final int startRange;
    private final int endRange;

    public CalendarHeaderItem(final int year, final int month, final int startRange, final int endRange) {
        this.year = year;
        this.month = month;
        this.startRange = startRange;
        this.endRange = endRange;
    }

    /**
     * Returns year.
     *
     * @return year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns number of month (where 0 is January and 11 is December).
     *
     * @return Number of month (where 0 is January and 11 is December).
     */
    public int getMonth() {
        return month;
    }

    @Override
    public int getStartRange() {
        return startRange;
    }

    @Override
    public int getEndRange() {
        return endRange;
    }

}
