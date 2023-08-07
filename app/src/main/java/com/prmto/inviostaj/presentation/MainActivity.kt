package com.prmto.inviostaj.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.prmto.inviostaj.R
import com.prmto.inviostaj.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fcv_activity_main) as NavHostFragment

        val navController = navHost.navController

        val appBarConfigure = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.exploreFragment,
                R.id.favoriteFragment
            )
        )

        binding.tbarActivityMain.setupWithNavController(navController, appBarConfigure)

        binding.bnvActivityMain?.setupWithNavController(navController)

        binding.nrvActivityMain?.setupWithNavController(navController)
    }
}