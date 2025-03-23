package com.ospaltic.mydebts.repository

import com.ospaltic.mydebts.data.local.PeopleDao
import com.ospaltic.mydebts.model.PeopleEntity
import kotlinx.coroutines.flow.Flow


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

}

