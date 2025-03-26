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


    @Query("SELECT * FROM debt_history WHERE debtId = :debtId ORDER BY timestamp DESC")
    fun getDebtRepayById(debtId: String): Flow<List<RepayEntity>>


    @Query("SELECT * FROM debt_history WHERE uid = :userId ORDER BY timestamp DESC")
    fun getAllDebtHist(userId: String): Flow<List<RepayEntity>>



@Query("SELECT SUM(amountPaid) FROM debt_history WHERE debtId = :debtId")
fun getAllTotalPaid(debtId: String): Flow<Float?>

//    @Query("SELECT COALESCE(SUM(amountPaid), 0) FROM debt_history WHERE debtId = :debtId")
//    suspend fun getTotalPaid(debtId: String): Float


//    @Query("SELECT * FROM debt_history WHERE uid = :debtId")
//    suspend fun getAllPayments(debtId: String): List<RepayEntity>
}