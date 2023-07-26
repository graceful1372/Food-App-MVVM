package com.graceful1372.foodappnouri.ui

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.graceful1372.foodappnouri.R
import com.graceful1372.foodappnouri.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private  var _binding: ActivityMainBinding? = null
    private val binding get()= _binding!!

    //Other
    private lateinit var navHost:NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //NavController
        navHost =supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment

        //Bottom nav
        binding.bottomNav.setupWithNavController(navHost.navController)
        //Hide bottom nav
        navHost.navController.addOnDestinationChangedListener{_,destination , _ ->

            if (destination.id == R. id . foodDetailFragment){
                binding.bottomNav.visibility = View.GONE
            }else{
                binding.bottomNav.visibility = View.VISIBLE

            }
        }



    }

    override fun onNavigateUp(): Boolean {

      return navHost.navController.navigateUp() || super.onNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }






}