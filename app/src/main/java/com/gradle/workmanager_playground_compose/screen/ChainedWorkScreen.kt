package com.gradle.workmanager_playground_compose.screen

import android.content.Context
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
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.gradle.workmanager_playground_compose.backgroundworker.BackgroundWorker
import com.gradle.workmanager_playground_compose.backgroundworker.FinalWorker
import com.gradle.workmanager_playground_compose.backgroundworker.ParallelWorkerOne
import com.gradle.workmanager_playground_compose.backgroundworker.ParallelWorkerTwo
import com.gradle.workmanager_playground_compose.components.StartWorkButton

@Composable
fun ChainedWorkScreen() {
    val ctx = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "This is chained output text", fontSize = 25.sp)
        Spacer(modifier = Modifier.height(200.dp))

        Button(modifier = Modifier.fillMaxWidth(0.9f), onClick = { startChainedWork(ctx) }) {
            Text(text = "Start Chained Work", fontSize = 15.sp)
        }
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            modifier = Modifier.fillMaxWidth(0.9f),
            onClick = { startChainedParallelWork(ctx) }) {
            Text(text = "Start Chained Work(Parallel)", fontSize = 15.sp)
        }
        Spacer(modifier = Modifier.height(200.dp))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ChainedWorkScreenPreview() {
    ChainedWorkScreen()
}

fun startChainedWork(ctx: Context) {
    val workManager = WorkManager.getInstance(ctx)

    val parallelFirstRequest = OneTimeWorkRequest.Builder(ParallelWorkerOne::class.java).build()
    val parallelSecondRequest = OneTimeWorkRequest.Builder(ParallelWorkerTwo::class.java).build()
    val finalWorkerRequest = OneTimeWorkRequest.Builder(FinalWorker::class.java).build()

    val workContinuation = workManager.beginWith(parallelFirstRequest).then(parallelSecondRequest)
        .then(finalWorkerRequest)

    workContinuation.enqueue()

    //observing is work is done or not
    workManager.getWorkInfoByIdLiveData(finalWorkerRequest.id)
        .observeForever { //we are observing final worker request so we can find if last worker executed then all executed

            if (it.state == WorkInfo.State.RUNNING) {
                Toast.makeText(ctx, "Chained Work Started", Toast.LENGTH_SHORT).show()
            }
            if (it.state.isFinished) {
                Toast.makeText(
                    ctx, "Chained Work Finished", Toast.LENGTH_SHORT
                ).show()
            }
        }
}

fun startChainedParallelWork(ctx: Context) {
    val workManager = WorkManager.getInstance(ctx)

    val parallelFirstRequest = OneTimeWorkRequest.Builder(ParallelWorkerOne::class.java).build()
    val parallelSecondRequest = OneTimeWorkRequest.Builder(ParallelWorkerTwo::class.java).build()
    val finalWorkerRequest = OneTimeWorkRequest.Builder(FinalWorker::class.java).build()

    //work requests are put into a list
    val parallelWorkerRequestList: MutableList<OneTimeWorkRequest> =
        mutableListOf(parallelFirstRequest, parallelSecondRequest)

    val workContinuation =
        workManager.beginWith(parallelWorkerRequestList) //and the list is given to work manager
            .then(finalWorkerRequest)

    workContinuation.enqueue()

    //observing is work is done or not
    workManager.getWorkInfoByIdLiveData(finalWorkerRequest.id)
        .observeForever { //we are observing final worker request so we can find if last worker executed then all executed

            if (it.state == WorkInfo.State.RUNNING) {
                Toast.makeText(ctx, "Chained Work Started", Toast.LENGTH_SHORT).show()
            }
            if (it.state.isFinished) {
                Toast.makeText(
                    ctx, "Chained Work Finished", Toast.LENGTH_SHORT
                ).show()
            }
        }
}