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

package ru.touchin.roboswag.core.utils;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Gavriil Sitnikov on 29/08/2016.
 * Utility class to providing some string-related helper methods.
 */
public final class StringUtils {

    /**
     * Returns MD5 of string.
     *
     * @param string String to get MD5 from;
     * @return MD5 of string.
     */
    @NonNull
    public static String md5(@NonNull final String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(string.getBytes("UTF-8"));
        final byte[] messageDigestArray = digest.digest();

        final StringBuilder hexString = new StringBuilder();
        for (final byte messageDigest : messageDigestArray) {
            final String hex = Integer.toHexString(0xFF & messageDigest);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private StringUtils() {
    }

}
