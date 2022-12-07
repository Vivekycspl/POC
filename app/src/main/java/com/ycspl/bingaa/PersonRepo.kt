package com.ycspl.bingaa

import kotlinx.coroutines.flow.Flow

interface PersonRepo {
    suspend fun insert(person: Person)
    suspend fun delete(person: Person)
    suspend fun update(person: String, id: Long)
    fun getAllPersons() : Flow<List<Person>>
}