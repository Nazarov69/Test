package com.bullfrog.particle

import android.animation.ValueAnimator
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bullfrog.particle.animation.ParticleAnimation
import com.bullfrog.particle.particle.configuration.Rotation
import com.bullfrog.particle.particle.configuration.Shape
import com.bullfrog.particle.path.IPathGenerator
import com.bullfrog.particle.path.LinearPathGenerator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private var mp : MediaPlayer? = null

    //private var currentSong = mutableListOf(R.raw.music)

    private lateinit var button: TextView

    private lateinit var play : FloatingActionButton

    private lateinit var pause : FloatingActionButton

    private lateinit var stop : FloatingActionButton

    private lateinit var seekBar : SeekBar

    private lateinit var container: ViewGroup

    private var particleManager: IParticleManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button = findViewById(R.id.button)
        container = findViewById(R.id.container)


        var sprite_image = findViewById<ImageView>(R.id.sprite_image);
        sprite_image.setBackgroundResource(R.drawable.running_anim);

        var mAnimationDrawable = sprite_image.background as AnimationDrawable
        mAnimationDrawable.start()



        var video = findViewById<VideoView>(R.id.video2)
        val path = "android.resource://" + packageName + "/" + R.raw.video
        val mediaController = MediaController(this)
        video.setMediaController(mediaController)
        mediaController.setAnchorView(video)
        video.setVideoPath(path)
        video.start()


        container.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                particleManager = Particles.with(this, container)
                particleManager!!.colorFromView(button)
                    .particleNum(100)
                    .anchor(event.x.toInt(), event.y.toInt())
                    .shape(Shape.CIRCLE)
                    .radius(2, 6)
                    .anim(ParticleAnimation.EXPLOSION)
                    .start()
            }
            true
        })

        button.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                particleManager = Particles.with(this, container)
                particleManager!!.colorFromView(button)
                    .particleNum(500)
                    .anchor(button)
                    .shape(Shape.HOLLOW_RECTANGLE)
                    .radius(8, 12)
                    .size(25, 25)
                    .strokeWidth(5f)
                    .rotation(Rotation(600))
                    .anim(ParticleAnimation.with({createAnimator()}, { createPathGenerator() }))
                    .start()
            }
            if(event.action == MotionEvent.ACTION_UP){
                particleManager?.cancel()
            }
            true
        })



        var play : FloatingActionButton = findViewById(R.id.play)
        var pause : FloatingActionButton= findViewById(R.id.pause)
        var stop : FloatingActionButton= findViewById(R.id.stop)
        var seekBar : SeekBar = findViewById(R.id.seekbar)

        play.setOnClickListener{
            if(mp == null){
                mp = MediaPlayer.create(this, R.raw.music)
                seekBar.max = mp!!.duration

                val handler = Handler()
                handler.postDelayed(object : Runnable{
                    override fun run() {
                        try{
                            seekBar.progress = mp!!.currentPosition
                            handler.postDelayed(this, 1000)
                        } catch(e : Exception){
                            seekBar.progress = 0
                        }
                    }
                }, 0)
            }
            mp?.start()
        }
        pause.setOnClickListener{
            if(mp != null) mp?.pause()
        }
        stop.setOnClickListener{
            if(mp != null){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(p2) mp?.seekTo(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun createPathGenerator(): IPathGenerator {
        return object : LinearPathGenerator() {
            val cos = Random.nextDouble(-1.0, 1.0)
            val sin = Random.nextDouble(-1.0, 1.0)

            override fun getCurrentCoord(
                progress: Float,
                duration: Long,
                outCoord: IntArray
            ): Unit {
                val originalX = distance * progress
                val originalY = 100 * sin(originalX / 50)
                val x = originalX * cos - originalY * sin
                val y = originalX * sin + originalY * cos
                outCoord[0] = (0.01 * x * originalY).toInt()
                outCoord[1] = -(0.0001 * y.pow(2) * originalX).toInt()
            }
        }
    }

    private fun createAnimator(): ValueAnimator {
        val animator = ValueAnimator.ofInt(0, 1)
        animator.repeatCount = -1
        animator.repeatMode = ValueAnimator.REVERSE
        animator.duration = 4000L
        return animator
    }


}
