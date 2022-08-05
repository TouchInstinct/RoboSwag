package ru.touchin.roboswag.base_filters.range

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Cap
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.slider.RangeSlider
import ru.touchin.roboswag.base_filters.R
import ru.touchin.roboswag.components.utils.getColorSimple

class FilterRangeSlider @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : RangeSlider(context, attrs, defStyleAttr) {

    var points: List<Int>? = null
        set(value) {
            field = value?.sorted()?.filter { it > valueFrom && it < valueTo }
        }

    private val innerThumbRadius: Int = thumbRadius

    private var stepValueMarginTop = 0f
    private var inactiveTickColor: Int = context.getColorSimple(R.color.slider_point_inactive)
    private var activeTickColor: Int = context.getColorSimple(R.color.slider_point_active)
    private var sliderPointSize: Float = 5f
    @StyleRes private var stepTextAppearance: Int = -1
    private var shape: Shape = Shape.CIRCLE

    private var trackCenterY: Float = -1F

    init {
        // Set original thumb radius to zero to draw custom one on top
        thumbRadius = 0

        context.withStyledAttributes(attrs, R.styleable.FilterRangeSlider, defStyleAttr, defStyleRes) {
            stepValueMarginTop = getDimension(R.styleable.FilterRangeSlider_filterRange_stepValueMarginTop, stepValueMarginTop)
            inactiveTickColor = getColor(R.styleable.FilterRangeSlider_filterRange_inactiveTickColor, inactiveTickColor)
            activeTickColor = getColor(R.styleable.FilterRangeSlider_filterRange_activeTickColor, activeTickColor)
            sliderPointSize = getDimension(R.styleable.FilterRangeSlider_filterRange_sliderPointSize, sliderPointSize)
            stepTextAppearance = getResourceId(R.styleable.FilterRangeSlider_filterRange_stepTextAppearance, -1)
            shape = Shape.values()[getInt(R.styleable.FilterRangeSlider_filterRange_pointShape, Shape.CIRCLE.ordinal)]
        }
    }

    private val thumbDrawable = MaterialShapeDrawable().apply {
        shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
        setBounds(0, 0, innerThumbRadius * 2, innerThumbRadius * 2)
        elevation = thumbElevation
        state = drawableState
        fillColor = thumbTintList
        shapeAppearanceModel = ShapeAppearanceModel
                .builder()
                .setAllCorners(shape.value, innerThumbRadius.toFloat())
                .build()
    }

    private val inactiveTicksPaint = getDefaultTickPaint().apply { color = inactiveTickColor }

    private val activeTicksPaint = getDefaultTickPaint().apply { color = activeTickColor }

    private fun getDefaultTickPaint() = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Cap.ROUND
        strokeWidth = sliderPointSize
    }

    // Using TextView as a bridge to get text params
    private val stepValuePaint: Paint = TextView(context)
            .apply { stepTextAppearance.takeIf { it != -1 }?.let { setTextAppearance(it) } }
            .let { textView ->
                Paint().apply {
                    isAntiAlias = true
                    color = textView.currentTextColor
                    textSize = textView.textSize
                    typeface = textView.typeface
                    textAlign = Paint.Align.CENTER
                }
            }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        trackCenterY = measuredHeight / 2F

        val height = trackCenterY + trackHeight / 2F + stepValueMarginTop + stepValuePaint.textSize
        setMeasuredDimension(measuredWidth, height.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawTicks(canvas)
        drawThumb(canvas)
        drawStepValues(canvas)
    }

    private fun drawTicks(canvas: Canvas) {
        if (points.isNullOrEmpty()) return

        val ticksCoordinates = mutableListOf<Float>()
        points?.forEach { point ->
            ticksCoordinates.add(normalizeValue(point.toFloat()) * trackWidth + trackSidePadding)
            ticksCoordinates.add(trackCenterY)
        }

        val leftPointsSize = points?.count { it < values[0] } ?: 0
        val rightPointSize = points?.count { it > values[1] } ?: 0
        val activePointSize = (points?.size ?: 0) - leftPointsSize - rightPointSize

        // Draw inactive ticks to the left of the smallest thumb.
        canvas.drawPoints(ticksCoordinates.toFloatArray(), 0, leftPointsSize * 2, inactiveTicksPaint)

        // Draw active ticks between the thumbs.
        canvas.drawPoints(
                ticksCoordinates.toFloatArray(),
                leftPointsSize * 2,
                activePointSize * 2,
                activeTicksPaint
        )

        // Draw inactive ticks to the right of the largest thumb.
        canvas.drawPoints(
                ticksCoordinates.toFloatArray(),
                leftPointsSize * 2 + activePointSize * 2,
                rightPointSize * 2,
                inactiveTicksPaint
        )
    }

    private fun drawThumb(canvas: Canvas) {
        for (value in values) {
            canvas.save()
            canvas.translate(
                    (trackSidePadding + (normalizeValue(value) * trackWidth).toInt() - innerThumbRadius).toFloat(),
                    trackCenterY - innerThumbRadius
            )
            thumbDrawable.draw(canvas)
            canvas.restore()
        }
    }

    private fun drawStepValues(canvas: Canvas) {
        points?.forEach { point ->
            canvas.drawText(
                    point.toString(),
                    normalizeValue(point.toFloat()) * trackWidth + trackSidePadding,
                    trackCenterY + trackHeight / 2F + stepValueMarginTop + stepValuePaint.textSize - 3F,
                    stepValuePaint
            )
        }
    }

    private fun normalizeValue(value: Float) = (value - valueFrom) / (valueTo - valueFrom)

    private enum class Shape(val value: Int) {
        CIRCLE(CornerFamily.ROUNDED),
        CUT(CornerFamily.CUT)
    }
}
