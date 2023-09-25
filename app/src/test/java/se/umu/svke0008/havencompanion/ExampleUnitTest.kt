package se.umu.svke0008.havencompanion

import android.graphics.Color
import android.util.Log
import androidx.core.graphics.toColor
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun colors_is_parable() {
        val colorString = "#DDc8a56e"
        val color = Color.parseColor(colorString)
        Log.e("colors_is_parable", color.toString() )
    }
}