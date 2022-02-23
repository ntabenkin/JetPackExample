package com.example.jetpackexample

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Car(
    val id: Int,
    val make: String,
    val model: String,
    val year: String,
    val image: Int = 0
)

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
                    Text(text = "Example App Bar")
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
                Text(text = car.make, style = MaterialTheme.typography.h6)
                Text(text = car.model, style = MaterialTheme.typography.caption)
                Text(text = car.year, style = MaterialTheme.typography.body1)
            }
        }
    }
}
