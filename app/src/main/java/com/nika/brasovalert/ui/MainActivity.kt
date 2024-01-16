package com.nika.brasovalert.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nika.brasovalert.R
import com.nika.brasovalert.databinding.ActivityMainBinding
import com.nika.brasovalert.databinding.FragmentHomeBinding
import com.nika.brasovalert.mvvm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var bottomNavBar : BottomNavigationView
    lateinit var navController: NavController
    private val vm by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavBar= findViewById(R.id.btm_menu)
        navController =  findNavController(R.id.fragmentContainerView2)
        bottomNavBar.setupWithNavController(navController)



        setVisibility()
        observeIsLogged()
    }





    private fun observeIsLogged() {
        vm.observeIsLogged().observe(this, Observer {
            Log.d("Main", "Is logged: $it")
            if (it != null && it == false) {
                Log.d("HomeFragment", "Navigating to logginFragment3")
                navController.navigate(R.id.action_homeFragment_to_logginFragment3)
            } else if (it != null && it == true) {
                Log.d("Main", "Navigating to homeFragment")
                navController.navigate(R.id.action_logginFragment3_to_homeFragment)
            }
        })
    }

    private fun setVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.logginFragment3 || destination.id == R.id.registerFragment2) {
                bottomNavBar.visibility = View.GONE
            } else {
                bottomNavBar.visibility = View.VISIBLE
            }

        }

    }

}