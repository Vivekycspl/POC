package com.ycspl.bingaa

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("select * from person")
    fun getAllPersons() : Flow<List<Location>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location:Location): Long

    @Update
    suspend fun updateDetails(location: Location)

    @Delete
    suspend fun delete(location: Location)

    @Query("select * from person where id=:personID")
    fun getPersonById(personID: Long): Location?

}