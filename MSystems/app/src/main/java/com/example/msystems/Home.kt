package com.example.msystems

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.provider.Settings.Global.getString
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class Home(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    var paint: Paint
    var rect: Rect
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.parseColor("#768CC0"))
        paint.color = Color.parseColor("#70F6DF")
        rect[spToPx(0f, this), spToPx(100f, this), spToPx(470f, this)] =
            spToPx(200f, this)
        canvas.drawRect(rect, paint)
        paint.color = Color.parseColor("#1B8CD6")
        rect[spToPx(120f, this), spToPx(50f, this), spToPx(300f, this)] =
            spToPx(150f, this)
        canvas.drawRect(rect, paint)
        paint.color = Color.parseColor("#0F64A7")
        rect[spToPx(265f, this), spToPx(10f, this), spToPx(285f, this)] =
            spToPx(50f, this)
        canvas.drawRect(rect, paint)
        paint.color = Color.parseColor("#FF018786")
        val path = Path()
        path.moveTo(spToPx(90f, this).toFloat(), spToPx(50f, this).toFloat())
        path.lineTo(spToPx(210f, this).toFloat(), spToPx(25f, this).toFloat())
        path.lineTo(spToPx(330f, this).toFloat(), spToPx(50f, this).toFloat())
        path.lineTo(spToPx(90f, this).toFloat(), spToPx(50f, this).toFloat())
        path.close()
        canvas.drawPath(path, paint)
    }

    companion object {
        fun spToPx(sp: Float, context: Home): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                sp,
                context.resources.displayMetrics
            ).toInt()
        }
    }

    init {
        paint = Paint()
        rect = Rect()
    }
}