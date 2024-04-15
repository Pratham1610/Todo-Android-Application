package com.example.todoapplication
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.data.DBHandler
import androidx.appcompat.app.AlertDialog

class TodoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private val db = DBHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo_main)
        val todoList = db.getAllTask()
        recyclerView = findViewById(R.id.recycler_view)
        adapter = MyAdapter(todoList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val addButton: Button = findViewById(R.id.btnAddItem)
        addButton.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
            updateRecyclerView()
        }
        val deleteBtn: Button = findViewById(R.id.btnDeleteItem)
        deleteBtn.setOnClickListener{
            alertBox()
        }
    }

    override fun onResume() {
        super.onResume()
        updateRecyclerView()
    }
    private fun updateRecyclerView() {
        val newTodoList = db.getAllTask()
        adapter.updateData(newTodoList)
        adapter.notifyDataSetChanged()
    }
    private fun alertBox(){
        var check: Boolean = false
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Warning!!")
        builder.setMessage("This action cannot be undone. Are you sure ?")

        builder.setPositiveButton("YES") { dialog, which ->
            Toast.makeText(applicationContext,
                "YES", Toast.LENGTH_SHORT).show()
            deleteAllTask()
        }

        builder.setNegativeButton("NO") { dialog, which ->
            Toast.makeText(applicationContext,
                "NO", Toast.LENGTH_SHORT).show()

        }

        builder.setNeutralButton("Cancel") { dialog, which ->
            Toast.makeText(applicationContext,
                "Cancel", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    private fun deleteAllTask(){
        Log.d("Initial Size of the Table: ", db.getSize().toString())
        db.deleteData()
        updateRecyclerView()
        Log.d("Size of the Table: ", db.getSize().toString())
        Toast.makeText(this, "All task delete successfully", Toast.LENGTH_LONG).show()
    }
}
