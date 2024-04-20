package com.example.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ToDoItems::class], version = 1)
abstract class DataBase:RoomDatabase() {
    abstract fun dao(): Dao
    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "To_Do_Items"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}