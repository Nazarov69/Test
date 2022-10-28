package com.example.msystems

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.msystems.databinding.ActivityTrafficLightBinding
import java.util.*

class Lab7TrafficLight : AppCompatActivity() {
    lateinit var binding: ActivityTrafficLightBinding
    var iterator = -1
    lateinit var timer : Timer
    lateinit var t : Timer
    var isRun = false
    var imgList = listOf(R.drawable.red, R.drawable.yellow, R.drawable.green, R.drawable.yellow)
    private lateinit var launcher : ActivityResultLauncher<Intent>
    @SuppressLint("ClickableViewAccessibility", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrafficLightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idNumber.text = Positions.CURRENT.toString() + "/" + Positions.ALL.toString()
        binding.text.setOnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.text.startAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.rotate180_down
                    ))
                    binding.text.setTextColor(Color.parseColor(getString(R.color.my_green)))
                }
                MotionEvent.ACTION_UP -> {
                    binding.text.startAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.rotate180_up
                    ))
                    binding.text.setTextColor(Color.parseColor(getString(R.color.logo)))
                }
            }
            view?.onTouchEvent(event) ?: true
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode == RESULT_OK){
                Toast.makeText(this, result.data?.getStringExtra("key").toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun onClickLogo(view : View){
        binding.idLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise))
    }

    fun onClickStartStop(view : View){
        view as ImageButton
        var second = 0
        var minute = 0
        var hour = 0
        var time = ""
        if(!isRun){
            timer = Timer()
            timer.schedule(object : TimerTask(){
                override fun run() {
                    runOnUiThread(){
                        time += if(hour < 10) "0$hour:" else "$hour:"
                        time += if(minute < 10) "0$minute:" else "$minute:"
                        time += if(second < 10) "0$second" else "$second"
                        second++
                        hour = (hour + (minute + second / 60) / 60) % 24
                        minute = (minute + second / 60) % 60
                        second %= 60
                        binding.idTimer.text = time
                        time = ""
                    }
                }
            }, 0, 1000)
            t = Timer()
            t.schedule(object : TimerTask(){
                override fun run() {
                    if (iterator == imgList.size - 1) iterator = -1
                    binding.idLight.setImageResource(imgList[++iterator])
                    if (imgList[iterator] == R.drawable.green) {
                        binding.idGo.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@Lab7TrafficLight,
                                R.anim.go
                            )
                        )
                        Thread.sleep(4000)
                    }

                } },0,1000)

            view.setImageResource(R.drawable.button_stop)
            isRun = true
        }
        else{
            timer.cancel()
            t.cancel()
            view.setImageResource(R.drawable.button_start)
            binding.idLight.setImageResource(R.drawable.grey)
            isRun = false
            iterator = -1
            second = 0
            minute = 0
            hour = 0
            binding.idTimer.text = "0$hour:0$minute:0$second"

        }
    }

    fun onClickText(view: View) {}
    fun onClickPrev(view: View) {
        startActivity(Intent(this@Lab7TrafficLight, Lab5Containers::class.java))
        Positions.CURRENT--
    }
}