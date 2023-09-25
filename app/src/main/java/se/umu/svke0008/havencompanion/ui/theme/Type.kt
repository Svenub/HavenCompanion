package se.umu.svke0008.havencompanion.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import se.umu.svke0008.havencompanion.R

object DisplayLargeVFConfig {
    const val WEIGHT = 950
    const val WIDTH = 30f
    const val SLANT = -6f
    const val ASCENDER_HEIGHT = 800f
    const val COUNTER_WIDTH = 500
}


val PirataOne = FontFamily(Font(R.font.pirataone_regular))

val NyalaSimilar =
    FontFamily(
        Font(resId = R.font.sortsmillgoudy_regular),
        Font(R.font.sortsmillgoudy_italic)
    )

val NyalaAlternative = FontFamily(
    Font(R.font.gfs_neohellenic),
    Font(R.font.gfs_neohellenic_bold)
)

val NyalaAlternative2 = FontFamily(
    Font(R.font.bellefair)
)


val HavenTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = PirataOne,
        fontWeight = FontWeight.Normal,
        lineHeight = 47.sp,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = NyalaSimilar,
        fontWeight = FontWeight.Normal,
        lineHeight = 45.sp,
        fontSize = 45.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = PirataOne,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        letterSpacing = 0.7.sp
    ),

    titleLarge = TextStyle(
        fontFamily = NyalaSimilar,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp

    ),
    titleMedium = TextStyle(
        fontFamily = NyalaSimilar,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = PirataOne,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = NyalaSimilar,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )

)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        lineHeight = 47.sp,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        lineHeight = 45.sp,
        fontSize = 45.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        letterSpacing = 0.7.sp
    ),

    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp

    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)

val testString2 = "Testing typography"
val testString1 = "The lazy fox jumped High Tower"

@Preview(name = "Display Large")
@Composable
fun DisplayLarge() {
    HavenCompanionTheme(havenFont = true) {
        Text(text = testString1, style = MaterialTheme.typography.displayLarge)
    }
}

@Preview(name = "Display Medium")
@Composable
fun DisplayMedium() {
    HavenCompanionTheme(havenFont = true) {
        Text(text = testString1, style = MaterialTheme.typography.displayMedium)
    }
}

@Preview(name = "Headline Large")
@Composable
fun HeadlineLarge() {
    HavenCompanionTheme(havenFont = true) {
        Text(text = testString1, style = MaterialTheme.typography.headlineLarge)
    }
}

@Preview(name = "Title Large")
@Composable
fun TitleLarge() {
    HavenCompanionTheme(havenFont = true) {
        Text(text = testString1, style = MaterialTheme.typography.titleLarge)
    }
}

@Preview(name = "Title Medium")
@Composable
fun TitleMedium() {
    HavenCompanionTheme(havenFont = true) {
        Text(text = testString1, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(name = "Body Large")
@Composable
fun BodyLarge() {
    HavenCompanionTheme(havenFont = true) {
        Text(text = testString1, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(name = "Label Large")
@Composable
fun LabelLarge() {
    HavenCompanionTheme(havenFont = true) {
        Text(text = testString1, style = MaterialTheme.typography.labelLarge)
    }
}

