package com.ospaltic.mydebts.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debt")
data class DebtEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, //DD-MM-YYYY
    val debtId: String,
    val uid: String,
    val description: String,
    val dueDate: String, //DD-MM-YYYY
    val amount: Float,
    val amountRem: Float,
    val amountPaid: Float,
    val status: String, //paid, partial, pending
    val timestamp: Long = System.currentTimeMillis()
)
