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



    @Delete
    suspend fun deleteItem(item:ToDoItems)

    @Query("DELETE FROM ToDoItems WHERE item = :Item")
    suspend fun delete(Item:String)


    @Update
    suspend fun updateDisplayOrder(items: List<ToDoItems>)

}