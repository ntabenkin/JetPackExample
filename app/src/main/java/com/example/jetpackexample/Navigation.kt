package com.example.jetpackexample

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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
            CarDetailScreen("nathan")
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
fun DropDown(){
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Porche 911 ","Porche Boxster","Porche Cayman")
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.Check //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown

    Column() {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = {Text("Label")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                }) {
                    Text(text = label)
                }
            }
        }
        }
    when (selectedText) {
        suggestions[0] -> {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.turbo),
                    contentDescription = "",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .height(350.dp)
                        .width(350.dp)
                        .padding(start = 60.dp)
                        .padding(10.dp),
                )
                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "\n\n" +
                                "With transcendent driving traits and terrific twin-turbo flat-six engines, the 2022 Porsche 911 is a nearly flawless sports car, and it's an obvious Editors' Choice winner. Apart from being too expensive for most enthusiasts, Porsche's iconic two-plus-two-seater is as immersive as it is versatile. Along with the aforementioned flat-six which produces a distinctive snarl and makes between 379 and 473 horsepower–we review the higher-powered GT3 and Turbo variants separately lineup offers a quick-shifting dual-clutch automatic or a manual transmission and rear- or all-wheel drive. The latter ensures its incredible performance can be enjoyed in all four seasons. The 911 comes in coupe, cabriolet (read: convertible), or quasi-convertible Targa body styles. The beauty of the 911 is that its athleticism doesn't diminish its livability. Its ride is surprisingly civil considering its cornering limits and race car-like steering feel. Its interior is roomy up front and can be lavishly appointed. And its driver is treated to a satisfyingly low seating position but also excellent outward visibility."
                    )
                }
            }
        }
        suggestions[1] -> {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.boxster_n),
                    contentDescription = "",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .height(350.dp)
                        .width(350.dp)
                        .padding(start = 60.dp)
                        .padding(10.dp),
                )
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "\n" +
                                "Few cars, including other sports cars we love, offer the same purity of mission as the 2022 Porsche 718 Cayman. Its mid-engine arrangement and expertly tuned suspension give it a joyful driving demeanor that's truly addictive, and we again named it to our 2022 Editors' Choice list. Buyers can choose from a horizontally opposed four- or six-cylinder engine, the latter of which sings a siren's song that encourages aggressive driving. Fans of open-air driving will find the 718 Boxster a similar experience, but we review that model separately. While the Cayman commands a price premium over rivals such as the Chevy Corvette and Toyota Supra, its focused persona and Porsche brand image are enough to justify the increased cost. Of course, its transcendent driving traits are what secure its spot on our 10Best list."
                    )
                }
            }
        }
        else -> {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.boxster),
                    contentDescription = "",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .height(350.dp)
                        .width(350.dp)
                        .padding(start = 60.dp)
                        .padding(10.dp),
                )
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "\n" +
                                "Most convertibles are a romantic way to enjoy the open road, but the 2022 Porsche 718 Boxster takes it a step further with its joyous handling and eager turbocharged powertrains. It shares its chassis and mechanical parts with the similarly sporty 718 Cayman coupe, but the Boxster's cloth top opens the cabin to fresh air and the freeing feeling of the wind in your hair. Base models come with a turbocharged horizontally opposed four-cylinder, but speed freaks will gravitate toward the optional flat-six. The 718 Boxster may not have the cachet of Porsche's iconic 911 sports car, but its focused chassis and lively nature make it one of the best-driving sports cars on the road earn it a 10Best award and a spot on our Editors' Choice list."
                    )
                }
            }
        }
    }
}

