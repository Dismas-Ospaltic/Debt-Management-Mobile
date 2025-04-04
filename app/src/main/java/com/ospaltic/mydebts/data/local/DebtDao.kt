package com.ospaltic.mydebts.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ospaltic.mydebts.model.DebtEntity
import com.ospaltic.mydebts.model.PeopleEntity
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



    @Query("SELECT SUM(amountRem) FROM debt WHERE uid = :userId AND (status = 'Pending' OR status = 'Partial')")
    fun getAllUnpaidTotal(userId: String): Flow<Float?>
//
//    @Query("SELECT SUM(amount) FROM debt WHERE uid = :userId AND status = 'pending'")
//    fun getAllUnpaidTotal(userId: String): Flow<Float?>

}