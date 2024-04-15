package com.example.todoapplication

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapplication.data.DBHandler

class AddTaskActivity : AppCompatActivity(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener{
            val taskText = findViewById<EditText>(R.id.task_data).text.toString()
            val taskPriority = findViewById<Spinner>(R.id.spinner).selectedItem.toString()
            val db = DBHandler(this)
            val todo = TodoData(taskPriority, taskText)
            db.addTask(todo)
            Toast.makeText(this, " $taskText  Added with Priority: $taskPriority", Toast.LENGTH_LONG).show()
            finish()
        }
    }

}