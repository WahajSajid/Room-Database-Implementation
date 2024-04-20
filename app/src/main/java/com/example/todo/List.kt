package com.example.todo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.FragmentListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.List

class List : Fragment() {
    private lateinit var binding:FragmentListBinding
    private lateinit var recyclerView:RecyclerView
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_list, container, false)
        val dao: Dao = DataBase.getDatabase(requireContext().applicationContext).dao()
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
//        recyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(context)
//                    CoroutineScope(Dispatchers.IO).launch {
//                val getData =  sharedViewModel.getAllToDoItems()
//                withContext(Dispatchers.Main) {
//                    getData.observe(viewLifecycleOwner, Observer { items ->
//                        if (items != null && items.isNotEmpty()) {
//                            recyclerView.adapter = Adapter(items)
//                        }
//                    })
//                }
//            }

        return binding.root
    }
}