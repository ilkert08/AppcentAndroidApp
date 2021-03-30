package com.ilkerdev.appcentproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ilkerdev.appcentproject.fragments.FavoriteFragment
import com.ilkerdev.appcentproject.fragments.HomeFragment

//Uygulama acildiginda gorulen, 2 fragment ve bottom navigation bari iceren man activity.


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val favoriteFragment = FavoriteFragment()
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        changeFragment(homeFragment)

        bottomNavigation.setOnNavigationItemSelectedListener {
            if(it.itemId == R.id.home){
                changeFragment(homeFragment)
            }else if(it.itemId == R.id.liked){
                changeFragment(favoriteFragment)
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment){  //Fragment degismesi durumunda calisan fonksiyon.
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.current_fragment, fragment)
            commit()
        }
    }
}