package com.bernardooechsler.firebasecourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.bernardooechsler.firebasecourse.presentation.navigation.Navigation
import com.bernardooechsler.firebasecourse.presentation.navigation.rootnavgraph.RootNavigationGraph
import com.bernardooechsler.firebasecourse.ui.theme.FirebaseCourseTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

