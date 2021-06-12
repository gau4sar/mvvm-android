package com.gaurav.fretello.ui.sessions.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.fretello.R
import com.gaurav.fretello.data.model.Session
import com.gaurav.fretello.databinding.FragmentSessionDetailsBinding
import com.gaurav.fretello.ui.sessions.ExerciseItem
import com.gaurav.fretello.utils.Constants
import com.gaurav.fretello.utils.getFormattedDateTime
import com.xwray.groupie.GroupieAdapter
import timber.log.Timber

/**
 * [Fragment] to display the all the information of each Session.
 */

class SessionDetailsFragment : Fragment(R.layout.fragment_session_details) {

    private lateinit var binding: FragmentSessionDetailsBinding
    private lateinit var navController: NavController

    private lateinit var exerciseListAdapter: GroupieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSessionDetailsBinding.bind(view)
        navController = findNavController()

        setFragmentResultListener(Constants.REQUEST_KEY_SESSION_DETAILS) { key, bundle ->
            val session = bundle.getSerializable(Constants.BUNDLE_SESSION) as Session
            Timber.d("Session details : $session")
            bind(session)
        }
    }

    private fun bind(session: Session) {

        binding.tvSessionName.text = session.name
        binding.tvPracticedOnDate.text = "Practiced on : ${getFormattedDateTime(session.practicedOnDate)}"

        // RecyclerView implementation using Groupie
        exerciseListAdapter = GroupieAdapter()

        binding.rvExercises.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter =  exerciseListAdapter
        }

        session.exercises.forEach {
            exerciseListAdapter.add(ExerciseItem(it))
        }
    }
}