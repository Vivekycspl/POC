package com.ycspl.bingaa

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
                MainScreen(navController = navController)
        }

        composable(
            route = Screen.DetailsScreen.route + "/{name}/{age}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Vivek"
                    nullable = true
                },
                navArgument("age") {
                    type = NavType.StringType
                    defaultValue = "34"
                    nullable = true
                }
            )
        ) { entry ->
            DetailsScreenView(
                name = entry.arguments?.getString("name"),
                age = entry.arguments?.getString("age")
            )
        }
    }
}


@Composable
fun MainScreen(navController: NavController) {

    var text = remember{
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {

        TextField(
            value = text.value,
            onValueChange = {
                text.value = it
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {

            val User = User("Vivek", "Singh")
            navController.currentBackStackEntry?.savedStateHandle?.set("user", User)
            navController.navigate(Screen.DetailsScreen.route+"/Phillip/50")
        }) {
            Text(text = "Sumbit")
        }

    }
}