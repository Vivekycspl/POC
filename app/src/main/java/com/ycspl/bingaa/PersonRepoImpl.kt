package com.ycspl.bingaa

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepoImpl@Inject constructor(
    private val dao: PersonDao
) : PersonRepo {

    override suspend fun insert(person: Person) {
        dao.insert(person)
    }

    override suspend fun delete(person: Person) {
        dao.delete(person)
    }

    override suspend fun update(person: String, id: Long) {
        dao.update(person, id)
    }

    override fun getAllPersons(): Flow<List<Person>> {
        return dao.getAllPersons()
    }
}