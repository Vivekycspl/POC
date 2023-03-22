package com.ycspl.bingaa

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class PersonDatabase : RoomDatabase(){
    abstract fun getDao() : PersonDao

    companion object {
        @Volatile
        var INSTANCE: PersonDatabase ? = null

        fun getInstance(context: Context): PersonDatabase {
            return INSTANCE ?: synchronized(this) {
                var instance = Room.databaseBuilder(
                    context,
                    PersonDatabase::class.java,
                    "location"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }

        }

    }

}