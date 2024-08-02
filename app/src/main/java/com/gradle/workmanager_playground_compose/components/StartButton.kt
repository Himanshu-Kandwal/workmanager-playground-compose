package com.gradle.workmanager_playground_compose.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StartButton(modifier: Modifier = Modifier.fillMaxWidth(0.9f)) {
    val ctx = LocalContext.current
    Button(modifier = modifier,
        onClick = {
            Toast.makeText(ctx, "Button clicked!", Toast.LENGTH_SHORT).show()
        },
    ) {
        Text(text = "Start Work")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartButtonPreview() {
    StartButton()
}