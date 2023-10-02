package com.dshovhenia.compose.playgroundapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dshovhenia.compose.playgroundapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
