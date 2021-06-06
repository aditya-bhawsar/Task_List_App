package com.aditya.task_list.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.aditya.task_list.R
import com.aditya.task_list.databinding.ActivityAppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {

    //Data Binding for Screen
    private var _binding: ActivityAppBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Delay for Splash Screen
        Thread.sleep(700)

        //Theme Change to remove Splash
        setTheme(R.style.Task_List)

        //Setting Content View Of ACTIVITY
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_app)

        //Linking nav controller with action bar
        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        //Implementing navigate up on action bar with nav controller
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        //Clearing reference for the binding object
        _binding = null
    }
}