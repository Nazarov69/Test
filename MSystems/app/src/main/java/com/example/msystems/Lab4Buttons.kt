package com.example.msystems

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.msystems.databinding.ActivityButtonsBinding
import java.util.*

class Lab4Buttons : AppCompatActivity() {
    lateinit var binding: ActivityButtonsBinding
    @SuppressLint("ClickableViewAccessibility")
    private var click : Int = 0
    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtonsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idNumber.text = Positions.CURRENT.toString() + "/" + Positions.ALL.toString()
        binding.idDate.text = getString(R.string.currentDate) + " ${
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        }.${Calendar.getInstance().get(Calendar.MONTH) + 1}.${
            Calendar.getInstance().get(Calendar.YEAR)
        }"
        binding.idTime.text = getString(R.string.currentTime) + " ${
            Calendar.getInstance().get(Calendar.HOUR)
        }:${Calendar.getInstance().get(Calendar.MINUTE)}"
        binding.edit.error = "Необходимо заполнить поле"
        binding.idAddmited.setOnTouchListener { view, motionEvent ->
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.idAddmited.apply {
                        setText(R.string.admitted)
                        setTextColor(Color.parseColor(getString(R.color.head)))
                        setBackgroundResource(R.drawable.stroke_ok)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    binding.idAddmited.apply {
                        setText(R.string.notAdmitted)
                        setTextColor(Color.parseColor(getString(R.color.gray)))
                        setBackgroundResource(R.drawable.stroke_cancel)
                    }
                }
            }
            view?.onTouchEvent(motionEvent) ?: true
        }

        binding.idSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.idSeekText.text = seekBar.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        var calendar: Calendar = Calendar.getInstance()
        binding.idDatePicker.init(
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            var t = " "
            t += if(day < 10) "0$day." else "$day."
            t += if(month < 10) "0$month." else "$month."
            t += "$year"
            binding.idDate.text = getString(R.string.currentDate) + t
        }

        binding.idTimePicker.setOnTimeChangedListener { timepicker, hourOfDay, minute ->
            var t = " "
            t += if(hourOfDay < 10) "0$hourOfDay:" else "$hourOfDay:"
            t += if(minute < 10) "0$minute" else "$minute"
            binding.idTime.text = getString(R.string.currentTime) + t
        }

        val timezone = arrayOf("Дальний Восток", "Сибирь", "Москва")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timezone)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.idSpinner.adapter = adapter
        binding.idSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                    0 -> Toast.makeText(this@Lab4Buttons, "GMT+7 (${timezone[0]})", Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(this@Lab4Buttons, "GMT+5 (${timezone[1]})", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(this@Lab4Buttons, "GMT+3 (${timezone[2]})", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
    fun onClickButton(view: View){
        binding.apply {
            click++
            idClickText.text = click.toString()
        }
    }


    @SuppressLint("ResourceAsColor", "ResourceType")
    fun onClickStatus(view : View){
        binding.idStatus.apply {
            setText(R.string.clickReady)
            setTextColor(Color.parseColor(getString(R.color.head)))
            setBackgroundResource(R.drawable.stroke_ok)
        }
    }

    fun onClickLogo(view : View){
        binding.idLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise))
    }

    fun onClickNext(view : View){
        startActivity(Intent(this@Lab4Buttons, Lab5Containers::class.java))
        Positions.CURRENT++
    }

    fun onClickPrev(view: View) {
        startActivity(Intent(this@Lab4Buttons, Lab3Squares::class.java))
        Positions.CURRENT--
    }
}