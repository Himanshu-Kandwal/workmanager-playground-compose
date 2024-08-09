package com.gradle.workmanager_playground_compose.backgroundworker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gradle.workmanager_playground_compose.util.Constants

class PeriodicWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {

    override fun doWork(): Result {
        try {

            for (i in 1..10) {
                if (isStopped) { //check if work manager got cancelled due to constraints,
                    return Result.failure() //cancel it manually as it does not cancel automatically
                }
                Log.d("WorkerTAG", "Periodic Worker doWork: $i")
            }

            return Result.success()
        } catch (e: Exception) {
            Log.e("WorkerTAG", "PeriodicWorker: Error executing background task", e)
            return Result.failure()
        }
    }

}