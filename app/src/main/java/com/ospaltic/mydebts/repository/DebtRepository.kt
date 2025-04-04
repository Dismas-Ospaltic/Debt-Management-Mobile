package com.ospaltic.mydebts.repository

import com.ospaltic.mydebts.data.local.DebtDao
import com.ospaltic.mydebts.model.DebtEntity
import com.ospaltic.mydebts.model.PeopleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DebtRepository(private val debtDao: DebtDao) {

//    val allDebt: Flow<List<DebtEntity>> = debtDao.getAllDebt(userId)
    fun getAllDebt(userId: String): Flow<List<DebtEntity>> = debtDao.getAllDebt(userId)

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

        fun getTotalUnPaid(userId: String): Flow<Float> {
        return debtDao.getAllUnpaidTotal(userId)
            .map { total -> total ?: 0.0f }  // Convert NULL to 0.0
    }

}

