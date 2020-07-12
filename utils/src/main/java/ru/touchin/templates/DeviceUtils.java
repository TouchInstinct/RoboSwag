/*
 *  Copyright (c) 2016 Touch Instinct
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

package ru.touchin.templates;

import android.content.Context;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

/**
 * Utility class that is providing common methods related to android device.
 */
public final class DeviceUtils {

    /**
     * Detects active network type.
     *
     * @param context Application context
     * @return Active network type {@link NetworkType}
     */
    @Deprecated
    @NonNull
    public static NetworkType getNetworkType(@NonNull final Context context) {
        return DeviceUtilsExtensionsKt.getNetworkType(context);
    }

    /**
     * Detects if some network connected.
     *
     * @param context Application context
     * @return true if network connected, false otherwise.
     */
    @Deprecated
    public static boolean isNetworkConnected(@NonNull final Context context) {
        return DeviceUtilsExtensionsKt.isNetworkConnected(context);
    }

    @Deprecated
    @NonNull
    static NetworkType getMobileNetworkType(@NonNull final NetworkInfo info) {
        return DeviceUtilsExtensionsKt.getMobileNetworkType(info);
    }

    private DeviceUtils() {
    }

    /**
     * Available network types.
     */
    public enum NetworkType {
        /**
         * Mobile 2G network.
         */
        MOBILE_2G("2g"),
        /**
         * Mobile 3G network.
         */
        MOBILE_3G("3g"),
        /**
         * Mobile LTE network.
         */
        MOBILE_LTE("lte"),
        /**
         * Wi-Fi network.
         */
        WI_FI("Wi-Fi"),
        /**
         * Unknown network type.
         */
        UNKNOWN("unknown"),
        /**
         * No network.
         */
        NONE("none");

        @NonNull
        private final String name;

        NetworkType(@NonNull final String name) {
            this.name = name;
        }

        /**
         * @return Network type readable name.
         */
        @NonNull
        public String getName() {
            return name;
        }

    }

}
