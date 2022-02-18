package com.example.jetpackexample.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.jetpackexample.R

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)


private val lightBackgroundColor = Color(0xFFf3f3f3)
private val darkBackgroundColor = Color(0xFF15202b)
private val primaryColor = Color(0xFF2376e6)

val grayColor = Color(0xFF636363)

val lightThemeColors = darkColors(
    primary = primaryColor,
    background = lightBackgroundColor,
    surface = lightBackgroundColor
)

val darkThemeColors = lightColors(
    primary = primaryColor,
    background = darkBackgroundColor,
    surface = darkBackgroundColor
)


