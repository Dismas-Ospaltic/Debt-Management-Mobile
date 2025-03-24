package com.ospaltic.mydebts.repository

import com.ospaltic.mydebts.data.local.DebtDao
import com.ospaltic.mydebts.data.local.DebtRepayDao
import com.ospaltic.mydebts.model.DebtEntity
import com.ospaltic.mydebts.model.RepayEntity
import kotlinx.coroutines.flow.Flow


class DebtRepayRepository(private val debtRepayDao: DebtRepayDao) {

    //    val allDebt: Flow<List<DebtEntity>> = debtDao.getAllDebt(userId)
    fun getDebtRepayById(debtId: String): Flow<List<RepayEntity>> = debtRepayDao.getDebtRepayById(debtId)

//    getAllDebtHist

    suspend fun insert(debt: RepayEntity) {
        debtRepayDao.insertRepay(debt)
    }

    suspend fun update(debt: RepayEntity) {
        debtRepayDao.updateRepayDebt(debt)
    }

//
//    suspend fun getDebtById(debtId: String): DebtEntity? {
//        return debtRepayDao.getDebtById(debtId)
//    }

}