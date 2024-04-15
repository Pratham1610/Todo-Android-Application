package com.example.todoapplication
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val taskData = itemView.findViewById<TextView>(R.id.text_todo)
    val taskPriority = itemView.findViewById<TextView>(R.id.text_priority)
    val card = itemView.findViewById<CardView>(R.id.card_view)
}
