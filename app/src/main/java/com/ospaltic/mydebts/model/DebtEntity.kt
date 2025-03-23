package com.ospaltic.mydebts.model

data class DebtEntity(
    val date: String,
    val debtId: String,
    val uid: String,
    val description: String,
    val dueDate: String,
    val amount: Float,
     val status: String //paid, partial, pending
)
