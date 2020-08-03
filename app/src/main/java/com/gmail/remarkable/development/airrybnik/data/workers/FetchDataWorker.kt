package com.gmail.remarkable.development.airrybnik.data.workers

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gmail.remarkable.development.airrybnik.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FetchDataWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val repository: Repository
) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "Fetch from network"
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                repository.refreshData()
                Result.success()
            } catch (e: HttpException) {
                Result.retry()
            }
        }

    }
}