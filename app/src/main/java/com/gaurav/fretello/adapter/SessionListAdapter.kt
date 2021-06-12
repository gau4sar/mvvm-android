package com.gaurav.fretello.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.fretello.data.model.Session
import com.gaurav.fretello.databinding.ItemSessionLayoutBinding
import com.gaurav.fretello.ui.sessions.ExerciseItem
import com.gaurav.fretello.ui.sessions.SessionsViewModel
import com.gaurav.fretello.utils.getFormattedDateTime
import com.xwray.groupie.GroupieAdapter
import timber.log.Timber

/**
 * RecyclerView Adapter to handle data in SessionListFragment
 */

class SessionListAdapter(val context: FragmentActivity, private val viewModel: SessionsViewModel) :
    RecyclerView.Adapter<SessionListAdapter.SessionViewHolder>() {

    inner class SessionViewHolder(val binding: ItemSessionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * DiffUtil implementation for better rendering of RecyclerView
     */
    private val differCallback = object : DiffUtil.ItemCallback<Session>() {
        override fun areItemsTheSame(
            oldItem: Session,
            newItem: Session
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Session,
            newItem: Session
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    /**
     * RecyclerView Adapter callback methods
     */
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {

        val binding = ItemSessionLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return SessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {

        val session = differ.currentList[position]

        holder.binding.apply {
            tvSessionName.text = "${session.name}"
            tvPracticedOn.text = "Last Practiced : ${getFormattedDateTime(session.practicedOnDate)}"

            // Exercise list implementation using Groupie
            val exerciseListAdapter = GroupieAdapter()

            rvExercises.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = exerciseListAdapter
            }

            viewModel.getExercises(session.id).observe(context, {
                it.forEach { exercise ->
                    exerciseListAdapter.add(ExerciseItem(exercise))
                }
            })

            tvShowExercises.setOnClickListener {
                tvShowExercises.text = if (session.isExpanded) "Hide details" else "View details"
                llExercises.visibility = if (session.isExpanded) View.VISIBLE else View.GONE
                session.isExpanded = !session.isExpanded
            }
        }
    }

    /**
     * Click listener handler for list items
     */

    private var onItemClickListener: ((Session) -> Unit)? = null

    fun setOnItemClickListener(listener: (Session) -> Unit) {
        onItemClickListener = listener
    }
}