package data

import android.health.connect.datatypes.units.Velocity
import androidx.compose.ui.graphics.Color

data class Planet(
    val name: String,
    val position: PlanetaryPosition,
    val scale: Float = 1f,
    val color: Color = Color.White,
    val velocity: PlanetVelocity
)