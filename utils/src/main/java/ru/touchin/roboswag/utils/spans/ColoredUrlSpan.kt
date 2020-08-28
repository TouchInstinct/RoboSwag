/*
 *  Copyright (c) 2017 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
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

package ru.touchin.roboswag.components.utils.spans

import android.text.TextPaint
import androidx.annotation.ColorInt

/**
 * Created by Anton Arhipov on 05/07/2017.
 * URLSpan that takes custom color and doesn't have the default underline.
 * Don't forget to use
 * textView.setMovementMethod(LinkMovementMethod.getInstance());
 * and
 * textView.setText(spannableString, TextView.BufferType.SPANNABLE);
 */
class ColoredUrlSpan(@ColorInt private val textColor: Int, url: String) : URLSpanWithoutUnderline(url) {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.color = textColor
    }

}
