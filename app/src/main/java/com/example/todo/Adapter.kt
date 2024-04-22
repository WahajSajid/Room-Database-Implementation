package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.collections.List

class Adapter(private val itemsData:List<ToDoItems>):RecyclerView.Adapter<Adapter.ViewHOlder>() {
    private lateinit var  clickListener: OnItemClickListener

    interface OnItemClickListener{
        fun itemClickListener(position: Int,textView:TextView)
        val mutex: Mutex
    }
    fun itemClickListener(listener: OnItemClickListener){
        clickListener = listener
    }

    class ViewHOlder(itemView:View,clickListener: OnItemClickListener):RecyclerView.ViewHolder(itemView) {
        val itemText = itemView.findViewById<TextView>(R.id.itemValue)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.deleteButton)
        init {
            deleteButton.setOnClickListener {
                deleteButton.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    clickListener.mutex.withLock { clickListener.itemClickListener(adapterPosition,itemText) }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHOlder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_items,parent,false)
        return ViewHOlder(layout,clickListener)
    }


    override fun onBindViewHolder(holder: Adapter.ViewHOlder, position: Int) {
        holder.itemText.text = itemsData[position].item
    }


    override fun getItemCount(): Int = itemsData.size
}