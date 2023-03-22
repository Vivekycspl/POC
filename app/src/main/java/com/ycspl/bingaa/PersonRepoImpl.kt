package com.ycspl.bingaa

import android.util.Log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepoImpl@Inject constructor(
    private val dao: PersonDao
) : PersonRepo {

    override suspend fun insert(location: Location): Long {
        return dao.insert(location)
    }

    override suspend fun getLocation(id: Long): Location? {
        return dao.getPersonById(id)
    }

    override suspend fun delete(location: Location) {
        dao.delete(location)
    }

    override suspend fun update(person: Location, id: Long) {
        Log.d("TAG", "update: $person")
        dao.updateDetails(person)
    }

    override fun getAllPersons(): Flow<List<Location>> {
        return dao.getAllPersons()
    }
}