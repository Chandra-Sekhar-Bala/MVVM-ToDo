package com.sekhar.todo.app.showTodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sekhar.todo.app.repo.db.DAO
import com.sekhar.todo.app.repo.db.TodoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class TodoViewModel(private val database: DAO) : ViewModel() {


    fun getAllTodo() : LiveData<List<TodoModel>> {

        return runBlocking {
            return@runBlocking getTodoFromDB()
        }

    }

    // get All todos from DO
    private suspend fun getTodoFromDB(): LiveData<List<TodoModel>> {

        return withContext(Dispatchers.IO){
            val todo = database.getAll()
            todo
        }

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