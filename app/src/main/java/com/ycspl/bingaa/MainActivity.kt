package com.ycspl.bingaa

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ycspl.bingaa.ui.theme.BingAATheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BingAATheme {
                test()
            }
        }
    }

    @Composable
    private fun test() {
        val viewModel: HomeViewModel = hiltViewModel()
        val persons by viewModel.persons.collectAsState(initial = emptyList())
        val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                viewModel.insertPerson(Person(name = "vivek", age = 34, gender = "Male", profession = "Engineer" ))
            }) {
                Text(text = "Insert")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                val person = persons.get(0)
                person.name = "Mukesh"
                viewModel.insertPerson(person)
            }) {
                Text(text = "Update Name")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                val person = persons.get(0)
//                viewModel.updatePerson("Advocate", person.id)
                val oneTimeWorkRequest =
                    OneTimeWorkRequestBuilder<UpdatePersonWorker>().setInputData(
                        Data.Builder().putLong("personId", person.id).build()
                    ).build()
                WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
            }) {
                Text(text = "Update Profession")
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn{
                items(persons) {
                    PersonCard(it)
                }
            }

        }
    }
}