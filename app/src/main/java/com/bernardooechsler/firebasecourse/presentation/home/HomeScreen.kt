package com.bernardooechsler.firebasecourse.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onVideoClick: () -> Unit,
    onGraphClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(onClick = { onGraphClick() }) {
            Text(text = "Graph")
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Button(onClick = { onVideoClick() }) {
            Text(text = "Videos")
        }
    }
}