package com.ycspl.bingaa

data class SelectWrapper(
    val id: Int,
    val name: String,
    val bind_id: Int? = null,
    var doc_id : Long = 0L
)