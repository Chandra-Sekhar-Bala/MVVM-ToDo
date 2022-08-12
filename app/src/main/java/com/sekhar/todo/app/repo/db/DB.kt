package com.sekhar.todo.app.repo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [TodoModel::class], version = 1, exportSchema = false)
abstract class DB : RoomDatabase() {

    abstract val todoDAO : DAO

    companion object{
        @Volatile
        private var INSTANCE : DB? = null
        fun getInstance(context: Context): DB {

            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext, DB::class.java,
                        "Todo_table")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }

    }


}