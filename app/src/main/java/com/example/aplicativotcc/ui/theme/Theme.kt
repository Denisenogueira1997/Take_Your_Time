package com.example.aplicativotcc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,

    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,

    background = Background,
    onBackground = OnBackground,

    surface = Surface,
    onSurface = OnSurface,

    outline = Outline,
    error = Error,
    onError = OnError
)


private val DarkColors = darkColorScheme(
    primary = PrimaryContainer,
    onPrimary = OnPrimaryContainer,

    secondary = Secondary,
    onSecondary = OnSecondary,

    background = Color(0xFF1C1B1A),
    onBackground = OnBackground,

    surface = Color(0xFF2A2928),
    onSurface = OnSurface,

    error = Error,
    onError = OnError
)


@Composable
fun AplicativoTCCTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    val view = LocalView.current

    if (!view.isInEditMode) {
        val window = (view.context as android.app.Activity).window
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
