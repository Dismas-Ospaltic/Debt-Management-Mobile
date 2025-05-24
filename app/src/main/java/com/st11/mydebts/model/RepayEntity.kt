package com.st11.mydebts.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debt_history")
data class RepayEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, //DD-MM-YYYY
    val debtId: String,
    val uid: String,
    val amountPaid: Float,
    val amountRem: Float,
    val timestamp: Long = System.currentTimeMillis()
    )
