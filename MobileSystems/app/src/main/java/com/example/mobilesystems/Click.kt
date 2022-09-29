package com.example.mobilesystems

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.example.mobilesystems.databinding.ActivityMainBinding

class Click : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var click : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idLogo.setImageResource(R.drawable.logo)
        binding.idTextClicks.text = click.toString()
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
        startActivity(Intent(this@Click, Squares::class.java))
    }


}