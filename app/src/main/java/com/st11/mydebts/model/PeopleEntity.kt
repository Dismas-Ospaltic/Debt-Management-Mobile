package com.st11.mydebts.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class PeopleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uid: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val address: String,
    val businessName: String,
    val date: String, //DD-MM-YYYY
    val timestamp: Long = System.currentTimeMillis()
)
