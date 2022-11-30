package com.ycspl.bingaa

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    private val _elements = mutableStateListOf<String>()
    val list : List<String> = _elements

    fun addItem(item: String) {
        _elements.add(item)
        Log.d("TAG", "addItem: ${_elements.size}")
    }


}