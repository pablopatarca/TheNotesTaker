package app.pablopatarca.thenotestaker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = GrayPrimary,
    primaryVariant = GrayPrimary,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = GrayPrimary,
    primaryVariant = GrayPrimary,
    secondary = Teal200,

    // Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

// Note colors
val NoteWhite = Color(0xFFFAFAFA)
val NoteRed = Color(0xFFFF8A80)
val NoteLime = Color(0xFFDCE775)
val NoteBlue = Color(0xFF90CAF9)
val NoteGreen = Color(0xFF00C853)

@Composable
fun TheNotesTakerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        systemUiController.setSystemBarsColor(color = Color.White)
        DarkColorPalette
    } else {
        systemUiController.setSystemBarsColor(color = Color.LightGray)
        LightColorPalette
    }


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}