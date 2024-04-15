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

    private lateinit var taskText: String
    private lateinit var taskPriority: String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener{

            taskText = findViewById<EditText>(R.id.task_data).text.toString()
            taskPriority = findViewById<Spinner>(R.id.spinner).selectedItem.toString()
            if(taskText.replace(" ", "").isEmpty()){
                Toast.makeText(this, "Input task is empty", Toast.LENGTH_LONG).show()
                finish()
            }
            else{
                addDataToDB()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addDataToDB(){
        val db = DBHandler(this)
        val todo = TodoData(taskPriority, taskText)
        db.addTask(todo)
        Toast.makeText(this, " $taskText  Added with Priority: $taskPriority", Toast.LENGTH_LONG).show()
        finish()
    }

}