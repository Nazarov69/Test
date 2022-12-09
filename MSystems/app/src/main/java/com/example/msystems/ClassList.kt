package com.example.msystems

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.msystems.databinding.DbElemBinding
import java.util.*

var list = mutableListOf<Elem_db>()
class ClassList(val listener : Listener) : RecyclerView.Adapter<ClassList.ViewHolder>() {

    inner class ViewHolder(item : View) : RecyclerView.ViewHolder(item){
        private val binding = DbElemBinding.bind(item)
        @SuppressLint("SetTextI18n")
        fun init(element: Elem_db, listener: Listener) {
            binding.id.text = "${element.index}"
            binding.text.text = element.text
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.db_elem, parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(list[position], listener)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    fun add(new : String){
        list.add(Elem_db(list.size, new.lowercase(Locale.getDefault())))
        notifyDataSetChanged()
        listener.text(list)
    }
    fun del(){
        if(list.size > 0)
        list.removeAt(list.size - 1)
        notifyDataSetChanged()
        listener.text(list)
    }
    interface Listener{
        //fun add(new : String)
        //fun del()
        fun text(list : MutableList<Elem_db>)
    }
}