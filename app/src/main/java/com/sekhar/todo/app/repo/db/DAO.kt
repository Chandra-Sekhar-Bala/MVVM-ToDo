package com.sekhar.todo.app.repo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DAO {
    @Insert
    fun insert(todo: TodoModel)

    @Update
    fun update(todo: TodoModel)

    @Query("SELECT * FROM todo_table")
    fun getAll() : LiveData<List<TodoModel>>

    @Query("DELETE FROM TODO_TABLE WHERE TodoID = :todoId")
    fun delete(todoId : Int)

}