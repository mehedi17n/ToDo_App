package com.example.sample.adapters

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.sample.R

class TaskAdapter(
    private val tasks: MutableList<String>,
    private val onTaskRemove: (String) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTextView: TextView = itemView.findViewById(R.id.taskTextView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        val deleteAnimationView: LottieAnimationView = itemView.findViewById(R.id.deleteAnimationView)

        fun bind(task: String) {
            taskTextView.text = task

            deleteButton.setOnClickListener {
                // Hide delete button and show animation
                deleteButton.visibility = View.GONE
                deleteAnimationView.visibility = View.VISIBLE
                deleteAnimationView.playAnimation()

                // Remove task after animation ends
                deleteAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        // Trigger the task removal after animation ends
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            tasks.removeAt(position)
                            notifyItemRemoved(position)
                        }
                    }
                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size
}
