package com.firstapp.hilocardgameapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity

import com.firstapp.hilocardgameapp.databinding.GameActivityBinding


class GameActivity : AppCompatActivity() {

    lateinit var cards : IntArray
    lateinit var binding: GameActivityBinding
    lateinit var sharedLastCard : SharedPreferences
    lateinit var sharedRandom : SharedPreferences

    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GameActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sharedCurrentScore = getSharedPreferences("sharedCurrentScore", Context.MODE_PRIVATE)
        val sharedBestScore = getSharedPreferences("sharedBestScore", Context.MODE_PRIVATE)
        sharedLastCard = getSharedPreferences("sharedLastCard", Context.MODE_PRIVATE)
        sharedRandom = getSharedPreferences("sharedRandom", Context.MODE_PRIVATE)

        binding.cardBackSide.startAnimation(AnimationUtils.loadAnimation(this@GameActivity, R.anim.jump))
        binding.scoreStreak.text = sharedCurrentScore.getInt("score", 0).toString()

       // binding.spriteImage.setBackgroundResource(R.drawable.anim)
       // var mAnimationDrawable = binding.spriteImage.background as AnimationDrawable
        // mAnimationDrawable.start()




        binding.apply {

            higherButton.setOnClickListener {
                val newCard = drawCard()
                var score = sharedCurrentScore.getInt("score", 0)
                var bestScore = sharedBestScore.getInt("bestScore", 0)
                var lastCard = sharedLastCard.getInt("lastCard", 0)

                if (lastCard <= newCard) {
                    score++
                    if (score > bestScore) {
                        sharedBestScore.edit().apply {
                            this.putInt("bestScore", score)
                            commit()
                        }
                    }
                    binding.highScoreText.setTextColor(Color.parseColor(getString(R.color.normalGreen)))
                    binding.scoreStreak.setTextColor(Color.parseColor(getString(R.color.normalGreen)))
                } else {
                    score = 0
                    binding.highScoreText.setTextColor(Color.parseColor(getString(R.color.normalRed)))
                    binding.scoreStreak.setTextColor(Color.parseColor(getString(R.color.normalRed)))
                }

                lastCard = newCard
                sharedLastCard.edit().apply {
                    this.putInt("lastCard", lastCard)
                    commit()
                }
                scoreStreak.text = score.toString()

                sharedCurrentScore.edit().apply {
                    this.putInt("score", score)
                    commit()
                }

                higherButton.startAnimation(AnimationUtils.loadAnimation(this@GameActivity, R.anim.alpha))
                cardBackSide.startAnimation(AnimationUtils.loadAnimation(this@GameActivity, R.anim.new_card))
            }

            lowerButton.setOnClickListener {
                val newCard = drawCard()
                var score = sharedCurrentScore.getInt("score", 0)
                val bestScore = sharedBestScore.getInt("bestScore", 0)
                var lastCard = sharedLastCard.getInt("lastCard", 0)

                if (lastCard >= newCard) {
                    score++
                    if (score > bestScore) {
                        sharedBestScore.edit().apply {
                            this.putInt("bestScore", score)
                            commit()
                        }
                    }
                    binding.highScoreText.setTextColor(Color.parseColor(getString(R.color.normalGreen)))
                    binding.scoreStreak.setTextColor(Color.parseColor(getString(R.color.normalGreen)))
                } else {
                    score = 0
                    binding.highScoreText.setTextColor(Color.parseColor(getString(R.color.normalRed)))
                    binding.scoreStreak.setTextColor(Color.parseColor(getString(R.color.normalRed)))
                }

                lastCard = newCard
                sharedLastCard.edit().apply {
                    this.putInt("lastCard", lastCard)
                    commit()
                }
                scoreStreak.text = score.toString()

                sharedCurrentScore.edit().apply {
                    this.putInt("score", score)
                    commit()
                }


                lowerButton.startAnimation(AnimationUtils.loadAnimation(this@GameActivity, R.anim.alpha))
                cardBackSide.startAnimation(AnimationUtils.loadAnimation(this@GameActivity, R.anim.new_card))
            }

            pointsButton.setOnClickListener { startActivity(Intent(this@GameActivity, PointsActivity::class.java)) }
        }

        cards = intArrayOf(
            R.drawable.c2_of_hearts,
            R.drawable.c2_of_diamonds,
            R.drawable.c2_of_clubs,
            R.drawable.c2_of_spades,
            R.drawable.c3_of_diamonds,
            R.drawable.c3_of_hearts,
            R.drawable.c3_of_clubs,
            R.drawable.c3_of_spades,
            R.drawable.c4_of_diamonds,
            R.drawable.c4_of_hearts,
            R.drawable.c4_of_clubs,
            R.drawable.c4_of_spades,
            R.drawable.c5_of_diamonds,
            R.drawable.c5_of_hearts,
            R.drawable.c5_of_clubs,
            R.drawable.c5_of_spades,
            R.drawable.c6_of_diamonds,
            R.drawable.c6_of_hearts,
            R.drawable.c6_of_clubs,
            R.drawable.c6_of_spades,
            R.drawable.c7_of_diamonds,
            R.drawable.c7_of_hearts,
            R.drawable.c7_of_clubs,
            R.drawable.c7_of_spades,
            R.drawable.c8_of_diamonds,
            R.drawable.c8_of_hearts,
            R.drawable.c8_of_clubs,
            R.drawable.c8_of_spades,
            R.drawable.c9_of_diamonds,
            R.drawable.c9_of_hearts,
            R.drawable.c9_of_clubs,
            R.drawable.c9_of_spades,
            R.drawable.c10_of_diamonds,
            R.drawable.c10_of_hearts,
            R.drawable.c10_of_clubs,
            R.drawable.c10_of_spades,
            R.drawable.jack_of_diamonds,
            R.drawable.jack_of_hearts,
            R.drawable.jack_of_clubs,
            R.drawable.jack_of_spades,
            R.drawable.queen_of_diamonds,
            R.drawable.queen_of_hearts,
            R.drawable.queen_of_clubs,
            R.drawable.queen_of_spades,
            R.drawable.king_of_diamonds,
            R.drawable.king_of_hearts,
            R.drawable.king_of_clubs,
            R.drawable.king_of_spades,
            R.drawable.ace_of_diamonds,
            R.drawable.ace_of_hearts,
            R.drawable.ace_of_clubs,
            R.drawable.ace_of_spades)

        if(sharedLastCard.getInt("lastCard", 0)  == EMPTY) {
            sharedLastCard.edit().apply {
                this.putInt("lastCard", drawCard())
                commit()
            }
        }else{
            binding.spriteImage.setImageResource(cards[sharedRandom.getInt("random", 0)])
        }
    }

    private fun drawCard(): Int {
        val random = (0..51).random()
        sharedRandom.edit().apply {
            this.putInt("random", random)
            commit()
        }
        binding.spriteImage.setImageResource(cards[random])
        if(random / 4 == sharedLastCard.getInt("lastCard", 0)) {
            println("repeat")
            drawCard()
        }
        return random / 4
    }
}
