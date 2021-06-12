package com.gaurav.fretello.ui.sessions.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.fretello.R
import com.gaurav.fretello.adapter.SessionListAdapter
import com.gaurav.fretello.databinding.FragmentSessionsBinding
import com.gaurav.fretello.ui.sessions.SessionsViewModel
import com.gaurav.fretello.data.remote.ApiResponseHandler
import com.gaurav.fretello.utils.MathUtil.getMaxPercentageIncreaseInteger
import com.gaurav.fretello.utils.handleApiError
import com.gaurav.fretello.utils.showLongToast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * [Fragment] to display the list of sessions .
 */
class SessionsListFragment : Fragment(R.layout.fragment_sessions) {

    private lateinit var binding: FragmentSessionsBinding
    private lateinit var navController: NavController

    private val viewModel by sharedViewModel<SessionsViewModel>()

    private lateinit var sessionListAdapter: SessionListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSessionsBinding.bind(view)
        navController = findNavController()

        setupRecyclerView()

        viewModel.sessionList.observe(viewLifecycleOwner, { response ->
            when (response) {

                is ApiResponseHandler.Success -> {
                    binding.progressCircular.hide()

                    response.data?.let { sessionListResponse ->
                        sessionListAdapter.differ.submitList(sessionListResponse)

                        sessionListResponse.forEach {
                            viewModel.saveExercises(it.exerciseList)
                        }

                        activity?.showLongToast(
                            "Maximum percentage increase : " +
                                    "${getMaxPercentageIncreaseInteger(sessionListResponse)}"
                        )
                    }
                }

                is ApiResponseHandler.Error -> {
                    binding.progressCircular.hide()
                    handleApiError(response, requireActivity())
                }

                is ApiResponseHandler.Loading -> {
                    binding.progressCircular.show()
                }
            }
        })
    }

    private fun setupRecyclerView() {

        sessionListAdapter = SessionListAdapter(requireActivity(), viewModel)

        binding.rvSessionList.apply {
            adapter = sessionListAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        // Navigate and send the session information to SessionDetailsFragment
        /*sessionListAdapter.setOnItemClickListener { session ->
            setFragmentResult(
                Constants.REQUEST_KEY_SESSION_DETAILS,
                bundleOf(Constants.BUNDLE_SESSION to session)
            )
            navController.navigate(R.id.action_sessionsListFragment_to_sessionDetailsFragment)
        }*/
    }
}