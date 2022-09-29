package com.example.mobilesystems

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import com.example.mobilesystems.databinding.ActivityButtonsBinding
import com.example.mobilesystems.databinding.ActivitySquaresBinding

class Squares : AppCompatActivity() {
    lateinit var binding : ActivitySquaresBinding
    private var b : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySquaresBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClickLogo(view : View){
        binding.idLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise))
    }

    fun onClickBlack(view : View){
            binding.idBlack.scaleY = Positions.SCALE.toFloat()
        Thread({
            Thread.sleep(4000)
            binding.idBlack.scaleY = Positions.REVERSE.toFloat()
        }).start()


        binding.idBlack.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise))
    }

    fun onClickOk(view : View){
        binding.apply {
            if(!idLeftAdd.text.isNullOrEmpty() && !idRightAdd.text.isNullOrEmpty())
            idResult.text = (idLeftAdd.text.toString().toInt() + idRightAdd.text.toString().toInt()).toString()
        }
    }

    fun onClickCancel(view : View){
        binding.apply {
            idLeftAdd.text.clear()
            idRightAdd.text.clear()
            idResult.text = ""
        }
    }

    fun onClickBlack2(view : View){
        binding.idBlack2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate))
    }

    fun onClickNext2(view: View) {
        startActivity(Intent(this@Squares, Buttons::class.java))
    }
}

/*import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextPaint
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.mobilesystems.databinding.ActivitySquaresBinding

class Squares : AppCompatActivity() {
    lateinit var binding : ActivitySquaresBinding
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySquaresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bitmap: Bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888)
        var canvas: Canvas = Canvas(bitmap)

        var shapeDrawable: ShapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds(Positions.LEFT,
            Positions.TOP,
            Positions.RIGHT,
            Positions.BOTTOM)
        shapeDrawable.getPaint().setColor(Color.RED)
        shapeDrawable.draw(canvas)

        var left = Positions.RIGHT
        var top = (Positions.TOP + Positions.BOTTOM) / 2
        var right = left + Positions.BOUND
        var bottom = top + Positions.BOUND

        // draw oval shape to canvas
        shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds(left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.GREEN)
        shapeDrawable.draw(canvas)

        // rectangle blue positions
        left = Positions.RIGHT + (Positions.RIGHT - Positions.LEFT) / 2
        top = Positions.TOP
        right = left + Positions.BOUND
        bottom = top + Positions.BOUND

        shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds(left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.BLUE)
        shapeDrawable.draw(canvas)

        shapeDrawable = ShapeDrawable(RectShape())
        var p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.textSize = Positions.BOUND.toFloat() / 7
        p.setColor(Color.WHITE)

        var square : String = "Квадрат"
        canvas.translate((left + right).toFloat() / 2, (top + bottom).toFloat() / 2)
        canvas.drawText(square, -2 * p.textSize , p.textSize / 2, p)
        shapeDrawable.draw(canvas)

        // now bitmap holds the updated pixels

        shapeDrawable = ShapeDrawable(RectShape())
        canvas = Canvas(bitmap)
        shapeDrawable.setBounds(Positions.LEFT,
            Positions.TOP + Positions.BOUND*2,
            Positions.LEFT + Positions.BOUND*2,
            Positions.TOP + Positions.BOUND*4)
        shapeDrawable.getPaint().setColor(Color.BLACK)
        shapeDrawable.draw(canvas)

        //binding.idTurn.setOnClickListener(){

       // }






        // set bitmap as background to ImageView
       // binding.idCanvas.background = BitmapDrawable(getResources(), bitmap)
    }

}*/