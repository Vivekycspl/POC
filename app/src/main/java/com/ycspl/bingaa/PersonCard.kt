package com.ycspl.bingaa

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PersonCard(person: Person) {
    Card(border = BorderStroke(width = 1.dp, color = Color.Blue), modifier = Modifier.fillMaxWidth().padding(15.dp)) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "Name :${person.name}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Age :${person.age}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Gender :${person.gender}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Profession :${person.profession}")
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}