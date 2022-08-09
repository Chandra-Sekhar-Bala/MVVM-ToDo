package com.sekhar.todo.app.showTodo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sekhar.todo.app.R
import com.sekhar.todo.app.repo.db.TodoModel
class TodoAdapter(private var listener: OnDoneClickedListener) : ListAdapter<TodoModel, TodoAdapter.TodoViewHolder>(MyCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_todo,parent,false)
        return TodoViewHolder(view)
    }

    // setting data
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val data = getItem(position)
        holder.titleText.text = data.subject
        // callback to UI
        holder.done.setOnClickListener{
            listener.onDoneClicked(data.TodoID)
        }
    }

    class TodoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val titleText = itemView.findViewById(R.id.todo_title) as TextView
        val done = itemView.findViewById(R.id.todo_done) as ImageView
    }

    interface OnDoneClickedListener{
        fun onDoneClicked(id: Int)
    }

    // callback for Data changing
    object MyCallBack : DiffUtil.ItemCallback<TodoModel>() {
        override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
            return oldItem.TodoID == newItem.TodoID
        }

        override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
            return oldItem == newItem
        }

    }

}

