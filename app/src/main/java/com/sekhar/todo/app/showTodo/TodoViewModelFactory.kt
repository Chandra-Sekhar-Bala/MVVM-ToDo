package com.sekhar.todo.app.showTodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sekhar.todo.app.repo.db.DAO


class TodoViewModelFactory (private val dataSource: DAO): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}