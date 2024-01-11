package com.nika.brasovalert.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nika.brasovalert.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var bottomNavBar : BottomNavigationView
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavBar= findViewById(R.id.btm_menu)
        navController =  findNavController(R.id.fragmentContainerView2)
        bottomNavBar.setupWithNavController(navController)


        setVisility()
    }

    private fun setVisility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.logginFragment3 || destination.id == R.id.registerFragment2) {
                bottomNavBar.visibility = View.GONE
            } else {
                bottomNavBar.visibility = View.VISIBLE
            }

        }

    }

}