package com.gaurav.fretello.ui.sessions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.gaurav.fretello.R
import com.gaurav.fretello.databinding.ActivitySessionsBinding
import com.gaurav.fretello.utils.checkNetworkConnection
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SessionsActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivitySessionsBinding

    private lateinit var sessionsViewModel: SessionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        checkNetworkConnection(this)
        super.onCreate(savedInstanceState)

        binding = ActivitySessionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_container)

        sessionsViewModel = getViewModel()
    }
}