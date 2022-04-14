package ru.nukolay.stupnikov.customviewsurf.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import ru.nukolay.stupnikov.customviewsurf.R
import java.util.*

@SuppressLint("CustomViewStyleable")
class CustomView(
        context: Context,
        attrs: AttributeSet? = null
): FrameLayout(context, attrs) {

    private lateinit var viewCanvas: CanvasView
    private lateinit var countTv: TextView

    private val MAX_COUNT = 10
    private var currentCount = 0

    private var defaultColor: Int = 0

    private val random: Random = Random()

    @ColorRes
    var colorResArray: Array<Int> = arrayOf()

    var colorHexArray: Array<String> = arrayOf()

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.view_custom, this, true)
        initAttrs(attrs)
        initViews(root)
    }

    private fun initAttrs(attributeSet: AttributeSet?) {
        val typedArray = context
                .obtainStyledAttributes(attributeSet, R.styleable.CustomViewSurf, 0, 0)

        defaultColor = typedArray.getColor(R.styleable.CustomViewSurf_defaultColor,
                ContextCompat.getColor(context, R.color.green))

        typedArray.recycle()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews(root: View) {
        viewCanvas = root.findViewById(R.id.view_canvas)
        countTv = root.findViewById(R.id.counter_tv)

        viewCanvas.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == ACTION_DOWN) {
                viewCanvas.drawFigure(
                        color = getColor(),
                        x = motionEvent.x,
                        y = motionEvent.y
                )
                currentCount++
                if (currentCount == MAX_COUNT) {
                    currentCount = 0
                    viewCanvas.clearCanvas()
                    Toast.makeText(context, "Game over", Toast.LENGTH_SHORT).show()
                }
                countTv.text = currentCount.toString()
            }
            return@setOnTouchListener true
        }
    }

    private fun getColor(): Int {
        if (colorResArray.isEmpty() && colorHexArray.isEmpty()) return defaultColor
        if (colorResArray.isNotEmpty()) {
            return ContextCompat.getColor(context, getRandom(colorResArray))
        }
        return Color.parseColor(getRandom(colorHexArray))
    }

    private fun <T> getRandom(array: Array<T>): T {
        return array[random.nextInt(array.size)]
    }
}