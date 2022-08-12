package com.sekhar.todo.app.showTodo

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sekhar.todo.app.R
import com.sekhar.todo.app.databinding.FragmentShowTodoBinding
import com.sekhar.todo.app.repo.db.DB
import com.sekhar.todo.app.showTodo.adapter.TodoAdapter


class ShowTodo : Fragment() , TodoAdapter.OnDoneClickedListener {

    private lateinit var bind : FragmentShowTodoBinding
    private lateinit var viewModel: TodoViewModel
    private lateinit var adapter : TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_show_todo, container, false)

        // initialize
        initialize()

        return bind.root
    }

    private fun initialize() {
        // no options for now
        setHasOptionsMenu(false)
        // getting ready to pass viewModel
        val application = requireNotNull(this.activity).application
        val datasource = DB.getInstance(application).todoDAO

        val viewModelFactory = TodoViewModelFactory(datasource)
        viewModel = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]

        // setting recyclerview with adapter
        adapter = TodoAdapter(this)
        bind.recyclerView.adapter = adapter

    }


    override fun onResume() {
        super.onResume()

        // add new To-Do
        bind.addTodo.setOnClickListener{
            requireView().findNavController().navigate(R.id.action_showTodo_to_addTodo)
        }

        // observing All Todos from DB
        viewModel.getAllTodo().observe(this) {
            adapter.submitList(it)
        }
    }

    // when Done button is clicked : Delete From DB
    override fun onDoneClicked(id: Int) {
        Toast.makeText(requireContext(), "Clicked on $id", Toast.LENGTH_SHORT).show()
//        viewModel.deleteItem(id)
        deleteTodo(id)
    }

    private fun deleteTodo(id: Int){

        AlertDialog.Builder(context)
            .setTitle("Todo done?")
            .setMessage("Delete this as you've completed it bro?")
            .setPositiveButton(android.R.string.yes
            ) { _, _ ->
                // Yes clicked  to delete bro
             viewModel.deleteItem(id)

            }.setNegativeButton(android.R.string.no, null)
            .setIcon(R.drawable.delete_icon)
            .show()
    }
}