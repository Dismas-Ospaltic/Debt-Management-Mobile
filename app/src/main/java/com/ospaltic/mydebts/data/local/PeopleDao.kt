package com.ospaltic.mydebts.data.local


import androidx.room.*
import com.ospaltic.mydebts.model.PeopleEntity
import kotlinx.coroutines.flow.Flow

//This interface defines the database operations.
@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PeopleEntity)

    @Update
    suspend fun updatePerson(person: PeopleEntity)

    @Delete
    suspend fun deletePerson(person: PeopleEntity)

    @Query("SELECT * FROM people ORDER BY timestamp DESC")
    fun getAllPeople(): Flow<List<PeopleEntity>>

    @Query("SELECT * FROM people WHERE uid = :userId LIMIT 1")
    suspend fun getPersonById(userId: String): PeopleEntity?

    @Query("SELECT COUNT(*) FROM people")
    fun getAllTotalPeople(): Flow<Int?>

}

