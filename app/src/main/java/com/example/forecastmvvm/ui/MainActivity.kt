package com.example.forecastmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.forecastmvvm.R
import kotlinx.android.synthetic.main.activity_main.*

//https://github.com/ResoCoder/forecast-mvvm-android-kotlin
//https://www.youtube.com/watch?v=yDaaM3u389I
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)
+        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        /*
        If anyone is having trouble with onSupportNavigateUp in MainActivity,
        this is how you write the return statement
        without using the deprecated NavigationUI.navigateUp method:

        // The navigateUp method takes parameters differently now
        // return NavigationUI.navigateUp(NavController, drawerLayout)
            return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), null)
         */
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), null)
    }
}