package com.st11.mydebts.model

data class PaymentItem(val date: String, val dueDate: String, val amount: String,
                       val paid: String,
                       val status: String)
