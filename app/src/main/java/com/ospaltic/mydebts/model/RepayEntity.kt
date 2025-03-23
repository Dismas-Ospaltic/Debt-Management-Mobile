package com.ospaltic.mydebts.model

data class RepayEntity(
    val date: String,
    val debtId: String,
    val uid: String,
    val amountPaid: Float,
    val amountRem: Float,
)
