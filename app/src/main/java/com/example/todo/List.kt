package com.example.todo

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
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
                            override fun itemClickListener(position: Int, view: View) {
//                                Toast.makeText(context,"$position",Toast.LENGTH_SHORT).show()
                                CoroutineScope(Dispatchers.IO).launch {
                                   dao.deleteItemById(position+1)
//                                    withContext(Dispatchers.Main) {
//                                        dao.getAll().observe(viewLifecycleOwner) { items ->
//                                            CoroutineScope(Dispatchers.IO).launch {
//                                                var currentId = 1
//                                                for (item in items) {
//                                                    if (item.id > position) {
//                                                        item.id = currentId
//                                                        dao.updateId(item)
//                                                        currentId++
//                                                    } else {
//                                                        currentId = item.id
//                                                    }
//                                                }
//                                            }
//
//                                        }
//                                    }
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
}