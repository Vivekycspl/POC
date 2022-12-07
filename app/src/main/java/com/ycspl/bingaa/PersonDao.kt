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
    fun getAllPersons() : Flow<List<Person>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person:Person)

    @Query("update person SET profession=:person where id=:id")
    suspend fun update(person: String, id: Long)

    @Update
    suspend fun updateDetails(person: Person)

    @Delete
    suspend fun delete(person: Person)

    @Query("select * from person where id=:personID")
    fun getPersonById(personID: Long): Person
}