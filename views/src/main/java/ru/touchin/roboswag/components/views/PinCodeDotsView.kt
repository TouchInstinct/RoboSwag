package ru.touchin.roboswag.components.views

import android.content.Context
import android.graphics.PorterDuff.Mode.SRC_IN
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.touchin.runtime.core_ui.R
import ru.touchin.roboswag.components.utils.UiUtils
import ru.touchin.roboswag.components.utils.px

class PinCodeDotsView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {

        const val RETURN_TO_REGULAR_FROM_ERROR_DELAY = 500L
        const val DEFAULT_DOT_SIZE = 12f

    }

    init {
        UiUtils.inflateAndAdd(R.layout.view_pin_dots, this)
    }

    private val errorAnimation = AnimationUtils.loadAnimation(context, R.anim.shaking_animation)

    private val pinViews = arrayOf<ImageView>(
            findViewById(R.id.dot_1),
            findViewById(R.id.dot_2),
            findViewById(R.id.dot_3),
            findViewById(R.id.dot_4)
    )

    var onErrorAnimationFinished: (() -> Unit)? = null

    init {
        errorAnimation.setAnimationListener(object : SimpleAnimationListener() {
            override fun onAnimationEnd(animation: Animation?) {
                postDelayed({
                    setState(PinDotsViewState.REGULAR)
                    onErrorAnimationFinished?.invoke()
                }, RETURN_TO_REGULAR_FROM_ERROR_DELAY)
            }
        })
        context.withStyledAttributes(attrs, R.styleable.PinCodeDotsView, defStyleAttr, 0) {
            val dotSize = getDimension(R.styleable.PinCodeDotsView_dotSize, DEFAULT_DOT_SIZE).toInt()
            pinViews.forEach { dot ->
                dot.layoutParams = dot.layoutParams.apply {
                    width = dotSize
                    height = dotSize
                }
            }
        }
    }

    fun setState(state: PinDotsViewState) {
        val color = ContextCompat.getColor(
                context,
                when (state) {
                    PinDotsViewState.ERROR -> {
                        startAnimation(errorAnimation)
                        R.color.codeError
                    }
                    PinDotsViewState.SUCCESS -> R.color.colorAccent
                    PinDotsViewState.REGULAR -> R.color.colorPrimary
                }
        )
        pinViews.forEach { it.setColorFilter(color, SRC_IN) }
    }

    fun fillDots(fillDotsCount: Int) {
        setState(PinDotsViewState.REGULAR)
        pinViews.take(fillDotsCount).forEach {
            it.setColorFilter(
                    ContextCompat.getColor(context, android.R.color.white),
                    SRC_IN
            )
        }
    }

}

enum class PinDotsViewState {
    ERROR,
    SUCCESS,
    REGULAR
}