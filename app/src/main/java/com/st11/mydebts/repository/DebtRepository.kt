package com.st11.mydebts.repository

import com.st11.mydebts.data.local.DebtDao
import com.st11.mydebts.model.DebtEntity
import com.st11.mydebts.utils.formatDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DebtRepository(private val debtDao: DebtDao) {

//    val allDebt: Flow<List<DebtEntity>> = debtDao.getAllDebt(userId)
  fun getAllDebt(userId: String): Flow<List<DebtEntity>> = debtDao.getAllDebt(userId)

    fun getAllRecentDebt(): Flow<List<DebtEntity>> = debtDao.getAllRecentDebt()



    //    val allDebt: Flow<List<DebtEntity>> = debtDao.getAllDebt(userId)
//    fun getAllDebtId(userId: String): Flow<List<DebtEntity>> = debtDao.getDebtIdByUid(userId)
    fun getAllDebtId(userId: String): Flow<List<String>> = debtDao.getDebtIdByUid(userId)


    suspend fun insert(debt: DebtEntity) {
        debtDao.insertDebt(debt)
    }

    suspend fun update(debt: DebtEntity) {
        debtDao.updateDebt(debt)
    }



    suspend fun updateDebtStatus(debtId: String, newStatus: String): Boolean {
        val rowsUpdated = debtDao.updateDebtStatus(debtId, newStatus) ?: 0
        return rowsUpdated > 0
    }

    suspend fun  updateDebtValues(debtId: String, newAmountRem: Float, newAmountPaid: Float): Boolean {
        val rowsUpdated = debtDao. updateDebtValues(debtId, newAmountRem, newAmountPaid) ?: 0
        return rowsUpdated > 0
    }




    suspend fun getDebtById(debtId: String): DebtEntity? {
        return debtDao.getDebtById(debtId)
    }


//    suspend fun getDebtAmountRemById(debtId: String): DebtEntity? {
//        return debtDao.getDebtAmountRemById(debtId)
//    }

        fun getTotalUnPaid(userId: String): Flow<Float> {
        return debtDao.getAllUnpaidTotal(userId)
            .map { total -> total ?: 0.0f }  // Convert NULL to 0.0
    }

    fun getAllUnpaidTotalAllPeople(): Flow<Float> {
        return debtDao.getAllUnpaidTotalAllPeople()
            .map { total -> total ?: 0.0f }  // Convert NULL to 0.0
    }



    fun getAllTotalPartialDebt(): Flow<Int> {
        return debtDao.getAllTotalPartialDebt()
            .map { total -> total ?: 0 }  // Convert NULL to 0.0
    }


    fun getAllTotalPendingDebt(): Flow<Int> {
        return debtDao.getAllTotalPendingDebt()
            .map { total -> total ?: 0 }  // Convert NULL to 0.0
    }


    fun getAllTotalPaidDebt(): Flow<Int> {
        return debtDao.getAllTotalPaidDebt()
            .map { total -> total ?: 0 }  // Convert NULL to 0.0
    }

    fun getAllTotalPastDueDebt(): Flow<Int> {
        val currentDate = System.currentTimeMillis()
        val formattedDate = formatDate(currentDate) // Should return "DD-MM-YYYY"
        return debtDao.getAllTotalPastDueDebt(formattedDate)
            .map { total -> total ?: 0 }  // Convert NULL to 0.0
    }








}

