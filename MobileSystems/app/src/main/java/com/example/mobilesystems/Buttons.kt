package com.example.mobilesystems

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ResultReceiver
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import com.example.mobilesystems.databinding.ActivityButtonsBinding

class Buttons : AppCompatActivity() {
    lateinit var binding: ActivityButtonsBinding
    @SuppressLint("ClickableViewAccessibility")
    private var click : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtonsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edit.error = "Необходимо заполнить поле"
        binding.idAddmited.setOnTouchListener { view, motionEvent ->
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.idAddmited.apply {
                        setText(R.string.admitted)
                        setTextColor(Color.GREEN)
                        setBackgroundResource(R.drawable.stroke_ok)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    binding.idAddmited.apply {
                        setText(R.string.notAdmitted)
                        setTextColor(Color.RED)
                        setBackgroundResource(R.drawable.stroke_cancel)
                    }
                }
            }
            view?.onTouchEvent(motionEvent) ?: true
        }
    }

    fun onClickButton(view: View){
        binding.apply {
            click++
            idClickText.text = click.toString()
        }
    }


    @SuppressLint("ResourceAsColor")
    fun onClickStatus(view : View){
        binding.idStatus.apply {
            setText(R.string.clickReady)
            setTextColor(Color.GREEN)
            setBackgroundResource(R.drawable.stroke_ok)
        }
    }

    fun onClickLogo(view : View){
        binding.idLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise))
    }
}