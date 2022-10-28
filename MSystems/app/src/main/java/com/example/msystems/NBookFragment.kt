package com.example.msystems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


const val ARG_DESCRIPTION = "description"
const val ARG_DATE = "date"
const val ARG_POSITION = "position"

class NBookFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_n_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_DESCRIPTION)}?. apply{
            val description= view.findViewById<TextView>(R.id.description)
            description.text = getString(ARG_DESCRIPTION)
        }
        arguments?.takeIf { it.containsKey(ARG_DATE) }?.apply {
            val date = view.findViewById<TextView>(R.id.date)
            date.text = getString(ARG_DATE)
        }
        arguments?.takeIf { it.containsKey(ARG_POSITION) }?.apply {
            val position = view.findViewById<TextView>(R.id.position)
            position.text = getString(ARG_POSITION) + "/${elements.size}"
        }
    }
}