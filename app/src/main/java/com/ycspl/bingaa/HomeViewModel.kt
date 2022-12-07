package com.ycspl.bingaa

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val context: Context,
    private val personsRepo: PersonRepo
) : ViewModel() {

    val persons = personsRepo.getAllPersons()

    private val _elements = mutableStateListOf<String>()
    val list : List<String> = _elements

    fun insertPerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            personsRepo.insert(person)
        }
    }

    fun updatePerson(person: String, id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            personsRepo.update(person, id)
        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            personsRepo.delete(person)
        }
    }

    fun addItem(item: String) {
        _elements.add(item)
        Log.d("TAG", "addItem: ${_elements.size}")
    }


}