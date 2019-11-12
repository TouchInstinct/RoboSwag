package ru.touchin.roboswag.components.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import ru.touchin.roboswag.components.utils.UiUtils
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.tan

/**
 * View to show to user loading process with gradient changing animation.
 * Default gradient angle is 20 degrees and default animation duration is 4 seconds.
 *
 * See SkeletonView Attributes:
 * @attr R.styleable#SkeletonView_skeletonGradientAngle
 * @attr R.styleable#SkeletonView_skeletonAnimationDuration
 * @attr R.styleable#SkeletonView_skeletonShape
 * @attr R.styleable#SkeletonView_skeletonBaseColor
 * @attr R.styleable#SkeletonView_skeletonFirstGradientColor
 * @attr R.styleable#SkeletonView_skeletonSecondGradientColor
 * @attr R.styleable#SkeletonView_skeletonCornerRadius
 * @attr R.styleable#SkeletonView_skeletonCircleStrokeWidth
 */
class SkeletonView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_CORNER_RADIUS_IN_DP = 2f
        private const val DEFAULT_CIRCLE_STROKE_WIDTH = 0f
        private const val DEFAULT_ANGLE = 20f
        private const val DEFAULT_ANIMATION_DURATION_IN_SEC = 4
    }

    private val displayMetrics = context.resources.displayMetrics

    private val valueAnimator: ValueAnimator

    private val paint = Paint()
    private val shaderMatrix = Matrix()
    private val horizontalBounds = RectF()

    private var baseColor = ContextCompat.getColor(context, R.color.skeleton_base)
    private var firstGradientColor = ContextCompat.getColor(context, R.color.skeleton_base)
    private var secondGradientColor = Color.WHITE

    private var cornerRadius = UiUtils.OfMetrics.dpToPixels(context, DEFAULT_CORNER_RADIUS_IN_DP)
    private var circleStrokeWidth = DEFAULT_CIRCLE_STROKE_WIDTH
    private var angle = 0F
    private var animationDuration = 0L

    private var shape: Shape = Shape.RECTANGLE

    private var delta = 0f

    init {
        context.withStyledAttributes(attrs, R.styleable.SkeletonView, defStyleAttr, 0) {
            shape = Shape.values()[getInt(R.styleable.SkeletonView_skeletonShape, Shape.RECTANGLE.ordinal)]
            baseColor = getColor(R.styleable.SkeletonView_skeletonBaseColor, baseColor)
            cornerRadius = getDimension(R.styleable.SkeletonView_skeletonCornerRadius, DEFAULT_CORNER_RADIUS_IN_DP)
            circleStrokeWidth = getDimension(R.styleable.SkeletonView_skeletonCircleStrokeWidth, DEFAULT_CIRCLE_STROKE_WIDTH)
            firstGradientColor = getColor(R.styleable.SkeletonView_skeletonFirstGradientColor, firstGradientColor)
            secondGradientColor = getColor(R.styleable.SkeletonView_skeletonSecondGradientColor, secondGradientColor)
            angle = getFloat(R.styleable.SkeletonView_skeletonGradientAngle, DEFAULT_ANGLE)
            animationDuration = TimeUnit.SECONDS.toMillis(
                    getInt(R.styleable.SkeletonView_skeletonAnimationDurationInSec, DEFAULT_ANIMATION_DURATION_IN_SEC).toLong()
            )
        }
        valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = animationDuration
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
        }
        if (shape == Shape.CIRCLE && circleStrokeWidth > 0) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = circleStrokeWidth
        }
        updateShader()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)

        horizontalBounds.set(0f, 0f, width.toFloat(), height.toFloat())
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (oldw != w && oldh != h) {
            val positionOnScreen = IntArray(2)
            getLocationOnScreen(positionOnScreen)

            delta = positionOnScreen[1] / tan(Math.toRadians(90.toDouble() - angle)).toFloat() + positionOnScreen[0]

            updateValueAnimator(true)
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        if (visibility == GONE || visibility == INVISIBLE) {
            updateValueAnimator(false)
        } else if (delta != 0f) {
            updateValueAnimator(true)
        }
    }

    override fun onDraw(canvas: Canvas) {
        val animatedValue = valueAnimator.animatedFraction
        val translateWidth = displayMetrics.widthPixels.toFloat()
        val dx = offset(-translateWidth * 3, translateWidth, animatedValue)

        shaderMatrix.reset()
        shaderMatrix.setRotate(angle, translateWidth / 2f, translateWidth / 2f)
        shaderMatrix.postTranslate(dx, 0f)

        paint.shader.setLocalMatrix(shaderMatrix)

        when (shape) {
            Shape.CIRCLE -> {
                val halfWidth = width / 2f
                val halfHeight = height / 2f
                val radius = min(halfWidth, halfHeight) - paint.strokeWidth / 2
                canvas.drawCircle(halfWidth, halfHeight, radius, paint)
            }
            Shape.RECTANGLE -> {
                canvas.drawRoundRect(horizontalBounds, cornerRadius, cornerRadius, paint)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        valueAnimator.cancel()
        valueAnimator.removeAllUpdateListeners()
    }

    private fun offset(start: Float, end: Float, percent: Float): Float = start + (end - start) * percent - delta

    private fun updateShader() {
        val width = displayMetrics.widthPixels.toFloat() * 3f
        paint.shader = LinearGradient(
                0f, 0f, width, 0f,
                intArrayOf(baseColor, secondGradientColor, baseColor, baseColor, firstGradientColor, baseColor),
                floatArrayOf(0.36f, 0.41f, 0.46f, 0.9f, 0.95f, 1f),
                Shader.TileMode.CLAMP
        )
    }

    private fun updateValueAnimator(start: Boolean) {
        valueAnimator.cancel()
        valueAnimator.removeAllUpdateListeners()

        if (start) {
            valueAnimator.addUpdateListener {
                invalidate()
            }
            valueAnimator.start()
        }
    }

    private enum class Shape {
        CIRCLE,
        RECTANGLE
    }

}
