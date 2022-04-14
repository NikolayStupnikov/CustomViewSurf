package ru.nukolay.stupnikov.customviewsurf.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*

class CanvasView(
    context: Context,
    attrs: AttributeSet? = null
): View(context, attrs) {

    private val random: Random = Random()
    private val figureArray = Figure.values()

    private val paint = Paint(ANTI_ALIAS_FLAG)
    private val rect = Rect()
    private val rectF = RectF()

    private val listFigure: MutableList<FigureState> = mutableListOf()

    fun drawFigure(color: Int, x: Float, y: Float) {
        listFigure.add(
            FigureState(
                xCoordinate = x,
                yCoordinate = y,
                color = color,
                size = getDimen(),
                figure = getFigure()
            )
        )
        invalidate()
    }

    fun clearCanvas() {
        listFigure.clear()
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (listFigure.isEmpty()) return

        for (state in listFigure) {
            paint.color = state.color
            when (state.figure) {
                Figure.CIRCLE -> canvas.drawCircle(
                    state.xCoordinate,
                    state.yCoordinate,
                    (state.size / 2).toFloat(),
                    paint
                )
                Figure.SQUARE -> {
                    updateRect(state.xCoordinate, state.yCoordinate, state.size)
                    canvas.drawRect(rect, paint)
                }
                Figure.SQUARE_WITH_ROUNDED_CORNERS -> {
                    updateRectF(state.xCoordinate, state.yCoordinate, state.size)
                    canvas.drawRoundRect(rectF, 30f, 30f, paint)
                }
            }
        }
    }

    private fun updateRect(xCoordinate: Float, yCoordinate: Float, size: Int) {
        rect.left = (xCoordinate - (size / 2)).toInt()
        rect.top = (yCoordinate + (size / 2)).toInt()
        rect.right = (xCoordinate + (size / 2)).toInt()
        rect.bottom = (yCoordinate - (size / 2)).toInt()
    }

    private fun updateRectF(xCoordinate: Float, yCoordinate: Float, size: Int) {
        rectF.left = xCoordinate - (size / 2)
        rectF.top = yCoordinate + (size / 2)
        rectF.right = xCoordinate + (size / 2)
        rectF.bottom = yCoordinate - (size / 2)
    }

    private fun getDimen(): Int {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                (random.nextInt(30) + 20).toFloat(),
                context.resources.displayMetrics
        ).toInt()
    }

    private enum class Figure {
        CIRCLE, SQUARE, SQUARE_WITH_ROUNDED_CORNERS
    }

    private fun getFigure(): Figure {
        return figureArray[random.nextInt(figureArray.size)]
    }

    private data class FigureState(
        val xCoordinate: Float,
        val yCoordinate: Float,
        val color: Int,
        val size: Int,
        val figure: Figure
    )
}