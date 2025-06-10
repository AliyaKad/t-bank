package com.example.t_bank

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.t_bank.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        applySavedTheme()
        setAppLocale(getSavedLanguage())

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)
        setupBottomNavigationVisibility(navController)
    }

    private fun setAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun getSavedLanguage(): String {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getString("app_language", "ru")!!
    }

    private fun applySavedTheme() {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("is_dark_theme", false)
        setTheme(if (isDarkTheme) R.style.Theme_Tbank_Dark else R.style.Theme_Tbank)
    }

    private fun setupBottomNavigationVisibility(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.expensesFragment,
                R.id.profileFragment,
                R.id.financialGoalsFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }
}


