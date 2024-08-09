package com.gradle.workmanager_playground_compose.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.gradle.workmanager_playground_compose.backgroundworker.PeriodicWorker
import java.util.concurrent.TimeUnit

@Composable
fun PeriodicWorkScreen() {
    val ctx = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "This is Periodic work", fontSize = 25.sp)
        Spacer(modifier = Modifier.height(200.dp))

        Button(modifier = Modifier.fillMaxWidth(0.9f), onClick = { startPeriodicWork(ctx) }) {
            Text(text = "Start Periodic Work", fontSize = 15.sp)
        }
        Spacer(modifier = Modifier.height(50.dp))

        Button(modifier = Modifier.fillMaxWidth(0.9f), onClick = { stopPeriodicWork(ctx) }) {
            Text(text = "Stop Periodic Work", fontSize = 15.sp)
        }
    }
}

fun startPeriodicWork(ctx: Context) {
    val workManager = WorkManager.getInstance(ctx)
    //minimum interval is 15 minutes , if less than this is mentioned work manager automatically takes it to 15 minutes
    val periodicWorkRequest =
        PeriodicWorkRequestBuilder<PeriodicWorker>(
            15,
            TimeUnit.MINUTES
        ).addTag("PERIODIC_WORK") //adding tag so we can cancel it using tag
            .build()
    workManager.enqueue(periodicWorkRequest)

    //adding listeners to work manager

    workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id).observeForever {

        if (it.state == WorkInfo.State.RUNNING) {
            Toast.makeText(ctx, "Work Running", Toast.LENGTH_SHORT).show()
            Log.d("WorkerTAG", "Periodic Worker Running")
        }

        if (it.state == WorkInfo.State.FAILED) {
            Toast.makeText(ctx, "Work Failed", Toast.LENGTH_SHORT).show()
            Log.d("WorkerTAG", "Periodic Worker Failed")
        }

        if (it.state == WorkInfo.State.CANCELLED) {
            Toast.makeText(ctx, "Work Cancelled", Toast.LENGTH_SHORT).show()
            Log.d("WorkerTAG", "Periodic Worker Cancelled")
        }
    }
}

fun stopPeriodicWork(ctx: Context) {
    val workManager = WorkManager.getInstance(ctx)
    workManager.cancelAllWorkByTag("PERIODIC_WORK")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PeriodicWorkScreenPreview() {
    PeriodicWorkScreen()
}