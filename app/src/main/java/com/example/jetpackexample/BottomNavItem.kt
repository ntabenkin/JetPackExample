package com.example.jetpackexample

sealed class BottomNavItem(var title: String, var icon: Int, var route :String) {
    object Home: BottomNavItem("Home", R.drawable.ic_home,"home")
    object Calculator: BottomNavItem("Calculator", R.drawable.ic_calculator,"calculator")
    object CarList: BottomNavItem("CarList", R.drawable.ic_car, "car")
    object CarDetail: BottomNavItem("Car Details", R.drawable.ic_car, "car_details")
}