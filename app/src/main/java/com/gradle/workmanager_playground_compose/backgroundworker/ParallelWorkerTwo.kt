package com.gradle.workmanager_playground_compose.backgroundworker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class ParallelWorkerTwo(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {

    override fun doWork(): Result {
        try {

            for (i in 1..50) {
                if (isStopped) { //check if work manager got cancelled due to constraints,
                    return Result.failure() //cancel it manually as it does not cancel automatically
                }
                Log.d("WorkerTAG", "ParallelWorkerTwo: doWork: $i")
                Thread.sleep(1000)
            }

            return Result.success()
        } catch (e: Exception) {
            Log.e("WorkerTAG", "ParallelWorkerTwo: Error executing background task", e)
            return Result.failure()
        }
    }

}