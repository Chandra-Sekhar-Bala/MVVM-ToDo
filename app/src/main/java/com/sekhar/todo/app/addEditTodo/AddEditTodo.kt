package com.sekhar.todo.app.addEditTodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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
    private lateinit var args: AddEditTodoArgs

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
        // set up as per navArgs
        args = AddEditTodoArgs.fromBundle(requireArguments())
        var title = ""

        if(args.todoModel == null) {
            title = "Add new Todo"
        }else{
            // set up data
            title = "Update your Todo"
            fillUpData(args.todoModel!!.subject!!)
        }
        (activity as AppCompatActivity).supportActionBar!!.title = title

        val application = requireNotNull(this.activity).application
        dao = DB.getInstance(application).todoDAO

    }

    private fun fillUpData(text: String) {
        // fill up to-do
        bind.editTextTodo.setText(text)

        // change tag for click listener
        bind.saveTodo.tag = getString(R.string.update)
        bind.saveTodo.text = getString(R.string.update)

    }

    override fun onResume() {
        super.onResume()

        bind.saveTodo.setOnClickListener{
            // validate data:
            if(validate()){
                // if clicked for save
                val textData = bind.editTextTodo.text.toString().trim()

                if(bind.saveTodo.tag == getString(R.string.save)) {
                    saveData(textData)
                }else{
                    // clicked for update
                    updateData(textData)
                }
            }
        }

    }

    private fun updateData(title : String) {
        // if to-do wasn't updated then no need to update
        if(args.todoModel?.subject == title)
            onBackPressed()

        // update title and move on
        args.todoModel?.subject = title
        GlobalScope.launch {
            dao.update(args.todoModel!!)
            
            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(), "Todo Updated 🤝🏽", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }

    private fun onBackPressed(){
        view?.findNavController()!!.navigate(AddEditTodoDirections.actionAddTodoToShowTodo())
    }


    private fun saveData(item : String){
        val data = TodoModel(subject = item)

        GlobalScope.launch(Dispatchers.IO){

            dao.insert(data)

            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(), "Todo Saved 🤭", Toast.LENGTH_SHORT).show()
                onBackPressed()
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