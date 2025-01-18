import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val DarkGray = Color(0xFF121212) // Dark background
val LightGray = Color(0xFF292929) // Slightly lighter for surfaces
val Whiteish = Color(0xFFEEEEEE) // Off-white for text

val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Color.White,
    secondary = PurpleGrey80,
    onSecondary = Color.White,
    tertiary = Pink80,
    onTertiary = Color.White,
    background = DarkGray, // Set background to dark gray
    onBackground = Whiteish, // Text color on dark background
    surface = LightGray, // Surfaces like buttons
    onSurface = Whiteish,
)

val LightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    secondary = PurpleGrey40,
    onSecondary = Color.White,
    tertiary = Pink40,
    onTertiary = Color.White,
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
)