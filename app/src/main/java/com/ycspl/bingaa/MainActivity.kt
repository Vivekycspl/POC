package com.ycspl.bingaa

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ycspl.bingaa.ui.theme.BingAATheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BingAATheme {
                Navigation()
            }
        }
    }

    @Composable
    private fun test() {
        val viewModel: HomeViewModel = hiltViewModel()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            val list = viewModel.list
            var text by remember { mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = { text = it },
                textStyle = TextStyle(color = Color.Red)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                viewModel.addItem(text)
            }) {
                Text(text = "Submit")
            }

            list.forEach {
                Text(
                    text = it,
                    color = Color.Red
                )
            }

        }
    }
}