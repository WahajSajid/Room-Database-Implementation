package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.List

class Adapter(private val itemsData:List<ToDoItems>):RecyclerView.Adapter<Adapter.ViewHOlder>() {
    class ViewHOlder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val itemText = itemView.findViewById<TextView>(R.id.itemValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHOlder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_items,parent,false)
        return ViewHOlder(layout)
    }


    override fun onBindViewHolder(holder: Adapter.ViewHOlder, position: Int) {
        holder.itemText.text = itemsData[position].item
    }


    override fun getItemCount(): Int = itemsData.size
}