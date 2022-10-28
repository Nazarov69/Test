package com.example.msystems

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.example.msystems.databinding.ActivityCounterBinding


class Counter : AppCompatActivity() {
    lateinit var binding: ActivityCounterBinding
    private var click = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCounterBinding.inflate(layoutInflater)
        binding.idTextClicks.text = click.toString()
        setContentView(binding.root)
    }
    fun onClickLogo(view : View){
        binding.idLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise))
    }

    fun onClickButton(view : View){
        binding.apply {
            click++
            idTextClicks.text = click.toString()
        }
    }

    fun onCLickMinus(view : View){
        binding.apply {
            click--
            idTextClicks.text = click.toString()
        }
    }

    fun onCLickZero(view : View){
        binding.apply {
            click = 0
            idTextClicks.text = click.toString()
        }
    }

    fun onClick(view : View){
        setResult(RESULT_OK, Intent().putExtra("key", click.toString()))
        finish()
    }
}