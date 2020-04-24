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

package ru.touchin.roboswag.components.navigation_base.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ru.touchin.roboswag.components.navigation_viewcontroller.keyboard_resizeable.KeyboardBehaviorDetector
import ru.touchin.roboswag.components.navigation_base.fragments.LifecycleLoggingObserver
import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.core.log.LcGroup

/**
 * Created by Gavriil Sitnikov on 08/03/2016.
 * Base activity to use in components repository.
 */
abstract class BaseActivity : AppCompatActivity() {

    private val onBackPressedListeners = ArrayList<OnBackPressedListener>()

    var keyboardBehaviorDetector: KeyboardBehaviorDetector? = null

    open val freezeFontScaleFactor: Boolean = true

    init {
        lifecycle.addObserver(LifecycleLoggingObserver(this))
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        // Possible work around for market launches. See http://code.google.com/p/android/issues/detail?id=2373
        // for more details. Essentially, the market launches the main activity on top of other activities.
        // we never want this to happen. Instead, we check if we are the root and if not, we finish.
        if (!isTaskRoot && intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == intent.action) {
            Lc.e("Finishing activity as it is launcher but not root")
            finish()
        }

        if (freezeFontScaleFactor) {
            adjustFontScale(resources.configuration)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LcGroup.UI_LIFECYCLE.i("${Lc.getCodePoint(this)} requestCode: $requestCode; resultCode: $resultCode")
    }

    override fun onSaveInstanceState(stateToSave: Bundle) {
        super.onSaveInstanceState(stateToSave)
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    open fun addOnBackPressedListener(onBackPressedListener: OnBackPressedListener) {
        onBackPressedListeners.add(onBackPressedListener)
    }

    open fun removeOnBackPressedListener(onBackPressedListener: OnBackPressedListener) {
        onBackPressedListeners.remove(onBackPressedListener)
    }

    open fun removeAllOnBackPressedListeners() {
        onBackPressedListeners.clear()
    }

    override fun onBackPressed() {
        onBackPressedListeners.reversed().forEach { onBackPressedListener ->
            if (onBackPressedListener.onBackPressed()) {
                return
            }
        }
        super.onBackPressed()
    }

    private fun adjustFontScale(configuration: Configuration) {
        configuration.fontScale = 1f
        val metrics = resources.displayMetrics
        (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }

}
