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
package ru.touchin.templates

import android.content.Context
import android.net.NetworkInfo
import android.telephony.TelephonyManager

/**
 * Utility class that is providing common methods related to android device.
 */
object DeviceUtils {
    /**
     * Detects active network type.
     *
     * @param context Application context
     * @return Active network type [NetworkType]
     */
    @Deprecated(
            "Use extension instead",
            ReplaceWith("context.getNetworkType()")
    )
    fun getNetworkType(context: Context) = context.getNetworkType()

    /**
     * Detects if some network connected.
     *
     * @param context Application context
     * @return true if network connected, false otherwise.
     */
    @Deprecated(
            "Use extension instead",
            ReplaceWith("context.isNetworkConnected()")
    )
    fun isNetworkConnected(context: Context) = context.isNetworkConnected()


    fun getMobileNetworkType(info: NetworkInfo): NetworkType =
            when (info.subtype) {
                TelephonyManager.NETWORK_TYPE_GPRS,
                TelephonyManager.NETWORK_TYPE_EDGE,
                TelephonyManager.NETWORK_TYPE_CDMA,
                TelephonyManager.NETWORK_TYPE_1xRTT,
                TelephonyManager.NETWORK_TYPE_IDEN -> NetworkType.MOBILE_2G
                TelephonyManager.NETWORK_TYPE_UMTS,
                TelephonyManager.NETWORK_TYPE_EVDO_0,
                TelephonyManager.NETWORK_TYPE_EVDO_A,
                TelephonyManager.NETWORK_TYPE_HSDPA,
                TelephonyManager.NETWORK_TYPE_HSUPA,
                TelephonyManager.NETWORK_TYPE_HSPA,
                TelephonyManager.NETWORK_TYPE_EVDO_B,
                TelephonyManager.NETWORK_TYPE_EHRPD,
                TelephonyManager.NETWORK_TYPE_HSPAP -> NetworkType.MOBILE_3G
                TelephonyManager.NETWORK_TYPE_LTE,
                19 -> NetworkType.MOBILE_LTE
                TelephonyManager.NETWORK_TYPE_UNKNOWN -> NetworkType.UNKNOWN
                else -> NetworkType.UNKNOWN
            }

    /**
     * Available network types.
     */
    enum class NetworkType(val networkName: String) {
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

        /**
         * @return Network type readable name.
         */

    }
}
