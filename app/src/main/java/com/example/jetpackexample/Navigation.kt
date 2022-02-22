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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
                    nullable = false
                }
            )
        ) { entry ->
            DetailScreen(name = entry.arguments?.getString("name"), navController = navController)
        }
        composable(Screen.ToCalculator.route) {
            RecyclerContent(navController = navController)
        }
        composable(Screen.ToDropDown.route) {
            MyScreen()
        }
        composable(Screen.CarDetailScreen.route){
            CarDetailScreen("nath")
        }
        composable(
            route = Screen.CarDetailScreen.route + "{/name}",
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
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "The Porsche 911 (pronounced Nine Eleven or in German: Neunelfer) is a two-door 2+2 high performance rear-engined sports car introduced in September 1964 by Porsche AG of Stuttgart, Germany. It has a rear-mounted flat-six engine and originally a torsion bar suspension. $name " )
                }
            }
        })
}



@Composable
fun DropdownDemo(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("A", "B", "C", "D", "E", "F")
    val disabledValue = "B"
    var selectedIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.TopStart)) {
        Text(items[selectedIndex],modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { expanded = true })
            .background(
                Color.Gray
            ))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.Red
                )
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) {
                    val disabledText = if (s == disabledValue) {
                        " (Disabled)"
                    } else {
                        ""
                    }
                    Text(text = s + disabledText)
                }
            }
        }

    }

}
@Composable
fun MyScreen() {

    Box(Modifier.fillMaxSize(), Alignment.Center) {

        Box {
            var expanded by remember { mutableStateOf(false) }

            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                MyMenuItem("item 1")           // Icon visible
                MyMenuItem("item 2")           // Icon visible
                MyMenuItem("item 3 long text") // Icon width shrunk to 0
                MyMenuItem("item 4 long te")   // Icon visible but shrunk
            }
            CarDetailScreen("dsd")
        }

    }
}
const val MaterialIconDimension = 24f

@Composable
fun MyMenuItem(text: String) {
    DropdownMenuItem(
        onClick = { }
    ) {
        Text(text, Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            modifier = Modifier.size(MaterialIconDimension.dp)
        )
        CarDetailScreen("")
    }
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
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 100.dp, vertical = 100.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Hello ,$name")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate(Screen.ToCalculator.route) },
            modifier = Modifier
                .align(Alignment.End),
            enabled = true
        ) {
            Text(text = "To DetailsScreen")
        }
    }
}
@Composable
fun CarDetailsScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 100.dp, vertical = 100.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(text = "The Porsche 911 (pronounced Nine Eleven or in German: Neunelfer) is a two-door 2+2 high performance rear-engined sports car introduced in September 1964 by Porsche AG of Stuttgart, Germany. It has a rear-mounted flat-six engine and originally a torsion bar suspension.")
        }
        Spacer(modifier = Modifier.height(8.dp))
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
fun RecyclerContent(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Top App Bar")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.ToDropDown.route) }) {
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
            .clickable { navController.navigate(Screen.CarDetailScreen.route) },
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
