package com.example.todoapplication
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private var todoList: List<TodoData>) : RecyclerView.Adapter<TodoViewHolder>() {

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
    }
    override fun getItemCount(): Int {
        return todoList.size
    }
    fun updateData(newTodoList: List<TodoData>) {
        todoList = newTodoList
    }
}
