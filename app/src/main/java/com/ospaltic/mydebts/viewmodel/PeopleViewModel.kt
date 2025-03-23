package com.ospaltic.mydebts.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ospaltic.mydebts.model.PeopleEntity
import com.ospaltic.mydebts.repository.PeopleRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PeopleViewModel(private val repository: PeopleRepository) : ViewModel() {

    private val _people = MutableStateFlow<List<PeopleEntity>>(emptyList())
    val people: StateFlow<List<PeopleEntity>> = _people

    init {
        getAllPeople()
    }

    private fun getAllPeople() {
        viewModelScope.launch {
            repository.allPeople.collect { peopleList ->
                _people.value = peopleList
            }
        }
    }

    fun insert(person: PeopleEntity) {
        viewModelScope.launch {
            repository.insert(person)
        }
    }

    fun update(person: PeopleEntity) {
        viewModelScope.launch {
            repository.update(person)
        }
    }

    fun delete(person: PeopleEntity) {
        viewModelScope.launch {
            repository.delete(person)
        }
    }


    private val _personState = MutableStateFlow<PeopleEntity?>(null)
    val personState: StateFlow<PeopleEntity?> = _personState

    fun getPersonDetails(userId: String) {
        viewModelScope.launch {
            val person = repository.getPersonById(userId)
            _personState.value = person
        }
    }


}
