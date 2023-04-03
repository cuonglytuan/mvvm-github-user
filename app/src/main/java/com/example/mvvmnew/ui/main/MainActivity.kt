package com.example.mvvmnew.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmnew.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, UserListFragment.newInstance())
                    .commitNow()
        }
    }
}