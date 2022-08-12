package com.sekhar.todo.app.showTodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sekhar.todo.app.R
import com.sekhar.todo.app.databinding.FragmentShowTodoBinding

class ShowTodo : Fragment() {
    private lateinit var bind : FragmentShowTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_show_todo, container, false)

        // no menu option for now
        setHasOptionsMenu(true)

        return bind.root
    }

    override fun onResume() {
        super.onResume()

        // add new To-Do
        bind.addTodo.setOnClickListener{
            requireView().findNavController().navigate(R.id.action_showTodo_to_addTodo)
        }
    }
}