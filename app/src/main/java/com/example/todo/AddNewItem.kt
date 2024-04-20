package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.todo.databinding.FragmentAddNewItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.List

class AddNewItem : Fragment() {
    private lateinit var binding: FragmentAddNewItemBinding
    private lateinit var db:DataBase
//    private lateinit var dao:Dao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_add_new_item, container, false)
        val dao: Dao = DataBase.getDatabase(requireContext().applicationContext).dao()
//        db =  Room.databaseBuilder(
//            requireContext().applicationContext,
//            DataBase::class.java,
//            "To Do Items"
//        ).build()

        binding.addItemButton.setOnClickListener {
            val  items = binding.enterItemEditText.text.toString()
            val data = ToDoItems(item = items)
            CoroutineScope(Dispatchers.IO).launch {
                dao.insert(data)
            }
            CoroutineScope(Dispatchers.IO).launch {
                val getData : LiveData<List<ToDoItems>> =  dao.getAll()
                withContext(Dispatchers.Main) {
                    getData.observe(viewLifecycleOwner, Observer { students ->
                        if (students != null && students.isNotEmpty()) {
                            binding.textView.text = students[1].item
                        }
                    })
                }
            }


        }

        return binding.root
    }
}