package com.bernardooechsler.firebasecourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.bernardooechsler.firebasecourse.presentation.navigation.rootnavgraph.RootNavigationGraph
import com.bernardooechsler.firebasecourse.ui.theme.FirebaseCourseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseCourseTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}

