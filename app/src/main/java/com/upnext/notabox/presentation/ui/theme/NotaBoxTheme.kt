package com.upnext.notabox.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

fun lightColors() = NotaBoxColors(
    primary = White,
    iconTint = Black,
    background = White,
    onBackground = Black,
    onBackgroundText = White,
    onBackgroundIconTint = White,
    secondary = LightGray,
    text = Black,
    description = Silver,
    selected = Black,
    onSelected = White,
    unSelected = LightGray,
    onUnSelected = White,
    checkBoxBorder = VeryLightGreen,
    checkBoxChecked = VeryLightGreen,
    onCheckBoxChecked = Black,
    radioButtonSelected = Black,
    radioButtonUnSelected = LightGray,
    creatingNoteTFTitleText = VeryDarkGray,
    searchTFOuterBackground = VeryLightGray,
    searchTFBackground = LightGray,
    onSearchTFBackground = MediumGrey,
    searchTFCancelButton = SoftBlue,
    dialogBgColor = White,
    drawerBgColor = White
)
fun darkColors() = NotaBoxColors(
    primary = Black,
    iconTint = White,
    background = Black,
    onBackground = White,
    onBackgroundText = Black,
    onBackgroundIconTint = Black,
    secondary = LightGray,
    text = White,
    description = Silver,
    selected = White,
    onSelected = Black,
    unSelected = Color(0xFF2E3339),
    onUnSelected = Black,
    checkBoxBorder = VeryLightGreen,
    checkBoxChecked = VeryLightGreen,
    onCheckBoxChecked = Black,
    radioButtonSelected = White,
    radioButtonUnSelected = LightGray,
    creatingNoteTFTitleText = White,
    searchTFOuterBackground = VeryDarkGray,
    searchTFBackground = SmokeyGray,
    onSearchTFBackground = LightGray,
    searchTFCancelButton = SoftBlue,
    dialogBgColor = MidBlack,
    drawerBgColor = MidBlack
)


val LocalSpaces = staticCompositionLocalOf { Spaces() }

val LocalColors = staticCompositionLocalOf { lightColors() }

val LocalTypography = staticCompositionLocalOf { Typography() }

object NotaBoxTheme {
    val colors: NotaBoxColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val spaces: Spaces
        @Composable
        @ReadOnlyComposable
        get() = LocalSpaces.current
}


@Composable
fun NotaBoxTheme(
    spaces: Spaces = NotaBoxTheme.spaces,
    typography: Typography = NotaBoxTheme.typography,
    colors: NotaBoxColors = lightColors(),
    darkColors: NotaBoxColors = darkColors(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val currentColors = remember { if (darkTheme) darkColors else colors }
    val rememberedColors = remember { currentColors.copy() }.apply { updateColorsFrom(currentColors) }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if (!darkTheme) White.toArgb() else Black.toArgb()
        }
    }


    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalSpaces provides spaces,
        LocalTypography provides typography,
    ) {
        ProvideTextStyle(typography.title, content = content)
    }

}
