package com.example.todo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlin.collections.List


@Dao
interface Dao {
    @Query("SELECT * FROM ToDoItems")
    fun getAll(): LiveData<List<ToDoItems>>

    @Insert
    fun insert(vararg toDoItems: ToDoItems)
}