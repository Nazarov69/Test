package com.example.msystems

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.msystems.databinding.ActivityRecyclerBinding
import java.util.*

class Lab5Recycler : AppCompatActivity(), RecyclerAdapter.Listener {
    lateinit var binding : ActivityRecyclerBinding
    private var layoutManager : RecyclerView.LayoutManager? = null
    private val adapter = RecyclerAdapter(this)
    var calendar: Calendar = Calendar.getInstance()
    var isChanged = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layoutManager = LinearLayoutManager(this)
        binding.idRecyclerView.layoutManager = layoutManager
        binding.idRecyclerView.adapter = adapter
    }

    fun onClickAdd(view: View) {
        adapter.add(Element("", 0,0,0))
    }

    override fun onClickDelete(element: Element) {
        adapter.del(element)
    }

    override fun onClickTime(element: Element) {
        binding.pickerDate.visibility = View.VISIBLE
        binding.acc.visibility = View.VISIBLE
        binding.idRecyclerView.visibility = View.GONE
        binding.pickerDate.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            adapter.changedTime(element, day, month, year)
        }

    }



    override fun onClickText(element: Element){

        Toast.makeText(this,"1313",Toast.LENGTH_SHORT).show()
    }

    fun onClickAcc(view: View) {
        isChanged = true
        binding.acc.visibility = View.INVISIBLE
        binding.pickerDate.visibility = View.INVISIBLE
        binding.idRecyclerView.visibility = View.VISIBLE
    }


}