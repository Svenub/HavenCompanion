package se.umu.svke0008.havencompanion.presentation.util_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    indicatorSize: Int = 100,
    text: String = ""
) {


    Surface(modifier = modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(indicatorSize.dp))
            Text(text)
        }
    }
}

@Preview
@Composable
fun LoadingPrev() {
    HavenCompanionTheme {
        LoadingScreen()
    }
}