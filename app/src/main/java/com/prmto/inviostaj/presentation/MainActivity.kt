package com.prmto.inviostaj.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.prmto.inviostaj.R
import com.prmto.inviostaj.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHost.navController

        val appBarConfigure = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.exploreFragment,
                R.id.favoriteFragment
            )
        )

        binding.toolbar.setupWithNavController(navController, appBarConfigure)

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}