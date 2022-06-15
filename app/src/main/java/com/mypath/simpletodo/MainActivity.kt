package com.mypath.simpletodo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {
      private var listOfTasks = mutableListOf<String>()
      lateinit var  adapter : TaskItemAdapter

    @SuppressLint("NotifyDataSetChanged")
    var editActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // If the user comes back to this activity from EditActivity
        // with no error or cancellation
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            // Get the data passed from EditActivity
            if (data != null) {
                val itemEditedString = data.extras!!.getString("ITEM_TEXT")
                val itemPosition = data.extras!!.getInt("ITEM_POSITION")

                listOfTasks[itemPosition] = itemEditedString.toString()
                adapter.notifyDataSetChanged()
                saveItems()
                Toast.makeText(this@MainActivity, "Item Changed", Toast.LENGTH_SHORT).show()
            }
        }
    }

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

        val onClickListener = object :TaskItemAdapter.OnClickListener{
            override fun onItemClicked(position: Int) {
                // first parameter is the context, second is the class of the activity to launch
                val i = Intent(this@MainActivity, EditActivity::class.java)

                // put "extras" into the bundle for access in the second activity
                i.putExtra("ITEM_TEXT", listOfTasks[position])
                i.putExtra("ITEM_POSITION", position)
                // brings up the second activity
                editActivityResultLauncher.launch(i)
            }

        }

        loadItems()

        // Lookup the recyclerview in activity layout
        val rvItems:RecyclerView = findViewById(R.id.rvItems)

        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks,onLongClickLister,onClickListener)

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

    // load items
    private fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }

    // save items
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),listOfTasks)
        }catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }
}