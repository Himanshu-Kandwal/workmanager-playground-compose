package com.gradle.workmanager_playground_compose.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StartWorkButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(0.9f),
        onClick = onClick,
    ) {
        Text(text = "Start Work")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartWorkButtonPreview() {
    val ctx = LocalContext.current
    StartWorkButton({ Toast.makeText(ctx, "Clicked", Toast.LENGTH_SHORT).show() })
}