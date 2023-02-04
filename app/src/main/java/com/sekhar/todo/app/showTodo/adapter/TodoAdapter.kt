package com.sekhar.todo.app.showTodo.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sekhar.todo.app.R
import com.sekhar.todo.app.repo.db.TodoModel
class TodoAdapter(private var listener: OnDoneClickedListener) : ListAdapter<TodoModel, TodoAdapter.TodoViewHolder>(MyCallBack){
private lateinit var colors : IntArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
         colors =parent.context.resources.getIntArray(R.array.colorArray)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_todo,parent,false)
        return TodoViewHolder(view)
    }

    // setting data
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val data = getItem(position)
        holder.titleText.text = data.subject
        // color changing
        holder.itemView.rootView.backgroundTintList = ColorStateList.valueOf(colors[position% colors.size])
        // To-do done clicked : callback to UI
        holder.done.setOnClickListener{
            listener.onDoneClicked(data.TodoID)
        }
        // Item clicked to update : callback to UI
        holder.itemView.setOnClickListener{
            listener.onItemClicked(data)
        }
    }

    class TodoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val titleText = itemView.findViewById(R.id.todo_title) as TextView
        val done = itemView.findViewById(R.id.todo_done) as ImageView
        val root = itemView.findViewById(R.id.rootView) as ConstraintLayout

    }

    interface OnDoneClickedListener{
        fun onDoneClicked(id: Int)
        fun onItemClicked(data : TodoModel)
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

