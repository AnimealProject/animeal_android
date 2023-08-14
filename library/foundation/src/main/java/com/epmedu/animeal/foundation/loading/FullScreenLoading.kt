package com.epmedu.animeal.foundation.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.util.generateLoremIpsum

@Composable
fun FullScreenLoading() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@AnimealPreview
@Composable
private fun FullScreenLoadingPreview() {
    AnimealTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = generateLoremIpsum(130..131))
            AnimealButton(text = "Click", onClick = {})
        }
        FullScreenLoading()
    }
}