package uk.vaent.kanaflash.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SteelBlue,
    onPrimary = Color.Black,
    secondary = SteelBlueBB,
    onSecondary = Color.Black,
    surface = Color.Black,
    onSurface = SteelBlue,
    surfaceVariant = Color.DarkGray,
    onSurfaceVariant = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = SteelBlue,
    onPrimary = Color.White,
    secondary = SteelBlueBB,
    onSecondary = Color.White,
    surface = Color.White,
    onSurface = SteelBlue,
    surfaceVariant = Color.LightGray,
    onSurfaceVariant = Color.Black
)

@Composable
fun KanaFlashTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
