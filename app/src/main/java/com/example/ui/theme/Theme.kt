package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val EthioMatricColorScheme = darkColorScheme(
    primary = EmeraldAccent,
    secondary = BlueAccent,
    background = SlateBg,
    surface = SlateSurface,
    error = RedAccent,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextMain,
    onSurface = TextMain,
    onSurfaceVariant = TextMuted,
    outline = SlateBorder
)

private val DarkColorScheme =
  darkColorScheme(primary = Purple80, secondary = PurpleGrey80, tertiary = Pink80)

private val LightColorScheme =
  lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Force dark mode as default for the premium slate aesthetic
  dynamicColor: Boolean = false, // Disable dynamic color to highlight the bespoke Ethio Matric branding
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) EthioMatricColorScheme else LightColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

