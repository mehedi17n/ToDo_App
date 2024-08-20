package com.example.sample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.adapters.TaskAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var taskEditText: EditText
    private lateinit var taskRecyclerView: RecyclerView
    private val taskList = mutableListOf<String>()
    private lateinit var taskAdapter: TaskAdapter

    private lateinit var addTaskConfirmButton: Button
    private lateinit var addTaskButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskEditText = findViewById(R.id.taskEditText)
        addTaskButton = findViewById(R.id.addTaskButton)
        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        addTaskConfirmButton = findViewById(R.id.addTaskConfirmButton)

        // Show the EditText and Add Task button when the FAB is clicked, then hide the FAB
        addTaskButton.setOnClickListener {
            addTaskButton.visibility = View.GONE
            taskEditText.visibility = View.VISIBLE
            addTaskConfirmButton.visibility = View.VISIBLE
        }

        // Set up the RecyclerView and Adapter
        taskAdapter = TaskAdapter(taskList) { task -> removeTask(task) }
        taskRecyclerView.adapter = taskAdapter
        taskRecyclerView.layoutManager = LinearLayoutManager(this)

        // Handle Add Task button click
        addTaskConfirmButton.setOnClickListener {
            val task = taskEditText.text.toString()
            if (task.isNotBlank()) {
                taskList.add(task)
                taskAdapter.notifyItemInserted(taskList.size - 1)
                taskEditText.text.clear()
                addTaskButton.visibility = View.GONE
                taskEditText.visibility = View.VISIBLE
                addTaskConfirmButton.visibility = View.VISIBLE
            }
        }
    }

    private fun removeTask(task: String) {
        val position = taskList.indexOf(task)
        if (position >= 0) {
            taskList.removeAt(position)
            taskAdapter.notifyItemRemoved(position)
        }
    }
}
