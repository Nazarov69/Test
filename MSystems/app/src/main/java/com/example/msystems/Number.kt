package com.example.msystems

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.msystems.databinding.ActivityNumberBinding


class Number : FragmentActivity(){
    lateinit var binding: ActivityNumberBinding
    private var adapter: NumberAdapter = NumberAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = adapter
    }
}