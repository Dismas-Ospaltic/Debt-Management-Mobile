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

    @Query("SELECT * FROM debt WHERE uid = :userId ORDER BY timestamp DESC")
    fun getAllDebt(userId: String): Flow<List<DebtEntity>>


    @Query("SELECT * FROM debt WHERE debtId = :debtId LIMIT 1")
    suspend fun getDebtById(debtId: String): DebtEntity?
}