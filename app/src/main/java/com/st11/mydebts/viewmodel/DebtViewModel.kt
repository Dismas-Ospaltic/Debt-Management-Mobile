package com.st11.mydebts.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.st11.mydebts.model.DebtEntity
import com.st11.mydebts.model.RecentDebt
import com.st11.mydebts.repository.DebtRepository
import com.st11.mydebts.repository.PeopleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DebtViewModel(private val debtRepository: DebtRepository,
                    private val peopleRepository: PeopleRepository) : ViewModel() {

    // Holds the list of debts for a specific user
    private val _debts = MutableStateFlow<List<DebtEntity>>(emptyList())
    val debts: StateFlow<List<DebtEntity>> = _debts

//    // Holds the list of debt Ids for a specific user
//    private val _debtsId = MutableStateFlow<List<DebtEntity>>(emptyList())
//    val debtsId: StateFlow<List<DebtEntity>> = _debtsId

    private val _debtsId = MutableStateFlow<List<String>>(emptyList())
    val debtsId: StateFlow<List<String>> = _debtsId

    // Holds the list of recent debts
    private val _recentDebts = MutableStateFlow<List<DebtEntity>>(emptyList())
    val recentDebts: StateFlow<List<DebtEntity>> = _recentDebts



    // Holds a single debt item
    private val _debtDetail = MutableStateFlow<DebtEntity?>(null)
    val debtDetail: StateFlow<DebtEntity?> = _debtDetail



//    // Holds a single debt item amount Rem
//    private val _debtAmountRem = MutableStateFlow<DebtEntity?>(null)
//    val debtAmountRem: StateFlow<DebtEntity?> = _debtAmountRem

    /**
     * Fetch all debts for a specific user
     */
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

   fun getAllDebts(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000)
            debtRepository.getAllDebt(userId).collectLatest { debtList ->
                _debts.value = debtList
                _isLoading.value = false //used to show shimmer effect by checking if function is ready
            }
        }
    }


    /**
     * Fetch all debts id for a specific user
     */
//    fun getAllDebtsId(userId: String) {
//        viewModelScope.launch {
//            debtRepository.getAllDebtId(userId).collectLatest { debtList ->
//                _debtsId.value = debtList
//            }
//        }
//    }


//   fun getAllRecentDebt(){
//        viewModelScope.launch {

//            debtRepository.getAllRecentDebt().collectLatest { debtList ->
//                _recentDebts.value = debtList

//            }
//        }
//   }

    private val _isRecentLoading = MutableStateFlow(true)
    val isRecentLoading: StateFlow<Boolean> = _isRecentLoading

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getRecentDebtList(): Flow<List<RecentDebt>> =
        debtRepository.getAllRecentDebt().flatMapLatest { debts ->
            flow {
                _isRecentLoading.value = true // ✅ side-effect now inside flow block

                delay(2000) // optional shimmer delay, now inside flow

                val result = coroutineScope {
                    debts.map { debt ->
                        async {
                            val person = peopleRepository.getPersonById(debt.uid)
                            person?.let {
                                RecentDebt(
                                    firstName = it.firstName,
                                    lastName = it.lastName,
                                    phone = it.phone,
                                    amount = debt.amount,
                                    dueDate = debt.dueDate
                                )
                            }
                        }
                    }.awaitAll().filterNotNull()
                }

                emit(result)
                _isRecentLoading.value = false // ✅ set loading to false after emit
            }
        }




    fun getAllDebtsId(userId: String) {
        viewModelScope.launch {
            debtRepository.getAllDebtId(userId).collectLatest { debtList ->
                _debtsId.value = debtList // _debtsId should be StateFlow<List<String>>
            }
        }
    }


    /**
     * Insert a new debt record
     */
    fun insertDebt(debt: DebtEntity) {
        viewModelScope.launch {
            debtRepository.insert(debt)
        }
    }

    /**
     * Update an existing debt record
     */
    fun updateDebt(debt: DebtEntity) {
        viewModelScope.launch {
            debtRepository.update(debt)
        }
    }

    /**
     * Update the status of a debt
     */
    fun updateDebtStatus(debtId: String, newStatus: String) {
        viewModelScope.launch {
            val success = debtRepository.updateDebtStatus(debtId, newStatus)
            if (success) {
                // Refresh debt list if update is successful
                _debts.value = _debts.value.map { debt ->
                    if (debt.debtId == debtId) debt.copy(status = newStatus) else debt
                }
            }
        }
    }



    /**
     * Update the values of a debt
     */
    fun updateDebtValues(debtId: String, newAmountRem: Float, newAmountPaid: Float) {
        viewModelScope.launch {
            val success = debtRepository.updateDebtValues(debtId, newAmountRem, newAmountPaid)
            if (success) {
                // Refresh debt list if update is successful
                _debts.value = _debts.value.map { debt ->
                    if (debt.debtId == debtId) debt.copy(amountRem = newAmountRem, amountPaid = newAmountPaid) else debt
                }
            }
        }
    }



    /**
     * Get a specific debt by its ID
     */
    fun getDebtById(debtId: String) {
        viewModelScope.launch {
            _debtDetail.value = debtRepository.getDebtById(debtId)
        }
    }



    suspend fun fetchDebtById(debtId: String): DebtEntity? {
        return debtRepository.getDebtById(debtId)
    }


    /**
     * Get a specific debt by its ID
     */
//    fun getAmountRemDebtById(debtId: String) {
//        viewModelScope.launch {
//            _debtAmountRem.value = debtRepository.getDebtAmountRemById(debtId)
//        }
//    }



    private val _totalPaidAllPeople = MutableStateFlow(0.0f)
    val totalPaidAllPeople: StateFlow<Float> = _totalPaidAllPeople

    fun fetchTotalUnpaidPaidAllPeople() {
        viewModelScope.launch {
            debtRepository.getAllUnpaidTotalAllPeople().collectLatest { total ->
                _totalPaidAllPeople.value = total
            }
        }
    }



    private val _totalUnpaid = MutableStateFlow(0.0f)
    val totalUnpaid: StateFlow<Float> = _totalUnpaid

    fun fetchTotalUnpaid(userId: String) {
        viewModelScope.launch {
            debtRepository.getTotalUnPaid(userId).collectLatest { total ->
                Log.d("DebtViewModel", "Total unpaid Updated: $total") // Debugging
                _totalUnpaid.value = total
            }
        }
    }




    private val _totalNoUnpaid = MutableStateFlow(0)
    val totalNoUnpaid : StateFlow<Int> = _totalNoUnpaid

    fun fetchTotalNoUnpaid() {
        viewModelScope.launch {
            debtRepository.getAllTotalPendingDebt().collectLatest { total ->
                _totalNoUnpaid.value = total
            }
        }
    }




    private val _totalNoPartial = MutableStateFlow(0)
    val totalNoPartial : StateFlow<Int> = _totalNoPartial

    fun fetchTotalNoPartial() {
        viewModelScope.launch {
           debtRepository.getAllTotalPartialDebt().collectLatest { total ->
                _totalNoPartial.value = total
            }
        }
    }




    private val _totalNoPaid = MutableStateFlow(0)
    val totalNoPaid : StateFlow<Int> = _totalNoPaid

    fun fetchTotalNoPeople() {
        viewModelScope.launch {
            debtRepository.getAllTotalPaidDebt().collectLatest { total ->
                _totalNoPaid.value = total
            }
        }
    }


    private val _totalNoOverDue = MutableStateFlow(0)
    val totalNoOverDue : StateFlow<Int> = _totalNoOverDue

    fun fetchTotalNoOverDue() {
        viewModelScope.launch {
            debtRepository.getAllTotalPastDueDebt().collectLatest { total ->
                _totalNoOverDue.value = total
            }
        }
    }



//
}
