package se.umu.svke0008.havencompanion.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
fun rememberHotel(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "hotel",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(3.25f, 33.125f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.396f)
                reflectiveQuadToRelative(-0.375f, -0.937f)
                verticalLineToRelative(-21.5f)
                quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                quadToRelative(0.375f, -0.396f, 0.917f, -0.396f)
                quadToRelative(0.583f, 0f, 0.958f, 0.396f)
                reflectiveQuadToRelative(0.375f, 0.938f)
                verticalLineToRelative(14.916f)
                horizontalLineToRelative(14.209f)
                verticalLineTo(15.042f)
                quadToRelative(0f, -1.084f, 0.77f, -1.854f)
                quadToRelative(0.771f, -0.771f, 1.855f, -0.771f)
                horizontalLineToRelative(10.541f)
                quadToRelative(2.584f, 0f, 4.334f, 1.75f)
                quadToRelative(1.75f, 1.75f, 1.75f, 4.333f)
                verticalLineToRelative(13.292f)
                quadToRelative(0f, 0.541f, -0.375f, 0.937f)
                reflectiveQuadToRelative(-0.917f, 0.396f)
                quadToRelative(-0.583f, 0f, -0.958f, -0.396f)
                reflectiveQuadToRelative(-0.375f, -0.937f)
                verticalLineToRelative(-3.959f)
                horizontalLineTo(4.583f)
                verticalLineToRelative(3.959f)
                quadToRelative(0f, 0.541f, -0.395f, 0.937f)
                quadToRelative(-0.396f, 0.396f, -0.938f, 0.396f)
                close()
                moveToRelative(8.208f, -10.167f)
                quadToRelative(-1.875f, 0f, -3.166f, -1.291f)
                quadTo(7f, 20.375f, 7f, 18.5f)
                quadToRelative(0f, -1.875f, 1.292f, -3.167f)
                quadToRelative(1.291f, -1.291f, 3.166f, -1.291f)
                quadToRelative(1.917f, 0f, 3.209f, 1.291f)
                quadToRelative(1.291f, 1.292f, 1.291f, 3.167f)
                quadToRelative(0f, 1.875f, -1.291f, 3.167f)
                quadToRelative(-1.292f, 1.291f, -3.209f, 1.291f)
                close()
                moveToRelative(9.959f, 2.25f)
                horizontalLineToRelative(14f)
                verticalLineTo(18.5f)
                quadToRelative(0f, -1.375f, -1.042f, -2.417f)
                quadToRelative(-1.042f, -1.041f, -2.417f, -1.041f)
                horizontalLineTo(21.417f)
                close()
                moveToRelative(-9.959f, -4.875f)
                quadToRelative(0.792f, 0f, 1.313f, -0.541f)
                quadToRelative(0.521f, -0.542f, 0.521f, -1.292f)
                reflectiveQuadToRelative(-0.521f, -1.292f)
                quadToRelative(-0.521f, -0.541f, -1.313f, -0.541f)
                quadToRelative(-0.75f, 0f, -1.291f, 0.541f)
                quadToRelative(-0.542f, 0.542f, -0.542f, 1.292f)
                reflectiveQuadToRelative(0.542f, 1.292f)
                quadToRelative(0.541f, 0.541f, 1.291f, 0.541f)
                close()
                moveToRelative(0f, -1.833f)
                close()
                moveToRelative(9.959f, -3.458f)
                verticalLineToRelative(10.166f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberSkull(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "skull",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(10.083f, 36.583f)
                verticalLineToRelative(-7.041f)
                quadToRelative(-1.541f, -0.667f, -2.771f, -1.813f)
                quadToRelative(-1.229f, -1.146f, -2.104f, -2.625f)
                reflectiveQuadToRelative(-1.333f, -3.208f)
                quadToRelative(-0.458f, -1.729f, -0.458f, -3.563f)
                quadToRelative(0f, -6.541f, 4.645f, -10.75f)
                quadTo(12.708f, 3.375f, 20f, 3.375f)
                quadToRelative(7.292f, 0f, 11.958f, 4.208f)
                quadToRelative(4.667f, 4.209f, 4.667f, 10.75f)
                quadToRelative(0f, 1.834f, -0.479f, 3.563f)
                reflectiveQuadToRelative(-1.354f, 3.208f)
                quadToRelative(-0.875f, 1.479f, -2.104f, 2.625f)
                quadToRelative(-1.23f, 1.146f, -2.73f, 1.813f)
                verticalLineToRelative(7.041f)
                close()
                moveToRelative(2.625f, -2.625f)
                horizontalLineToRelative(2.667f)
                verticalLineToRelative(-3.916f)
                horizontalLineTo(18f)
                verticalLineToRelative(3.916f)
                horizontalLineToRelative(4.042f)
                verticalLineToRelative(-3.916f)
                horizontalLineToRelative(2.625f)
                verticalLineToRelative(3.916f)
                horizontalLineToRelative(2.625f)
                verticalLineToRelative(-6.083f)
                quadToRelative(1.541f, -0.458f, 2.791f, -1.354f)
                quadToRelative(1.25f, -0.896f, 2.105f, -2.146f)
                quadToRelative(0.854f, -1.25f, 1.312f, -2.792f)
                quadToRelative(0.458f, -1.541f, 0.458f, -3.25f)
                quadToRelative(0f, -5.5f, -3.854f, -8.895f)
                quadTo(26.25f, 6.042f, 20f, 6.042f)
                quadToRelative(-6.25f, 0f, -10.104f, 3.396f)
                quadToRelative(-3.854f, 3.395f, -3.854f, 8.895f)
                quadToRelative(0f, 1.709f, 0.458f, 3.25f)
                quadToRelative(0.458f, 1.542f, 1.312f, 2.792f)
                quadToRelative(0.855f, 1.25f, 2.105f, 2.146f)
                quadToRelative(1.25f, 0.896f, 2.791f, 1.354f)
                close()
                moveToRelative(4.917f, -9.041f)
                horizontalLineToRelative(4.75f)
                lineTo(20f, 20.125f)
                close()
                moveToRelative(-3.458f, -3.875f)
                quadToRelative(1.25f, 0f, 2.125f, -0.875f)
                reflectiveQuadToRelative(0.875f, -2.125f)
                quadToRelative(0f, -1.209f, -0.875f, -2.104f)
                quadToRelative(-0.875f, -0.896f, -2.125f, -0.896f)
                reflectiveQuadToRelative(-2.105f, 0.896f)
                quadToRelative(-0.854f, 0.895f, -0.854f, 2.104f)
                quadToRelative(0f, 1.25f, 0.875f, 2.125f)
                reflectiveQuadToRelative(2.084f, 0.875f)
                close()
                moveToRelative(11.666f, 0f)
                quadToRelative(1.25f, 0f, 2.125f, -0.875f)
                reflectiveQuadToRelative(0.875f, -2.125f)
                quadToRelative(0f, -1.209f, -0.875f, -2.104f)
                quadToRelative(-0.875f, -0.896f, -2.125f, -0.896f)
                reflectiveQuadToRelative(-2.104f, 0.896f)
                quadToRelative(-0.854f, 0.895f, -0.854f, 2.104f)
                quadToRelative(0f, 1.25f, 0.875f, 2.125f)
                reflectiveQuadToRelative(2.083f, 0.875f)
                close()
                moveTo(20f, 33.958f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberPersonAdd(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "person_add",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(31.542f, 23.25f)
                quadToRelative(-0.584f, 0f, -0.959f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.958f)
                verticalLineToRelative(-3.875f)
                horizontalLineToRelative(-3.916f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.396f)
                reflectiveQuadTo(25f, 16.708f)
                quadToRelative(0f, -0.541f, 0.375f, -0.937f)
                reflectiveQuadToRelative(0.917f, -0.396f)
                horizontalLineToRelative(3.916f)
                verticalLineToRelative(-3.917f)
                quadToRelative(0f, -0.5f, 0.375f, -0.895f)
                quadToRelative(0.375f, -0.396f, 0.959f, -0.396f)
                quadToRelative(0.541f, 0f, 0.916f, 0.396f)
                quadToRelative(0.375f, 0.395f, 0.375f, 0.895f)
                verticalLineToRelative(3.917f)
                horizontalLineToRelative(3.917f)
                quadToRelative(0.542f, 0f, 0.938f, 0.396f)
                quadToRelative(0.395f, 0.396f, 0.395f, 0.937f)
                quadToRelative(0f, 0.542f, -0.395f, 0.938f)
                quadToRelative(-0.396f, 0.396f, -0.938f, 0.396f)
                horizontalLineToRelative(-3.917f)
                verticalLineToRelative(3.875f)
                quadToRelative(0f, 0.583f, -0.375f, 0.958f)
                reflectiveQuadToRelative(-0.916f, 0.375f)
                close()
                moveTo(15f, 20f)
                quadToRelative(-2.708f, 0f, -4.458f, -1.771f)
                reflectiveQuadToRelative(-1.75f, -4.479f)
                quadToRelative(0f, -2.708f, 1.75f, -4.479f)
                reflectiveQuadToRelative(4.5f, -1.771f)
                quadToRelative(2.666f, 0f, 4.437f, 1.771f)
                quadToRelative(1.771f, 1.771f, 1.771f, 4.479f)
                reflectiveQuadToRelative(-1.771f, 4.479f)
                quadTo(17.708f, 20f, 15f, 20f)
                close()
                moveTo(3.25f, 32.958f)
                quadToRelative(-0.583f, 0f, -0.958f, -0.354f)
                reflectiveQuadToRelative(-0.375f, -0.937f)
                verticalLineToRelative(-2.709f)
                quadToRelative(0f, -1.375f, 0.729f, -2.541f)
                quadToRelative(0.729f, -1.167f, 2.021f, -1.75f)
                quadToRelative(2.916f, -1.334f, 5.354f, -1.917f)
                quadToRelative(2.437f, -0.583f, 4.979f, -0.583f)
                quadToRelative(2.542f, 0f, 5f, 0.583f)
                reflectiveQuadToRelative(5.333f, 1.917f)
                quadToRelative(1.292f, 0.625f, 2.042f, 1.75f)
                quadToRelative(0.75f, 1.125f, 0.75f, 2.541f)
                verticalLineToRelative(2.709f)
                quadToRelative(0f, 0.583f, -0.396f, 0.937f)
                quadToRelative(-0.396f, 0.354f, -0.937f, 0.354f)
                close()
                moveToRelative(1.333f, -2.625f)
                horizontalLineToRelative(20.875f)
                verticalLineToRelative(-1.375f)
                quadToRelative(0f, -0.583f, -0.291f, -1.125f)
                quadToRelative(-0.292f, -0.541f, -0.917f, -0.791f)
                quadToRelative(-2.708f, -1.292f, -4.792f, -1.75f)
                quadToRelative(-2.083f, -0.459f, -4.416f, -0.459f)
                quadToRelative(-2.375f, 0f, -4.48f, 0.459f)
                quadToRelative(-2.104f, 0.458f, -4.812f, 1.75f)
                quadToRelative(-0.542f, 0.25f, -0.854f, 0.791f)
                quadToRelative(-0.313f, 0.542f, -0.313f, 1.125f)
                close()
                moveToRelative(10.459f, -12.958f)
                quadToRelative(1.541f, 0f, 2.562f, -1.042f)
                quadToRelative(1.021f, -1.041f, 1.021f, -2.583f)
                quadToRelative(0f, -1.542f, -1.021f, -2.583f)
                quadToRelative(-1.021f, -1.042f, -2.562f, -1.042f)
                quadToRelative(-1.584f, 0f, -2.604f, 1.042f)
                quadToRelative(-1.021f, 1.041f, -1.021f, 2.583f)
                quadToRelative(0f, 1.542f, 1.021f, 2.583f)
                quadToRelative(1.02f, 1.042f, 2.604f, 1.042f)
                close()
                moveToRelative(0f, -3.625f)
                close()
                moveToRelative(0f, 11.083f)
                close()
            }
        }.build()
    }
}

fun personAdd(): ImageVector {
    val image = ImageVector.Builder(
        name = "person_add",
        defaultWidth = 40.0.dp,
        defaultHeight = 40.0.dp,
        viewportWidth = 40.0f,
        viewportHeight = 40.0f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1.0f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(31.542f, 23.25f)
            quadToRelative(-0.584f, 0f, -0.959f, -0.375f)
            reflectiveQuadToRelative(-0.375f, -0.958f)
            verticalLineToRelative(-3.875f)
            horizontalLineToRelative(-3.916f)
            quadToRelative(-0.542f, 0f, -0.917f, -0.396f)
            reflectiveQuadTo(25f, 16.708f)
            quadToRelative(0f, -0.541f, 0.375f, -0.937f)
            reflectiveQuadToRelative(0.917f, -0.396f)
            horizontalLineToRelative(3.916f)
            verticalLineToRelative(-3.917f)
            quadToRelative(0f, -0.5f, 0.375f, -0.895f)
            quadToRelative(0.375f, -0.396f, 0.959f, -0.396f)
            quadToRelative(0.541f, 0f, 0.916f, 0.396f)
            quadToRelative(0.375f, 0.395f, 0.375f, 0.895f)
            verticalLineToRelative(3.917f)
            horizontalLineToRelative(3.917f)
            quadToRelative(0.542f, 0f, 0.938f, 0.396f)
            quadToRelative(0.395f, 0.396f, 0.395f, 0.937f)
            quadToRelative(0f, 0.542f, -0.395f, 0.938f)
            quadToRelative(-0.396f, 0.396f, -0.938f, 0.396f)
            horizontalLineToRelative(-3.917f)
            verticalLineToRelative(3.875f)
            quadToRelative(0f, 0.583f, -0.375f, 0.958f)
            reflectiveQuadToRelative(-0.916f, 0.375f)
            close()
            moveTo(15f, 20f)
            quadToRelative(-2.708f, 0f, -4.458f, -1.771f)
            reflectiveQuadToRelative(-1.75f, -4.479f)
            quadToRelative(0f, -2.708f, 1.75f, -4.479f)
            reflectiveQuadToRelative(4.5f, -1.771f)
            quadToRelative(2.666f, 0f, 4.437f, 1.771f)
            quadToRelative(1.771f, 1.771f, 1.771f, 4.479f)
            reflectiveQuadToRelative(-1.771f, 4.479f)
            quadTo(17.708f, 20f, 15f, 20f)
            close()
            moveTo(3.25f, 32.958f)
            quadToRelative(-0.583f, 0f, -0.958f, -0.354f)
            reflectiveQuadToRelative(-0.375f, -0.937f)
            verticalLineToRelative(-2.709f)
            quadToRelative(0f, -1.375f, 0.729f, -2.541f)
            quadToRelative(0.729f, -1.167f, 2.021f, -1.75f)
            quadToRelative(2.916f, -1.334f, 5.354f, -1.917f)
            quadToRelative(2.437f, -0.583f, 4.979f, -0.583f)
            quadToRelative(2.542f, 0f, 5f, 0.583f)
            reflectiveQuadToRelative(5.333f, 1.917f)
            quadToRelative(1.292f, 0.625f, 2.042f, 1.75f)
            quadToRelative(0.75f, 1.125f, 0.75f, 2.541f)
            verticalLineToRelative(2.709f)
            quadToRelative(0f, 0.583f, -0.396f, 0.937f)
            quadToRelative(-0.396f, 0.354f, -0.937f, 0.354f)
            close()
            moveToRelative(1.333f, -2.625f)
            horizontalLineToRelative(20.875f)
            verticalLineToRelative(-1.375f)
            quadToRelative(0f, -0.583f, -0.291f, -1.125f)
            quadToRelative(-0.292f, -0.541f, -0.917f, -0.791f)
            quadToRelative(-2.708f, -1.292f, -4.792f, -1.75f)
            quadToRelative(-2.083f, -0.459f, -4.416f, -0.459f)
            quadToRelative(-2.375f, 0f, -4.48f, 0.459f)
            quadToRelative(-2.104f, 0.458f, -4.812f, 1.75f)
            quadToRelative(-0.542f, 0.25f, -0.854f, 0.791f)
            quadToRelative(-0.313f, 0.542f, -0.313f, 1.125f)
            close()
            moveToRelative(10.459f, -12.958f)
            quadToRelative(1.541f, 0f, 2.562f, -1.042f)
            quadToRelative(1.021f, -1.041f, 1.021f, -2.583f)
            quadToRelative(0f, -1.542f, -1.021f, -2.583f)
            quadToRelative(-1.021f, -1.042f, -2.562f, -1.042f)
            quadToRelative(-1.584f, 0f, -2.604f, 1.042f)
            quadToRelative(-1.021f, 1.041f, -1.021f, 2.583f)
            quadToRelative(0f, 1.542f, 1.021f, 2.583f)
            quadToRelative(1.02f, 1.042f, 2.604f, 1.042f)
            close()
            moveToRelative(0f, -3.625f)
            close()
            moveToRelative(0f, 11.083f)
            close()
        }
    }.build()
    return image
}

@Composable
fun rememberMic(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "mic",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20f, 22.542f)
                quadToRelative(-1.833f, 0f, -3.062f, -1.292f)
                quadToRelative(-1.23f, -1.292f, -1.23f, -3.167f)
                verticalLineTo(7.875f)
                quadToRelative(0f, -1.75f, 1.25f, -3f)
                reflectiveQuadTo(20f, 3.625f)
                quadToRelative(1.792f, 0f, 3.042f, 1.25f)
                quadToRelative(1.25f, 1.25f, 1.25f, 3f)
                verticalLineToRelative(10.208f)
                quadToRelative(0f, 1.875f, -1.23f, 3.167f)
                quadToRelative(-1.229f, 1.292f, -3.062f, 1.292f)
                close()
                moveToRelative(0f, -9.459f)
                close()
                moveToRelative(0f, 21.709f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.396f)
                reflectiveQuadToRelative(-0.375f, -0.938f)
                verticalLineToRelative(-4.166f)
                quadToRelative(-4f, -0.459f, -6.791f, -3.209f)
                quadToRelative(-2.792f, -2.75f, -3.25f, -6.583f)
                quadToRelative(-0.084f, -0.583f, 0.312f, -1f)
                quadToRelative(0.396f, -0.417f, 1.021f, -0.417f)
                quadToRelative(0.458f, 0f, 0.833f, 0.355f)
                quadToRelative(0.375f, 0.354f, 0.459f, 0.854f)
                quadToRelative(0.458f, 3.166f, 2.916f, 5.312f)
                quadTo(16.667f, 26.75f, 20f, 26.75f)
                quadToRelative(3.333f, 0f, 5.792f, -2.146f)
                quadToRelative(2.458f, -2.146f, 2.916f, -5.312f)
                quadToRelative(0.084f, -0.542f, 0.459f, -0.875f)
                quadToRelative(0.375f, -0.334f, 0.875f, -0.334f)
                quadToRelative(0.583f, 0f, 0.979f, 0.417f)
                reflectiveQuadToRelative(0.312f, 1f)
                quadToRelative(-0.458f, 3.833f, -3.25f, 6.583f)
                quadToRelative(-2.791f, 2.75f, -6.75f, 3.209f)
                verticalLineToRelative(4.166f)
                quadToRelative(0f, 0.542f, -0.395f, 0.938f)
                quadToRelative(-0.396f, 0.396f, -0.938f, 0.396f)
                close()
                moveToRelative(0f, -14.875f)
                quadToRelative(0.75f, 0f, 1.208f, -0.542f)
                quadToRelative(0.459f, -0.542f, 0.459f, -1.292f)
                verticalLineTo(7.917f)
                quadToRelative(0f, -0.709f, -0.479f, -1.188f)
                quadToRelative(-0.48f, -0.479f, -1.188f, -0.479f)
                reflectiveQuadToRelative(-1.188f, 0.479f)
                quadToRelative(-0.479f, 0.479f, -0.479f, 1.146f)
                verticalLineToRelative(10.208f)
                quadToRelative(0f, 0.75f, 0.459f, 1.292f)
                quadToRelative(0.458f, 0.542f, 1.208f, 0.542f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberMicOff(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "mic_off",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(28.667f, 24.833f)
                lineToRelative(-1.917f, -1.916f)
                quadToRelative(0.583f, -0.75f, 0.938f, -1.688f)
                quadToRelative(0.354f, -0.937f, 0.479f, -1.937f)
                quadToRelative(0.083f, -0.542f, 0.479f, -0.875f)
                quadToRelative(0.396f, -0.334f, 0.854f, -0.334f)
                quadToRelative(0.625f, 0f, 1f, 0.417f)
                reflectiveQuadToRelative(0.333f, 1f)
                quadToRelative(-0.208f, 1.458f, -0.75f, 2.812f)
                quadToRelative(-0.541f, 1.355f, -1.416f, 2.521f)
                close()
                moveTo(23.5f, 19.667f)
                lineToRelative(-2.375f, -2.334f)
                verticalLineTo(7.917f)
                quadToRelative(0f, -0.709f, -0.458f, -1.188f)
                quadToRelative(-0.459f, -0.479f, -1.209f, -0.479f)
                quadToRelative(-0.708f, 0f, -1.187f, 0.479f)
                quadToRelative(-0.479f, 0.479f, -0.479f, 1.188f)
                verticalLineToRelative(6.041f)
                lineToRelative(-2.625f, -2.625f)
                verticalLineTo(7.917f)
                quadToRelative(0f, -1.792f, 1.25f, -3.042f)
                quadToRelative(1.25f, -1.25f, 3.041f, -1.25f)
                quadToRelative(1.792f, 0f, 3.042f, 1.25f)
                quadToRelative(1.25f, 1.25f, 1.25f, 3.042f)
                verticalLineToRelative(10.166f)
                quadToRelative(0f, 0.334f, -0.062f, 0.792f)
                quadToRelative(-0.063f, 0.458f, -0.188f, 0.792f)
                close()
                moveToRelative(-4.125f, -4.125f)
                close()
                moveToRelative(13.708f, 20.916f)
                lineTo(2.875f, 6.292f)
                quadTo(2.5f, 5.917f, 2.5f, 5.396f)
                reflectiveQuadToRelative(0.375f, -0.854f)
                quadToRelative(0.375f, -0.375f, 0.875f, -0.375f)
                reflectiveQuadToRelative(0.833f, 0.375f)
                lineToRelative(30.25f, 30.208f)
                quadToRelative(0.375f, 0.375f, 0.375f, 0.875f)
                reflectiveQuadToRelative(-0.375f, 0.833f)
                quadToRelative(-0.375f, 0.375f, -0.895f, 0.375f)
                quadToRelative(-0.521f, 0f, -0.855f, -0.375f)
                close()
                moveToRelative(-14.958f, -3f)
                verticalLineToRelative(-4.166f)
                quadToRelative(-3.958f, -0.459f, -6.75f, -3.209f)
                reflectiveQuadTo(8.125f, 19.5f)
                quadToRelative(-0.083f, -0.583f, 0.313f, -1f)
                quadToRelative(0.395f, -0.417f, 1.02f, -0.417f)
                quadToRelative(0.459f, 0f, 0.834f, 0.334f)
                quadToRelative(0.375f, 0.333f, 0.458f, 0.875f)
                quadToRelative(0.458f, 3.208f, 2.917f, 5.333f)
                quadToRelative(2.458f, 2.125f, 5.791f, 2.125f)
                quadToRelative(1.5f, 0f, 2.917f, -0.5f)
                quadToRelative(1.417f, -0.5f, 2.583f, -1.375f)
                lineToRelative(1.875f, 1.875f)
                quadToRelative(-1.25f, 1.042f, -2.791f, 1.708f)
                quadToRelative(-1.542f, 0.667f, -3.25f, 0.834f)
                verticalLineToRelative(4.166f)
                quadToRelative(0f, 0.542f, -0.396f, 0.938f)
                quadToRelative(-0.396f, 0.396f, -0.938f, 0.396f)
                quadToRelative(-0.541f, 0f, -0.937f, -0.396f)
                reflectiveQuadToRelative(-0.396f, -0.938f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberEdit(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "edit",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(7.958f, 32.042f)
                horizontalLineToRelative(1.875f)
                lineTo(27f, 14.875f)
                lineTo(25.083f, 13f)
                lineTo(7.958f, 30.167f)
                close()
                moveTo(32.583f, 13f)
                lineToRelative(-5.625f, -5.583f)
                lineToRelative(1.875f, -1.875f)
                quadToRelative(0.75f, -0.75f, 1.875f, -0.75f)
                reflectiveQuadToRelative(1.875f, 0.791f)
                lineToRelative(1.875f, 1.875f)
                quadToRelative(0.75f, 0.75f, 0.75f, 1.855f)
                quadToRelative(0f, 1.104f, -0.75f, 1.854f)
                close()
                moveTo(6.625f, 34.667f)
                quadToRelative(-0.583f, 0f, -0.958f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.917f)
                verticalLineToRelative(-3.792f)
                quadToRelative(0f, -0.25f, 0.104f, -0.479f)
                quadToRelative(0.104f, -0.229f, 0.312f, -0.437f)
                lineTo(25.125f, 9.25f)
                lineToRelative(5.625f, 5.625f)
                lineToRelative(-19.458f, 19.417f)
                quadToRelative(-0.167f, 0.208f, -0.417f, 0.291f)
                quadToRelative(-0.25f, 0.084f, -0.5f, 0.084f)
                close()
                moveToRelative(19.417f, -20.75f)
                lineTo(25.083f, 13f)
                lineTo(27f, 14.875f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberKeyboardArrowRight(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "keyboard_arrow_right",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(14.75f, 28.875f)
                quadToRelative(-0.375f, -0.417f, -0.396f, -0.937f)
                quadToRelative(-0.021f, -0.521f, 0.396f, -0.938f)
                lineToRelative(7.042f, -7.042f)
                lineToRelative(-7.084f, -7.041f)
                quadToRelative(-0.375f, -0.375f, -0.354f, -0.938f)
                quadToRelative(0.021f, -0.562f, 0.396f, -0.937f)
                quadToRelative(0.417f, -0.417f, 0.938f, -0.417f)
                quadToRelative(0.52f, 0f, 0.895f, 0.417f)
                lineToRelative(8.042f, 8f)
                quadToRelative(0.208f, 0.208f, 0.292f, 0.437f)
                quadToRelative(0.083f, 0.229f, 0.083f, 0.479f)
                quadToRelative(0f, 0.292f, -0.083f, 0.5f)
                quadToRelative(-0.084f, 0.209f, -0.292f, 0.417f)
                lineToRelative(-8f, 8f)
                quadToRelative(-0.417f, 0.417f, -0.937f, 0.396f)
                quadToRelative(-0.521f, -0.021f, -0.938f, -0.396f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberKeyboardArrowLeft(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "keyboard_arrow_left",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(22.375f, 28.875f)
                lineToRelative(-8f, -8f)
                quadToRelative(-0.208f, -0.208f, -0.292f, -0.417f)
                quadToRelative(-0.083f, -0.208f, -0.083f, -0.5f)
                quadToRelative(0f, -0.25f, 0.083f, -0.479f)
                quadToRelative(0.084f, -0.229f, 0.292f, -0.437f)
                lineToRelative(8.042f, -8f)
                quadToRelative(0.375f, -0.417f, 0.916f, -0.417f)
                quadToRelative(0.542f, 0f, 0.959f, 0.417f)
                quadToRelative(0.375f, 0.375f, 0.354f, 0.937f)
                quadToRelative(-0.021f, 0.563f, -0.396f, 0.979f)
                lineToRelative(-7.042f, 7f)
                lineToRelative(7.084f, 7.084f)
                quadToRelative(0.375f, 0.375f, 0.375f, 0.916f)
                quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                quadToRelative(-0.417f, 0.417f, -0.959f, 0.417f)
                quadToRelative(-0.541f, 0f, -0.958f, -0.417f)
                close()
            }
        }.build()
    }
}