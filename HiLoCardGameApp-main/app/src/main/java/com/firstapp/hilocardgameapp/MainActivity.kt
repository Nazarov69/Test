package com.firstapp.hilocardgameapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.firstapp.hilocardgameapp.databinding.ActivityMainBinding

const val EMPTY = -36218

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedCurrentScore = getSharedPreferences("sharedCurrentScore", Context.MODE_PRIVATE)
        val sharedLastCard = getSharedPreferences("sharedLastCard", Context.MODE_PRIVATE)
        binding.rulesButton.setOnClickListener{ startActivity(Intent(this, InfoActivity::class.java)) }
        binding.startButton.setOnClickListener {
            sharedCurrentScore.edit().apply(){
                this.putInt("score", 0)
                commit()
            }
            sharedLastCard.edit().apply {
                this.putInt("lastCard", EMPTY)
                commit()
            }
            startActivity(Intent(this, GameActivity::class.java))
        }
        binding.imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image))
    }
}
