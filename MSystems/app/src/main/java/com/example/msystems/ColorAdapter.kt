package com.example.msystems

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.example.msystems.databinding.ColorsBinding

var colors = mutableListOf<Elem_color>(
    Elem_color("Цвет", 1),
    Elem_color("Цвет", 2),
    Elem_color("Цвет", 3)
)
var count : Int = 3
class ColorAdapter(val listener : Listener) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {
    inner class ViewHolder(item : View) : RecyclerView.ViewHolder(item){
        private val binding = ColorsBinding.bind(item)

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun init(element: Elem_color, listener: Listener) {
            binding.id.text = "${element.index}"
            binding.text.text = element.color
            when(element.color){
                "Изумрудный" -> binding.main.setCardBackgroundColor(Color.parseColor("#019980"))
                "Синий" -> binding.main.setCardBackgroundColor(Color.parseColor("#0F64A7"))
                "Сизый" -> binding.main.setCardBackgroundColor(Color.parseColor("#768CC0"))
                else -> binding.main.setCardBackgroundColor(Color.parseColor("#FF595959"))
            }
            binding.set.setOnClickListener(){
                listener.onClickColor(element)
            }
            binding.main.setOnClickListener(){
                element.color = "Цвет"
                colors.remove(element)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.colors, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(colors[position], listener)
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    fun color(element: Elem_color, color : String){
        element.set(color)
        notifyDataSetChanged()
    }
    fun add(){
        colors.add(Elem_color("Цвет", ++count))
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClickColor(elemColor: Elem_color)
    }
}

