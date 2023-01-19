package com.ycspl.bingaa

import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.parcelize.Parcelize

@Parcelize
data class Test(val text: String) : Parcelable


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {

        composable(route = Screen.MainScreen.route) {
            MainScreen(navController)
        }

        composable(route = Screen.DetailsScreen.route+"/name={name}/age={age}", arguments = listOf(
            navArgument("name") {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument("age") {
                type = NavType.StringType
                defaultValue = ""
            },
        )) {navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString("name")
            val age = navBackStackEntry.arguments?.getString("age")
            val test = navController.previousBackStackEntry?.savedStateHandle?.get<Test>("test")
            DetailsScreenView(name = name, age = age, test)
        }

    }


}

@Composable
fun MainScreen(navController: NavHostController) {

    val nameText = remember{ mutableStateOf("") }
    val ageText = remember{ mutableStateOf("") }

    Column(modifier = Modifier.padding(50.dp)) {
        TextField(value = nameText.value, onValueChange = {
            nameText.value = it
        })
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = ageText.value, onValueChange = {
            ageText.value = it
        })
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            val test = Test("hello there")
            navController.currentBackStackEntry?.savedStateHandle?.set("test", test)
            navController.navigate(Screen.DetailsScreen.route +"/name=${nameText.value}/age=${ageText.value}")
        }) {
            Text(text = "Submit")
        }
    }

}



@Composable
fun DetailsScreenView(name: String?, age: String?, test: Test?) {
    Text(text = " ${test?.text} $name, your age is $age", modifier = Modifier.padding(100.dp))

}
