package com.example.msystems

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NBookAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = elements.size

    override fun createFragment(position: Int): Fragment {
        val fragment = NBookFragment()
        fragment.arguments = Bundle().apply {
            putString(ARG_DESCRIPTION, elements[position].description.toString())
            putString(ARG_DATE, elements[position].getTime())
            putString(ARG_POSITION, (position + 1).toString())
        }
        return fragment
    }
}