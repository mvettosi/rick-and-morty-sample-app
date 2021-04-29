package com.urban.androidhomework.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.urban.androidhomework.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main activity of the app, that serves as a container for the Single Activity App structure.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}