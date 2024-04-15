package com.example.todoapplication

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
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
                alertBox("Input task is empty")
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
    private fun alertBox(text: String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(text)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, id ->
                Log.d("OK", "pressed")
                finish()
            }
        val alert = builder.create()
        alert.show()
    }
}