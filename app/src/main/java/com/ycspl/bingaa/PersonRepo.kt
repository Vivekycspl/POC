package com.ycspl.bingaa

import kotlinx.coroutines.flow.Flow

interface PersonRepo {
    suspend fun insert(location: Location): Long
    suspend fun getLocation(location: Long): Location?
    suspend fun delete(location: Location)
    suspend fun update(person: Location, id: Long)
    fun getAllPersons() : Flow<List<Location>>
}