package com.ycspl.bingaa

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var age: Int,
    var gender: String,
    var profession: String
)
