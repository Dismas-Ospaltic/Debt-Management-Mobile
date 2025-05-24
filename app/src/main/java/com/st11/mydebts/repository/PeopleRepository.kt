package com.st11.mydebts.repository

import com.st11.mydebts.data.local.PeopleDao
import com.st11.mydebts.model.PeopleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PeopleRepository(private val peopleDao: PeopleDao) {

    val allPeople: Flow<List<PeopleEntity>> = peopleDao.getAllPeople()

    suspend fun insert(person: PeopleEntity) {
        peopleDao.insertPerson(person)
    }

    suspend fun update(person: PeopleEntity) {
        peopleDao.updatePerson(person)
    }

    suspend fun delete(person: PeopleEntity) {
        peopleDao. deletePerson(person)
    }

    suspend fun getPersonById(userId: String): PeopleEntity? {
        return peopleDao.getPersonById(userId)
    }


    fun getAllTotalPeople(): Flow<Int> {
        return peopleDao.getAllTotalPeople()
            .map { total -> total ?: 0 }  // Convert NULL to 0.0
    }


    suspend fun  editPersonDetail(userId: String, firstName: String, lastName: String, email: String, phone: String, businessName: String, address: String): Boolean {
        val rowsUpdated =  peopleDao.editPersonDetail(userId, firstName, lastName, email, phone, businessName, address) ?: 0
        return rowsUpdated > 0
    }


}

