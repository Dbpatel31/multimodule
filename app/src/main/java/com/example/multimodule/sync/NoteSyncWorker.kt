package com.example.multimodule.sync

import android.content.Context
import android.content.ContextParams
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.multimodule.domain.usecase.SyncNoteUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NoteSyncWorker @AssistedInject constructor(
  @Assisted appContext: Context,
  @Assisted params: WorkerParameters,
  private val sync: SyncNoteUseCase
): CoroutineWorker(appContext,params) {
    override suspend fun doWork(): Result = try {
        sync()
        Result.success()
    }
    catch (t:Throwable){
        Result.retry()
    }

}