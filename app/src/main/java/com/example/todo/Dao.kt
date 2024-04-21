package com.example.todo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlin.collections.List


@Dao
interface Dao {
    @Query("SELECT * FROM ToDoItems")
    fun getAll(): LiveData<List<ToDoItems>>

    @Insert
    fun insert(vararg toDoItems: ToDoItems)
    @Query("DELETE FROM ToDoItems WHERE id = :Id")
    fun deleteItemById(Id:Int)

    @Update
    suspend fun updateId(id: ToDoItems)

    @Delete
    suspend fun deleteItem(item:ToDoItems)

}