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

    // Holds a single debt item
    private val _debtDetail = MutableStateFlow<DebtEntity?>(null)
    val debtDetail: StateFlow<DebtEntity?> = _debtDetail

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
     * Get a specific debt by its ID
     */
    fun getDebtById(debtId: String) {
        viewModelScope.launch {
            _debtDetail.value = debtRepository.getDebtById(debtId)
        }
    }



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

//
}
