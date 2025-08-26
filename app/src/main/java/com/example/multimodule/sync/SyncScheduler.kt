package com.example.multimodule.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncScheduler @Inject constructor(
   @ApplicationContext private val context: Context
) {

    fun enqueueImmediate(){
        val req= OneTimeWorkRequestBuilder<NoteSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "NoteSyncOneTime",
            ExistingWorkPolicy.KEEP,
            req
        )
    }


    fun schedulePeriodic(){
        val req= PeriodicWorkRequestBuilder<NoteSyncWorker>(6, TimeUnit.HOURS).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "NoteSyncPeriodic",
            ExistingPeriodicWorkPolicy.KEEP,
            req
        )
    }


}