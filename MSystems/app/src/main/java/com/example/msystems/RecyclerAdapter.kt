package com.example.msystems

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.msystems.databinding.RowBinding

var elements = mutableListOf<Element>(
    Element("поспать", 23,7),
    Element("проснуться", 6,1),
    Element("пробежка", 6,12)
)

class RecyclerAdapter(val listener : Listener) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(item : View) : RecyclerView.ViewHolder(item){
        //var description: TextView
        //var time: TextView
        private val binding = RowBinding.bind(item)
        fun init(element: Element, listener: Listener) {
            binding.description.text = element.description
            binding.time.text = element.getTime()
            binding.edit.setText("")
            binding.edit.visibility = if(element.description == "") View.VISIBLE else View.INVISIBLE
            //description = item.findViewById(R.id.description)
            //time = item.findViewById(R.id.time)
            binding.delete.setOnClickListener(){
                listener.onClickDelete(element)
            }
            binding.time.setOnClickListener(){
                listener.onClickTime(element)
            }
            binding.description.setOnClickListener(){
                listener.onClickText(element)
            }
            binding.edit.setOnKeyListener(View.OnKeyListener{v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    element.description = binding.edit.text.toString()
                    binding.description.text = element.description
                    binding.edit.visibility = View.INVISIBLE
                    return@OnKeyListener true
                }
                false
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(elements[position], listener)
        //holder.description.text = elements[position].description
        //holder.time.text = elements[position].getTime()
    }

    override fun getItemCount(): Int {
        return elements.size
    }

    fun add(element : Element){
        elements.add(element)
        notifyDataSetChanged()
        elements.sortBy { it.year }
    }

    fun del(element : Element){
        element.delete()
        elements.remove(element)
        notifyDataSetChanged()
        elements.sortBy { it.year }
    }

    fun changedTime(element: Element, day : Int, month : Int, year : Int){
        element.setTime(day, month, year)
        notifyDataSetChanged()
        elements.sortBy { it.year }
    }

    interface Listener{
        fun onClickDelete(element : Element)
        fun onClickTime(element: Element)
        fun onClickText(element: Element)
    }
}