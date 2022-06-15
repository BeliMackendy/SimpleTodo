package com.mypath.simpletodo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
      private var listOfTasks = mutableListOf<String>()
      lateinit var  adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickLister = object :TaskItemAdapter.OnLongClickListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemLongClicked(position: Int) {
                // Remove the Item form the list
                listOfTasks.removeAt(position)

                // Notify that the adapter has changed.
                adapter.notifyDataSetChanged()

                saveItems()

                Toast.makeText(this@MainActivity, "Item Removed", Toast.LENGTH_SHORT).show()
            }
        }

        loadItems()

        // Lookup the recyclerview in activity layout
        val rvItems:RecyclerView = findViewById(R.id.rvItems)

        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks,onLongClickLister)

        // Attach the adapter to the recyclerview to populate items
        rvItems.adapter = adapter

        // Set layout manager to position the items
        rvItems.layoutManager = LinearLayoutManager(this)

        val btAdd:Button = findViewById(R.id.btAdd)
        val addTaskField:TextView = findViewById(R.id.addTaskField)

        btAdd.setOnClickListener {
            val inputTaskField = addTaskField.text.toString()

            // Add the string to our list of task
            listOfTasks.add(inputTaskField)

            // Notify that item reflected at the position has been newly inserted.
            adapter.notifyItemInserted(listOfTasks.size-1)

            // Reset text field
            addTaskField.text=""

            saveItems()

            Toast.makeText(this@MainActivity, "Item Inserted", Toast.LENGTH_SHORT).show()
        }
    }

    // get the file we need
    private fun  getDataFile():File{
        return File(filesDir,"data.txt")
    }

    private fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }

    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),listOfTasks)
        }catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }
}