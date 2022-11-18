package com.example.msystems

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.msystems.databinding.RateBinding

class RateAdapter(private val listener : Listener) : RecyclerView.Adapter<RateAdapter.ViewHolder>() {
    inner class ViewHolder(item : View) : RecyclerView.ViewHolder(item){
        private val binding = RateBinding.bind(item)

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun init(element: Elem_db, listener: Listener) {
            binding.currency.text = element.text
            binding.rate.text = "${element.index}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rate, parent, false))
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