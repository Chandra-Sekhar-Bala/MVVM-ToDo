package com.sekhar.todo.app.showTodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sekhar.todo.app.repo.db.DAO
import com.sekhar.todo.app.repo.db.TodoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoViewModel(private val database: DAO) : ViewModel() {
    private val _todoData = MutableLiveData<List<TodoModel>>()
    val todoData: LiveData<List<TodoModel>>
        get() = _todoData


    val data = viewModelScope.async {
        getAllTodo()
    }
    fun fetchTodos() {
        viewModelScope.launch {
            val todos = getAllTodo()
            _todoData.value = todos.value
        }
    }

    private suspend fun getAllTodo(): LiveData<List<TodoModel>> {
        return withContext(viewModelScope.coroutineContext) {
            getTodoFromDB()
        }
    }


    // get All todos from DO
    private fun getTodoFromDB(): LiveData<List<TodoModel>> {
        return database.getAll()
    }

    // for public access
    fun deleteItem(id: Int){
        viewModelScope.launch {
            deleteTodo(id)
        }
    }

    private suspend fun deleteTodo(id:Int){
        withContext(Dispatchers.IO){
            try {
                database.delete(id)
            }catch (e : Exception){
                throw e.suppressedExceptions[0]
            }
        }

    }

}