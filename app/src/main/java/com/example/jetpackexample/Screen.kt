package com.example.jetpackexample

sealed class Screen( val route: String){
    object MainScreen: Screen("main_screen")
    object DetailScreen : Screen("detail_screen")
    object ToCalculator : Screen("to_calc")
    object CarDetailScreen: Screen("to_car")
    object ToDropDown : Screen("drop")

    fun withArgs(vararg args: String): String{
        return buildString{
            append(route)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }
}
