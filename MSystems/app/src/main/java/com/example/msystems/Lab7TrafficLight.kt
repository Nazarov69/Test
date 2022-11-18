package com.example.msystems

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.msystems.databinding.ActivityTrafficLightBinding
import java.util.*

class Lab7TrafficLight : AppCompatActivity(), TimerAdapter.Listener {
    lateinit var binding: ActivityTrafficLightBinding
    var iterator = -1
    lateinit var timer : Timer
    lateinit var t : Timer
    var isRun = false
    private var layoutManager : RecyclerView.LayoutManager? = null
    private val adapter = TimerAdapter(this)
    var imgList = listOf(R.drawable.red, R.drawable.yellow, R.drawable.green, R.drawable.yellow)
    private lateinit var launcher : ActivityResultLauncher<Intent>
    @SuppressLint("ClickableViewAccessibility", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrafficLightBinding.inflate(layoutInflater)
        layoutManager = LinearLayoutManager(this)
        binding.idRecyclerView.layoutManager = layoutManager
        binding.idRecyclerView.adapter = adapter
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
                binding.button.visibility = View.VISIBLE
                binding.create.text = "Кнопка создана"
                binding.idSpinner.visibility = View.VISIBLE
                var color = result.data?.getStringExtra("key").toString()
                when(color){
                    "Изумрудный" -> binding.button.setBackgroundColor(Color.parseColor("#019980"))
                    "Синий" -> binding.button.setBackgroundColor(Color.parseColor("#0F64A7"))
                    "Сизый" -> binding.button.setBackgroundColor(Color.parseColor("#768CC0"))
                    "Цвет" -> binding.button.setBackgroundColor(Color.parseColor("#FF595959"))
                }
            }
        }
        val description = arrayOf("Описание 1", "Описание 2", "Описание 3")
        val adapt = ArrayAdapter(this, android.R.layout.simple_spinner_item, description)
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.idSpinner.adapter = adapt
        binding.idSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.button.text = when(p2){
                    0 -> description[0]
                    1 -> description[1]
                    2 -> description[2]
                    else -> {""}
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        val str = ArrayList<String>()
        binding.addTime.setOnClickListener(){
            adapter.add(0,binding.idTimer.text.toString())
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
        startActivity(Intent(this@Lab7TrafficLight, Lab6Lists::class.java))
        Positions.CURRENT--
    }

    fun onClickColors(view: View) {
        launcher?.launch(Intent(this@Lab7TrafficLight, Colors::class.java))
    }
}