package com.example.msystems

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.msystems.databinding.ActivityNumBinding

class Num : AppCompatActivity() {
    private lateinit var adapter : NumAdapter
    private lateinit var viewPager : ViewPager2
    lateinit var binding : ActivityNumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NumAdapter(this)
        binding.pager.adapter = adapter
    }
}