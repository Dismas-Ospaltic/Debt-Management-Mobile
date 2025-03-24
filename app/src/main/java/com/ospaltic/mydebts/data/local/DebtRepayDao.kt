package com.ospaltic.mydebts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ospaltic.mydebts.model.DebtEntity
import com.ospaltic.mydebts.model.RepayEntity
import kotlinx.coroutines.flow.Flow

//This interface defines the database operations.
@Dao
interface DebtRepayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepay(repay: RepayEntity)

    @Update
    suspend fun updateRepayDebt(repay: RepayEntity)

//    @Query("UPDATE debt SET status = :status WHERE debtId = :debtId")
//    suspend fun updateDebtStatus(debtId: String, status: String): Int?

    @Query("SELECT * FROM debt_history WHERE debtId = :debtId ORDER BY timestamp DESC")
    fun getDebtRepayById(debtId: String): Flow<List<RepayEntity>>


    @Query("SELECT * FROM debt_history WHERE uid = :userId ORDER BY timestamp DESC")
    suspend fun getAllDebtHist(userId: String): Flow<List<RepayEntity>>
}