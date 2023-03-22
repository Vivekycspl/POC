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



}