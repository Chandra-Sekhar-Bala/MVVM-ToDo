package com.sekhar.todo.app.addEditTodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sekhar.todo.app.R
import com.sekhar.todo.app.databinding.FragmentAddTodoBinding

class AddEditTodo : Fragment() {
    private lateinit var bind : FragmentAddTodoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = DataBindingUtil.inflate( inflater, R.layout.fragment_add_todo, container, false)

        // Setting Title
        (activity as AppCompatActivity).supportActionBar!!.title = "Add new Todo"
        return bind.root
    }

    override fun onResume() {
        super.onResume()

        bind.saveTodo.setOnClickListener{
            if(validate()){
                // TODO : save/ update data to room
                Toast.makeText(requireContext(), "Saved to DB", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validate(): Boolean {
        var flag = true

        if(bind.editTextTodo.text.toString().trim().isEmpty()){
            bind.editTextTodo.requestFocus()
            bind.editTextTodo.error = "Cannot be empty"
            flag = false
        }

        return flag
    }

}