package com.gmail.remarkable.development.airrybnik

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.gmail.remarkable.development.airrybnik.data.workers.FetchDataWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class AirRybnikApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupPeriodicDataUpdate()
        }
    }

    private fun setupPeriodicDataUpdate() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val calendar = Calendar.getInstance()
        val delayMinutes = (60 - (calendar.get(Calendar.MINUTE)) + 20).toLong()
        val repeatingRequest = PeriodicWorkRequestBuilder<FetchDataWorker>(
            1, TimeUnit.HOURS
        )
            //.setInitialDelay(delayMinutes, TimeUnit.MINUTES)
            .addTag(FetchDataWorker.WORK_NAME)
            .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            FetchDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}