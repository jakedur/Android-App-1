package com.example.lifecyclemanagement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.lifecyclemanagement.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityStartBinding

//    private val userModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // for some reason, after updating android studio, this broke
//        val navController = findNavController(R.id.nav_host_fragment_content_start)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_start) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        val userObserver = Observer<User> { newUser ->
//            println("new user: $newUser") // does trigger from debug
//        }
//        userModel.currentUser.observe(this, userObserver)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_start)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}