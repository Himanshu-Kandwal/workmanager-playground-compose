package com.gradle.workmanager_playground_compose.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.gradle.workmanager_playground_compose.backgroundworker.BackgroundWorker
import com.gradle.workmanager_playground_compose.components.StartWorkButton

@Composable
fun HomeScreen() {
    val ctx = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "This is output text", fontSize = 40.sp)
        Spacer(modifier = Modifier.height(200.dp))
        StartWorkButton {
            startWork(ctx)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreenPreview() {
    HomeScreen()
}

fun startWork(ctx: Context) {
    val workManager = WorkManager.getInstance(ctx)
    Toast.makeText(ctx, "Work Started", Toast.LENGTH_SHORT).show()
    val workRequest = OneTimeWorkRequest.Builder(BackgroundWorker::class.java).build()
    workManager.enqueue(workRequest)
    //observing is work is done or not
    workManager.getWorkInfoByIdLiveData(workRequest.id).observeForever {
        if (it.state.isFinished) {
            Toast.makeText(ctx, "Work Finished", Toast.LENGTH_SHORT).show()
        }
    }
}