package com.example.msystems

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.msystems.databinding.ActivityLab8Binding
import java.util.*

class Lab8 : AppCompatActivity(), ClassList.Listener {
    lateinit var binding : ActivityLab8Binding
    private var click  = ClassClick(5)
    private var layoutManager : RecyclerView.LayoutManager? = null
    private val adapter = ClassList(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLab8Binding.inflate(layoutInflater)
        binding.data.text = click.get().toString()
        setContentView(binding.root)
        layoutManager = LinearLayoutManager(this)
        binding.idRecyclerView.layoutManager = layoutManager
        binding.idRecyclerView.adapter = adapter

    }

    fun onClickCancel(view: View) {
        binding.data.text = click.cancel().toString()
    }
    fun onClickAdd(view: View) {
        binding.data.text = click.add().toString()
    }
    fun onClickLogo(view : View){
        binding.idLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise))
    }

    fun onClickCancel2(view: View) {
        adapter.del()
    }
    fun onClickAdd2(view: View) {
        adapter.add(binding.edit.text.toString())
    }

    @SuppressLint("SetTextI18n")
    override fun text(list : MutableList<Elem_db>) {
        binding.text.text = ""
        for(l in list){
            if(l.index == 0)
                binding.text.text = l.text.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            else
                binding.text.text = binding.text.text.toString() + ", " + l.text
        }

    }
}