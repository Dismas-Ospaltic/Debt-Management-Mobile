package com.ospaltic.mydebts.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ospaltic.mydebts.model.DebtEntity
import com.ospaltic.mydebts.repository.DebtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DebtViewModel(private val debtRepository: DebtRepository) : ViewModel() {

    // Holds the list of debts for a specific user
    private val _debts = MutableStateFlow<List<DebtEntity>>(emptyList())
    val debts: StateFlow<List<DebtEntity>> = _debts

//    // Holds the list of debt Ids for a specific user
//    private val _debtsId = MutableStateFlow<List<DebtEntity>>(emptyList())
//    val debtsId: StateFlow<List<DebtEntity>> = _debtsId

    private val _debtsId = MutableStateFlow<List<String>>(emptyList())
    val debtsId: StateFlow<List<String>> = _debtsId



    // Holds a single debt item
    private val _debtDetail = MutableStateFlow<DebtEntity?>(null)
    val debtDetail: StateFlow<DebtEntity?> = _debtDetail



//    // Holds a single debt item amount Rem
//    private val _debtAmountRem = MutableStateFlow<DebtEntity?>(null)
//    val debtAmountRem: StateFlow<DebtEntity?> = _debtAmountRem

    /**
     * Fetch all debts for a specific user
     */
    fun getAllDebts(userId: String) {
        viewModelScope.launch {
            debtRepository.getAllDebt(userId).collectLatest { debtList ->
                _debts.value = debtList
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



    private val _totalPaid = MutableStateFlow(0.0f)
    val totalPaid: StateFlow<Float> = _totalPaid

    fun fetchTotalUnpaidPaid(userId: String) {
        viewModelScope.launch {
            debtRepository.getTotalUnPaid(userId).collectLatest { total ->
                Log.d("DebtViewModel", "Total unpaid Updated: $total") // Debugging
                _totalPaid.value = total
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

//
}
