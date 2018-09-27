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

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Process;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

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
    @NonNull
    public static NetworkType getNetworkType(@NonNull final Context context) {
        if (context.checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, Process.myPid(), Process.myUid())
                != PackageManager.PERMISSION_GRANTED) {
            return NetworkType.UNKNOWN;
        }
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return NetworkType.UNKNOWN;
        }
        @SuppressLint("MissingPermission") final NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return NetworkType.NONE;
        }
        switch (info.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                return NetworkType.WI_FI;
            case ConnectivityManager.TYPE_MOBILE:
                return getMobileNetworkType(info);
            default:
                return NetworkType.UNKNOWN;
        }
    }

    /**
     * Detects if some network connected.
     *
     * @param context Application context
     * @return true if network connected, false otherwise.
     */
    public static boolean isNetworkConnected(@NonNull final Context context) {
        return getNetworkType(context) != NetworkType.NONE;
    }

    @NonNull
    private static NetworkType getMobileNetworkType(@NonNull final NetworkInfo info) {
        switch (info.getSubtype()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NetworkType.MOBILE_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NetworkType.MOBILE_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
            case 19: // NETWORK_TYPE_LTE_CA is hide
                return NetworkType.MOBILE_LTE;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
            default:
                return NetworkType.UNKNOWN;
        }
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
