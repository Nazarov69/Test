package com.firstapp.hilocardgameapp


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firstapp.hilocardgameapp.databinding.PointsActivityBinding


class PointsActivity : AppCompatActivity() {

    lateinit var binding : PointsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PointsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playAgainButton.setOnClickListener { startActivity(Intent(this, GameActivity::class.java)) }
        binding.exitButton.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }

        val sharedCurrentScore = getSharedPreferences("sharedCurrentScore", Context.MODE_PRIVATE)
        val sharedBestScore = getSharedPreferences("sharedBestScore", Context.MODE_PRIVATE)

        binding.scoreStreak.text = sharedCurrentScore.getInt("score", 0).toString()
        binding.bestStreak.text = sharedBestScore.getInt("bestScore", 0).toString()

    }
}
