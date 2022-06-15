package com.mypath.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val etItemText:TextView = findViewById(R.id.etItemText)

        etItemText.text = intent.getStringExtra("ITEM_TEXT")
        val itemPosition:Int = intent.getIntExtra("ITEM_POSITION",0)

        supportActionBar?.title = "Edit Item"

        findViewById<Button>(R.id.btSave).setOnClickListener {
            val data = Intent()

            data.putExtra("ITEM_TEXT", etItemText.text.toString())
            data.putExtra("ITEM_POSITION", itemPosition)
            setResult(RESULT_OK, data)
            finish()
        }
    }
}