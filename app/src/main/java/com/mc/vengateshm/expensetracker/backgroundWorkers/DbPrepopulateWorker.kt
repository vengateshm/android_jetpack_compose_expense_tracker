package com.mc.vengateshm.expensetracker.backgroundWorkers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mc.vengateshm.expensetracker.data.local.room.dao.ExpenseCategoryDao
import com.mc.vengateshm.expensetracker.data.local.room.dao.PaymentTypeDao
import com.mc.vengateshm.expensetracker.data.local.room.prepopulateData.defaultExpenseCategoryDtoTypeList
import com.mc.vengateshm.expensetracker.data.local.room.prepopulateData.defaultPaymentDtoTypeList
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

@HiltWorker
class DbPrepopulateWorker @AssistedInject constructor(
    private val expenseCategoryDao: ExpenseCategoryDao,
    private val paymentTypeDao: PaymentTypeDao,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val tag = com.mc.vengateshm.expensetracker.backgroundWorkers.DbPrepopulateWorker::class.java.simpleName

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            Log.d(tag, "Thread ${Thread.currentThread().name}")
            val time = measureTimeMillis {
                val expenseCategoryDeferred = async {
                    expenseCategoryDao.insertAll(
                        defaultExpenseCategoryDtoTypeList
                    )
                }
                val paymentTypeDeferred = async {
                    paymentTypeDao.insertAll(
                        defaultPaymentDtoTypeList
                    )
                }
                listOf(expenseCategoryDeferred, paymentTypeDeferred).awaitAll()
            }
            Log.d(tag, "Time to prepopulate db ${time / 1000}sec")
            Result.success()
        }
    }
}