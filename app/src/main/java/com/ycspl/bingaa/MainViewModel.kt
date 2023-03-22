package com.ycspl.bingaa

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val context: Context
): ViewModel() {



    val mutableLocation = MutableStateFlow<Location?>(null)
    val location = mutableLocation.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val mutableSelectedState = MutableStateFlow<String>("Select State")
    val selectedState = mutableSelectedState.asStateFlow()

    val mutableSelectedCity = MutableStateFlow<String>("Select City")
    val selectedCity = mutableSelectedCity.asStateFlow()

    init {
        getLocation(2)
        loadLocation()
    }

    private fun loadLocation() {
        mutableSelectedState.value = location.value?.state ?: "Select State"
        mutableSelectedCity.value = location.value?.city ?: "Select City"
    }

    fun saveLocation(name: String, name1: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = PersonDatabase.getInstance(context)
            val location = Location(state = selectedState.value, city = selectedCity.value)
            val id = database.getDao().insert(location)
            getLocation(id)
        }
    }

    fun getLocation(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = PersonDatabase.getInstance(context)
            val location = database.getDao().getPersonById(id)
            mutableLocation.value = location
        }
    }

    fun changeSelectedState(selectedID: String) {
        mutableSelectedState.value = selectedID
    }

    fun changeSelectedCity(selectedID: String) {
        mutableSelectedCity.value = selectedID
    }


}