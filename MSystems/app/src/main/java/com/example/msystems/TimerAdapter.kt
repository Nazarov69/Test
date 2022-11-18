package com.example.msystems

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.msystems.databinding.DbElemBinding

class TimerAdapter(private val listener : Listener) : RecyclerView.Adapter<TimerAdapter.ViewHolder>() {
    inner class ViewHolder(item : View) : RecyclerView.ViewHolder(item){
        private val binding = DbElemBinding.bind(item)

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun init(element: Elem_db, listener: Listener) {
            binding.id.text = "${element.index}"
            binding.text.text = element.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.db_elem, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(DB[position], listener)
    }

    override fun getItemCount(): Int {
        return DB.size
    }

    fun add(id : Int, text : String){
        DB.add(Elem_db(id, text))
        notifyDataSetChanged()
    }


    interface Listener{
    }
}