package com.ycspl.bingaa

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class UpdatePersonWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    private val personDatabase = Room.databaseBuilder(context, PersonDatabase::class.java, "personDb").build()
    override suspend fun doWork(): Result {
        val personID = inputData.getLong("personId", -1)
        val person = personDatabase.getDao().getPersonById(personID)
        personDatabase.getDao().update("Doctor", personID)
        return Result.success()
    }
}