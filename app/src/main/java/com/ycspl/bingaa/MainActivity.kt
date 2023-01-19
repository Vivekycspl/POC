package com.ycspl.bingaa

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.navigation.compose.rememberNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ycspl.bingaa.ui.theme.BingAATheme
import com.ycspl.correctionapp.common.uicomponents.CommonDropDownButton
import com.ycspl.correctionapp.common.uicomponents.DependentDropDown
import dagger.hilt.android.AndroidEntryPoint

data class UserModel(val name: String, val age: Int, val address: String)
data class Wrapper(val id: Int, val name:String)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BingAATheme {

                val user1 = UserModel("Vivek", 20, "Kharela")
                val user2 = UserModel("Vivekgg", 20, "Kharelqqa")
                val list = mutableListOf<UserModel>()
                list.add(user1)
                list.add(user2)

                var count = 0

                val modifiedList = list.map { Wrapper(id = ++count, name = it.name) }

                Log.d("TAG", "onCreate: ${modifiedList.get(0).id} ${modifiedList.get(0).id}")

                Navigation()
            }
        }
    }
}