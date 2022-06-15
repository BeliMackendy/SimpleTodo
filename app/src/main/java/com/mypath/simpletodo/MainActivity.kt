package com.mypath.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
      private val listOfTasks = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOfTasks.add("First Item")
        listOfTasks.add("Second Item")

        // Lookup the recyclerview in activity layout
        val rvItems:RecyclerView = findViewById(R.id.rvItems)

        // Create adapter passing in the sample user data
        val adapter = TaskItemAdapter(listOfTasks)

        // Attach the adapter to the recyclerview to populate items
        rvItems.adapter = adapter

        // Set layout manager to position the items
        rvItems.layoutManager = LinearLayoutManager(this)

        val btAdd:Button = findViewById(R.id.btAdd)
        val addTaskField:TextView = findViewById(R.id.addTaskField)

        btAdd.setOnClickListener {
            val inputTaskField = addTaskField.text.toString()

            listOfTasks.add(inputTaskField)

            adapter.notifyItemInserted(listOfTasks.size-1)

            addTaskField.text=""
        }

    }
}