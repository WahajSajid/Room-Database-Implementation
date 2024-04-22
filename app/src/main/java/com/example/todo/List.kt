package com.example.todo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.FragmentListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import kotlin.collections.List

class List : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var toDoItems: ToDoItems
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        val dao: Dao = DataBase.getDatabase(requireContext().applicationContext).dao()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        CoroutineScope(Dispatchers.IO).launch {
            val getData: LiveData<List<ToDoItems>> = dao.getAll()
            withContext(Dispatchers.Main) {
                getData.observe(viewLifecycleOwner, Observer { students ->
                    if (students != null && students.isNotEmpty()) {
                        binding.textView.visibility = View.GONE
                        val adapter = Adapter(students)
                        recyclerView.adapter = adapter
                        adapter.itemClickListener(@SuppressLint("SuspiciousIndentation")
                        object : Adapter.OnItemClickListener {
                            override val mutex: Mutex = Mutex()
                            override fun itemClickListener(position: Int, textView: TextView) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    dao.delete(textView.text.toString())
//                                    rearrangeDisplayOrder()
//                                    val dataFromDataBase = dao.getAll()
//                                    val updatedId = mutableListOf<ToDoItems>()
//                                    for (i in 0 until updatedId.size) {
//                                        updatedId[i].id = i + 1
//                                    }
//                                    dao.updateDisplayOrder(updatedId)
                                }
                            }
                        })

                    } else {
                        binding.textView.visibility = View.VISIBLE
                    }
                })
            }
        }
        return binding.root
    }

    private suspend fun rearrangeDisplayOrder() {
        val dao: Dao = DataBase.getDatabase(requireContext().applicationContext).dao()
        val items = dao.getAll().value ?: return
        items.forEachIndexed { _, item ->
            item.displayOrder += 1
        }
        dao.updateDisplayOrder(items)
    }
}