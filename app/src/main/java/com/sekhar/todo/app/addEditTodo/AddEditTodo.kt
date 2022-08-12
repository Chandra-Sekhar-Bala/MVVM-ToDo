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
import com.sekhar.todo.app.repo.db.DAO
import com.sekhar.todo.app.repo.db.DB
import com.sekhar.todo.app.repo.db.TodoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditTodo : Fragment() {
    private lateinit var bind : FragmentAddTodoBinding
    private lateinit var dao : DAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = DataBindingUtil.inflate( inflater, R.layout.fragment_add_todo, container, false)

        initialize()

        return bind.root
    }

    private fun initialize() {

        // Setting Title
        (activity as AppCompatActivity).supportActionBar!!.title = "Add new Todo"

        val application = requireNotNull(this.activity).application
        dao = DB.getInstance(application).todoDAO

    }

    override fun onResume() {
        super.onResume()

        bind.saveTodo.setOnClickListener{
            if(validate()){

                saveData(bind.editTextTodo.text.toString().trim())
            }
        }

    }

    private fun saveData(item : String){
        val data = TodoModel(subject = item)

        GlobalScope.launch(Dispatchers.IO){

            dao.insert(data)

            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(), "Todo Saved", Toast.LENGTH_SHORT).show()
                bind.editTextTodo.text?.clear()
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