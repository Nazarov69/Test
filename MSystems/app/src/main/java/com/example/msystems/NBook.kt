package com.example.msystems

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.msystems.databinding.ActivityNbookBinding


class NBook : FragmentActivity() {
    lateinit var binding: ActivityNbookBinding
    private var adapter : NBookAdapter = NBookAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNbookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nbook.adapter = adapter
    }

}