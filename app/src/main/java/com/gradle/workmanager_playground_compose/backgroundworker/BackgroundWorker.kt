package com.gradle.workmanager_playground_compose.backgroundworker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gradle.workmanager_playground_compose.util.Constants
import kotlinx.coroutines.delay

class BackgroundWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    override fun doWork(): Result {
        val loopCount = inputData.getInt(Constants.LOOP_COUNT_KEY, 0)
        try {
            for (i in 1..loopCount) {
                if (isStopped) { //check if work manager got cancelled due to constraints,
                    return Result.failure() //cancel it manually as it does not cancel automatically
                }
                Log.d("WorkerTAG", "doWork: $i")
                Thread.sleep(1000)
            }
            return Result.success()
        } catch (e: Exception) {
            Log.e("BackgroundWorker", "Error executing background task", e)
            return Result.failure()
        }
    }

}