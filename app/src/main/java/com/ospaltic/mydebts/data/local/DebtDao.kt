package com.ospaltic.mydebts.data.local

import androidx.compose.runtime.remember
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ospaltic.mydebts.model.DebtEntity
import com.ospaltic.mydebts.model.PeopleEntity
import com.ospaltic.mydebts.utils.formatDate
import kotlinx.coroutines.flow.Flow

//This interface defines the database operations.
@Dao
interface DebtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDebt(debt: DebtEntity)

    @Update
    suspend fun updateDebt(debt: DebtEntity)

//    @Delete
//    suspend fun deletePerson(person: PeopleEntity)
     @Query("UPDATE debt SET status = :status WHERE debtId = :debtId")
     suspend fun updateDebtStatus(debtId: String, status: String): Int?

    @Query("UPDATE debt SET amountRem = :amountRem, amountPaid= :amountPaid WHERE debtId = :debtId")
    suspend fun updateDebtValues(debtId: String, amountRem: Float, amountPaid: Float): Int?

//    val amountRem: Float,
//    val amountPaid: Float,

    @Query("SELECT * FROM debt WHERE uid = :userId ORDER BY timestamp DESC")
    fun getAllDebt(userId: String): Flow<List<DebtEntity>>


    @Query("SELECT * FROM debt WHERE debtId = :debtId LIMIT 1")
    suspend fun getDebtById(debtId: String): DebtEntity?

//    @Query("SELECT * FROM debt WHERE debtId = :debtId LIMIT 1")
//    suspend fun getDebtAmountRemById(debtId: String): DebtEntity?



    @Query("SELECT SUM(amountRem) FROM debt WHERE uid = :userId AND (status = 'Pending' OR status = 'Partial')")
    fun getAllUnpaidTotal(userId: String): Flow<Float?>


//    @Query("SELECT debtId FROM debt WHERE uid = :userId AND (status = 'Pending' OR status = 'Partial')")
//     fun getDebtIdByUid(userId: String): Flow<List<DebtEntity>>


    @Query("SELECT SUM(amountRem) FROM debt WHERE status = 'Pending' OR status = 'Partial'")
    fun getAllUnpaidTotalAllPeople(): Flow<Float?>


    @Query("SELECT debtId FROM debt WHERE uid = :userId AND (status = 'Pending' OR status = 'Partial')")
    fun getDebtIdByUid(userId: String): Flow<List<String>>



    @Query("SELECT COUNT(*) FROM debt WHERE status = 'Pending'")
    fun getAllTotalPendingDebt(): Flow<Int?>

    @Query("SELECT COUNT(*) FROM debt WHERE status = 'Partial'")
    fun getAllTotalPartialDebt(): Flow<Int?>


    @Query("SELECT COUNT(*) FROM debt WHERE status = 'Paid'")
    fun getAllTotalPaidDebt(): Flow<Int?>



    @Query("SELECT COUNT(*) FROM debt WHERE dueDate < :formattedDate AND (status = 'Pending' OR status = 'Partial')")
    fun getAllTotalPastDueDebt(formattedDate: String): Flow<Int?>

//
//    @Query("SELECT SUM(amount) FROM debt WHERE uid = :userId AND status = 'pending'")
//    fun getAllUnpaidTotal(userId: String): Flow<Float?>

}