package com.example.msystems

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.msystems.databinding.ActivityColorsBinding

class Colors : AppCompatActivity() {
    lateinit var binding : ActivityColorsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClickBack(view: View) {
        setResult(RESULT_OK, Intent().putExtra("key", "Сизый"))
        finish()
    }
    fun onClickBl(view: View) {
        setResult(RESULT_OK, Intent().putExtra("key", "Синий"))
        finish()
    }
    fun onClickGr(view: View) {
        setResult(RESULT_OK, Intent().putExtra("key", "Изумрудный"))
        finish()
    }

}