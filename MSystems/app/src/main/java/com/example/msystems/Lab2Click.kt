package com.example.msystems

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.example.msystems.databinding.ActivityMainBinding

class Lab2Click : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var click : Int = 0
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idTextClicks.text = click.toString()
        binding.idNumber.text = Positions.CURRENT.toString() + "/" + Positions.ALL.toString()
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

    fun onClickNext(view : View){
        startActivity(Intent(this@Lab2Click, Lab3Squares::class.java))
        Positions.CURRENT++
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
}