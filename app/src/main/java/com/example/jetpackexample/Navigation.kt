package com.example.jetpackexample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            //Screen.DetailScreen.route + "?name={name}" -- Pass with options
            route = Screen.DetailScreen.route + "/{name}", // mandatory argument
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Nathan"
                    nullable = true
                }
            )
        ) { entry ->
            DetailScreen(name = entry.arguments?.getString("name"), navController = navController)
        }
        composable(Screen.ToCalculator.route) {
            MyApp(navController = navController)
        }
        composable(Screen.CarDetailScreen.route + "{/name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Nathan"
                    nullable = true
                }
            )
        ) { entry ->
            CarDetailScreen(name = entry.arguments?.getString("name"))

        }
    }
}

@Composable
fun CarDetailScreen(name: String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "This is the Description of car ,$name")
    }
}

@Composable
fun UniversalAppBar(title: String, compos: Composable) : Composable {
    val vm = UserState.current
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            vm.signOut()
                        }
                    }) {
                        Icon(Icons.Filled.ExitToApp, null)
                    }
                }
            )
        }) {

    }
    return compos
}

@Composable
fun MainScreen(navController: NavController) {

    var text by remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 100.dp, vertical = 100.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { navController.navigate(Screen.DetailScreen.withArgs(text)) },
            modifier = Modifier.align(Alignment.End)

        ) {
            Text(text = "To DetailsScreen")
        }
    }
}


@Composable
fun DetailScreen(name: String?, navController: NavController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Hello ,$name")
    }
    Button(
        onClick = { navController.navigate(Screen.ToCalculator.route) },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 100.dp, vertical = 100.dp)
    ) {
        Text(text = "To DetailsScreen")
    }


}

@Composable
fun CarHomeContent(navController: NavController) {
    val car = remember { DataProvider.carList }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {

        items(
            items = car,
            itemContent = {
                CarListItem(car = it, navController = navController)
            })
    }
}

@Composable
fun MyApp(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Top App Bar")
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Menu, "Menu")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp
            )
        }, content = {
            CarHomeContent(navController)
})
}


@Composable
private fun CarImage(car: Car) {
    Image(
        painter = painterResource(id = car.image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}


@Composable
fun CarListItem(car: Car, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.DetailScreen.withArgs(car.make)) },
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            CarImage(car)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = car.make, style = typography.h6)
                Text(text = car.model, style = typography.caption)
                Text(text = car.year, style = typography.body1)
            }
        }
    }
}
