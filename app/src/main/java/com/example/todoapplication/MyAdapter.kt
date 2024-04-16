package com.example.todoapplication
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.data.DBHandler

class MyAdapter(private var context: Context, private var todoList: MutableList<TodoData>) : RecyclerView.Adapter<TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentData = todoList[position]
        holder.taskData.setText(currentData.data)
        holder.taskPriority.setText(currentData.priority)
        if("High Priority" == currentData.priority){
            holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
        }
        else if("Medium Priority" == currentData.priority){
            holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
        }
        else{
            holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }

        holder.card.setOnClickListener{
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.input)
            val textShow = dialog.findViewById<TextView>(R.id.textView6)
            textShow.setText("Edit Task")
            val btnSubmit: Button = dialog.findViewById(R.id.btnSubmit)
            val taskDataEditText: EditText = dialog.findViewById(R.id.task_data)
            val spinner: Spinner = dialog.findViewById(R.id.spinner)

            btnSubmit.setOnClickListener {
                val newTaskData = taskDataEditText.text.toString()
                val newTaskPriority = spinner.selectedItem.toString()
                if (newTaskData.trim().isEmpty()) {
                    alertBox("Input task is empty")
                } else {
                    holder.taskData.setText(newTaskData)
                    holder.taskPriority.setText(newTaskPriority)
                    if("High Priority" == newTaskPriority){
                        holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
                    }
                    else if("Medium Priority" == newTaskPriority){
                        holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
                    }
                    else{
                        holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
                    }
                    editTask(currentData.data, currentData.priority, newTaskData, newTaskPriority)
                }
                dialog.dismiss()
            }
            dialog.show()
        }

        holder.card.setOnLongClickListener{
            alertBox(currentData.data, currentData.priority, position)
            true
        }
    }
    override fun getItemCount(): Int {
        return todoList.size
    }
    fun updateData(newTodoList: MutableList<TodoData>) {
        todoList = newTodoList
    }
    private fun alertBox(text: String){
        val builder = AlertDialog.Builder(context)
        builder.setMessage(text)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, id ->
                Log.d("OK", "pressed")
            }
        val alert = builder.create()
        alert.show()
    }
    private fun alertBox(task: String, taskPriority: String, position: Int){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Warning!!")
        builder.setMessage("This action cannot be undone. Are you sure you want to delete this task ?")

        builder.setPositiveButton("YES") { dialog, which ->
            Toast.makeText(context,
                "YES", Toast.LENGTH_SHORT).show()
            todoList.removeAt(position)
            notifyItemRemoved(position)
            deleteRow(task, taskPriority)
        }

        builder.setNegativeButton("NO") { dialog, which ->
            Toast.makeText(context,
                "NO", Toast.LENGTH_SHORT).show()

        }

        builder.setNeutralButton("Cancel") { dialog, which ->
            Toast.makeText(context,
                "Cancel", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }
    private fun deleteRow(task: String, taskPriority: String){
        val db = DBHandler(context)
        db.deleteTask(task, taskPriority)
    }

    private fun editTask(
        taskName: String,
        taskPriority: String,
        newTaskName: String,
        newTaskPriority: String
    ){
        val db = DBHandler(context)
        db.editTask(taskName, taskPriority, newTaskName, newTaskPriority)
    }
}
