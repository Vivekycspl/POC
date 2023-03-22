package com.ycspl.bingaa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "state")
    val state: String ? = null,
    @ColumnInfo(name = "city")
    val city: String ? = null
)
