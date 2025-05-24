package com.st11.mydebts.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.st11.mydebts.model.PeopleEntity
import com.st11.mydebts.repository.PeopleRepository
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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
    private val _isPersonDetailsLoading = MutableStateFlow(true)

    val isPersonDetailsLoading: StateFlow<Boolean> = _isPersonDetailsLoading
    fun getPersonDetails(userId: String) {
        viewModelScope.launch {
            _isPersonDetailsLoading.value = true
            delay(2000)
            val person = repository.getPersonById(userId)
            _personState.value = person
            _isPersonDetailsLoading.value = false
        }
    }




    private val _totalPeople = MutableStateFlow(0)
    val totalPeople : StateFlow<Int> = _totalPeople

    fun fetchTotalNoPeople() {
        viewModelScope.launch {
            repository.getAllTotalPeople().collectLatest { total ->
                _totalPeople.value = total
            }
        }
    }


    /**
     * Update the values of customer/person
     */
//    fun updateCustomerDetails(userId: String, firstName: String, lastName: String, email: String, phone: String, businessName: String, address: String) {
//        viewModelScope.launch {
//            val success = repository.editPersonDetail(userId, firstName, lastName, email, phone, businessName, address)
//            if (success) {
//                // Refresh customer details if update is successful
//                _personState.value = _personState.value.map { person ->
//                    if (person.uid == userId) person.copy(firstName = firstName, lastName = lastName, email = email, phone = phone, businessName = businessName, address = address) else person
//                }
//            }
//        }
//    }
    fun updateCustomerDetails(
        userId: String,
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        businessName: String,
        address: String
    ) {
        viewModelScope.launch {
            val success = repository.editPersonDetail(userId, firstName, lastName, email, phone, businessName, address)
            if (success) {
                _personState.value = _personState.value?.let { person ->
                    if (person.uid == userId) {
                        person.copy(
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            phone = phone,
                            businessName = businessName,
                            address = address
                        )
                    } else person
                }
            }
        }
    }



}
