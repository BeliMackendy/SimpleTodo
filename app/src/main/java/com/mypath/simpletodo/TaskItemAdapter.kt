package com.mypath.simpletodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter(val lisOfItems:List<String>):RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val interview = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(interview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item= lisOfItems[position]

        // Set item views based on your views and data model
        val textView = holder.textView
        textView.text = item
    }

    override fun getItemCount(): Int {
       return  lisOfItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView:TextView
        init{
            textView= itemView.findViewById(android.R.id.text1)
        }
    }
}