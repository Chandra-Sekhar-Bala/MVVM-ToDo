package com.sekhar.todo.app.repo.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo_table")
data class TodoModel(
    @PrimaryKey(autoGenerate = true)
    var TodoID : Int = 0,
    var subject : String? = null
)
