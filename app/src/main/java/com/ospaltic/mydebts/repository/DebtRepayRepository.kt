package com.ospaltic.mydebts.repository

import android.util.Log
import com.ospaltic.mydebts.data.local.DebtRepayDao
import com.ospaltic.mydebts.model.DebtEntity
import com.ospaltic.mydebts.model.RepayEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DebtRepayRepository(private val debtRepayDao: DebtRepayDao) {

    //    val allDebt: Flow<List<DebtEntity>> = debtDao.getAllDebt(userId)
    fun getDebtRepayById(debtId: String): Flow<List<RepayEntity>> = debtRepayDao.getDebtRepayById(debtId)

//    getAllDebtHist
     fun getAllDebtHist(userId: String): Flow<List<RepayEntity>> = debtRepayDao.getAllDebtHist(userId)

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




    fun getTotalPaid(debtId: String): Flow<Float> {
        return debtRepayDao.getAllTotalPaid(debtId)
            .map { total -> total ?: 0.0f }  // Convert NULL to 0.0
    }
//suspend fun getTotalPaid(debtId: String): Float {
//    val totalPaid = debtRepayDao.getTotalPaid(debtId)
//    Log.d("DebtPayRepository", "Total paid for $debtId: $totalPaid")
//    return totalPaid
//}


//    suspend fun getTotalPaid(debtId: String): Float {
//        val payments = debtRepayDao.getAllPayments(debtId) // Fetch all records
//        val totalPaid = payments.sumOf { it.amountPaid.toDouble() }.toFloat() // Convert and sum
//        Log.d("DebtPayRepository", "Total Paid for $debtId: $totalPaid (From ${payments.size} records)")
//        return totalPaid
//    }

}