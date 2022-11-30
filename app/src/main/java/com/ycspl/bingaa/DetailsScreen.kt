package com.ycspl.bingaa

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DetailsScreenView(name: String?, age: String?) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Hello,$name, $age old", modifier = Modifier.padding(20.dp))
    }
    
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreenView(name = "Vivek", 34.toString())
}