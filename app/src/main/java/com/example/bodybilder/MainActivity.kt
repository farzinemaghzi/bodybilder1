package com.example.bodybilder

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.bodybilder.exercises.view.BodyPartFragment
import com.example.bodybilder.exercises.view.TestWorkoutFragment
import com.example.bodybilder.exercises.view.ExerciseDetailFragment
import com.example.bodybilder.exercises.view.ExercisesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        setTitle(null)


        bottomNav = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_exercise -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BodyPartFragment())
                        .commit()
                    true
                }
                R.id.navigation_plan -> {
                    supportFragmentManager.beginTransaction()
                    true
                }
                R.id.navigation_log -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, TestWorkoutFragment())
                        .commit()
                    true
                }
                R.id.navigation_more -> {
                    // Handle More navigation
                    true
                }
                else -> false
            }
        }



        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BodyPartFragment())
                .commit()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            updateBottomNavigationVisibility()
        }

        val constraintLayout = findViewById<ConstraintLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(constraintLayout) { view, insets ->
            view.setPadding(0, 0, 0, 0)
            insets
        }
        window.navigationBarColor = ContextCompat.getColor(this, R.color.primary)









    }

    private fun updateBottomNavigationVisibility(){
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is ExerciseDetailFragment) {
            bottomNavigation?.visibility = ImageView.GONE
        } else {
            bottomNavigation?.visibility = ImageView.VISIBLE
        }

    }

}