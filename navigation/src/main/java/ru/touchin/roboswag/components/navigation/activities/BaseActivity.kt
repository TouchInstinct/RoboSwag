/*
 *  Copyright (c) 2015 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
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

package ru.touchin.roboswag.components.navigation.activities

import android.content.Intent
import android.os.Bundle
import androidx.collection.ArraySet
import androidx.appcompat.app.AppCompatActivity

import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.core.log.LcGroup

/**
 * Created by Gavriil Sitnikov on 08/03/2016.
 * Base activity to use in components repository.
 */
abstract class BaseActivity : AppCompatActivity() {

    private val onBackPressedListeners = ArraySet<OnBackPressedListener>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LcGroup.UI_LIFECYCLE.i("${Lc.getCodePoint(this)} requestCode: $requestCode; resultCode: $resultCode")
    }

    override fun onStart() {
        super.onStart()
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
    }

    override fun onResume() {
        super.onResume()
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
    }

    override fun onPause() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
        super.onPause()
    }

    override fun onSaveInstanceState(stateToSave: Bundle) {
        super.onSaveInstanceState(stateToSave)
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
    }

    override fun onLowMemory() {
        super.onLowMemory()
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
    }

    override fun onStop() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
        super.onStop()
    }

    override fun onDestroy() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun addOnBackPressedListener(onBackPressedListener: OnBackPressedListener) {
        onBackPressedListeners.add(onBackPressedListener)
    }

    fun removeOnBackPressedListener(onBackPressedListener: OnBackPressedListener) {
        onBackPressedListeners.remove(onBackPressedListener)
    }

    fun removeAllOnBackPressedListeners() {
        onBackPressedListeners.clear()
    }

    override fun onBackPressed() {
        onBackPressedListeners.forEach { onBackPressedListener ->
            if (onBackPressedListener.onBackPressed()) {
                return
            }
        }
        super.onBackPressed()
    }

}
