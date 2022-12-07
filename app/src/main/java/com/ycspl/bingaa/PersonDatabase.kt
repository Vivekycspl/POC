package com.ycspl.bingaa

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonDatabase : RoomDatabase(){
    abstract fun getDao() : PersonDao
}