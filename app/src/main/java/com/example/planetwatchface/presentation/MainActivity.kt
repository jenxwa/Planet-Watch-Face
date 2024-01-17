package com.example.planetwatchface.presentation

import PlanetViewModel
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.planetwatchface.presentation.theme.PlanetWatchFaceTheme
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import data.Planet
import kotlinx.coroutines.coroutineScope
import kotlin.math.sqrt

// ...

class MainActivity : ComponentActivity() {
    private val planetViewModel by viewModels<PlanetViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanetWatchFaceTheme {
                val planets by planetViewModel.planetList.collectAsState()
                if (planets.isEmpty()) {
                    CenteredCircularProgressIndicator()
                } else {
                    SolarSystem(planets = planets)
                }
            }
        }
    }

    @Composable
    fun CenteredCircularProgressIndicator() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun SolarSystem(planets: List<Planet>) {
        var scrollPosition by remember { mutableFloatStateOf(0f) }
        val focusRequester = remember { FocusRequester() }
        // Retrieve the LocalFocusManager
        val focusManager = LocalFocusManager.current

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Canvas(modifier = Modifier
            .fillMaxSize()
            .onRotaryScrollEvent {
                Log.d("MainActivity", "onRotaryScrollEvent: $it")
                // Update the planets position based on the scroll event
                scrollPosition += it.verticalScrollPixels
                planetViewModel.incrementTime(scrollPosition.toInt())
                true
            }
            .focusRequester(focusRequester)
            .focusable()
        ) {
            val strokeWidth = 0.8.dp.toPx() // Stroke width for orbits
            val orbitColor = Color.White // Orbit color
            val numberOfOrbits = 9
            val initialOrbitOffset = 10.dp.toPx() // Initial offset for the first orbit

            // Calculate the spacing between orbits
            val maxOrbitDimension = size.maxDimension - 30.dp.toPx() // Adjust this value as needed
            val spacing = maxOrbitDimension / (numberOfOrbits * 2)

            // The sun:
            drawCircle(
                color = Color.Yellow,
                radius = 10.dp.toPx(),
            )

            for (i in 1..numberOfOrbits) {
                // Draw orbits
                val radius = spacing * i + initialOrbitOffset
                drawCircle(
                    color = orbitColor,
                    radius = radius,
                    style = Stroke(width = strokeWidth)
                )
            }

            // Draw planets
            planets.forEachIndexed { index, planet ->
                // Calculate the radius for this planet's orbit
                val orbitRadius = spacing * (index + 1) + initialOrbitOffset

                // Normalize the planet's position to a unit vector
                val direction = normalize(planet.position.x ?: 0f, planet.position.y ?: 0f)

                // Calculate the final position of the planet on its orbit
                val planetX = size.width / 2 + direction.x * orbitRadius
                val planetY = size.height / 2 + direction.y * orbitRadius

                drawCircle(
                    color = planet.color,
                    center = Offset(planetX, planetY),
                    radius = planet.scale * 5.dp.toPx() // Adjust planet size as needed
                )
                val planetRadius = planet.scale * 5.dp.toPx() // Adjust planet size as needed

                // Check if the planet is Saturn and draw the ring
                if (planet.name == "Saturn") {
                    val ringWidth = 2.dp.toPx()
                    val ringOuterRadius =
                        planetRadius - 2.dp.toPx() + 10.dp.toPx() // Adjust as needed for ring size
                    val ringColor = Color.Gray // Adjust as needed for ring color

                    // Draw the ring around Saturn
                    drawOval(
                        color = ringColor,
                        topLeft = Offset(
                            planetX - ringOuterRadius,
                            planetY - ringOuterRadius / 2
                        ), // Adjust for oval shape
                        size = Size(ringOuterRadius * 2, ringOuterRadius),
                        style = Stroke(width = ringWidth)
                    )
                }
            }
        }
    }

    // Normalize a vector to a unit vector
    private fun normalize(x: Float, y: Float): Offset {
        val length = sqrt(x * x + y * y)
        return if (length != 0f) {
            Offset(x / length, y / length)
        } else {
            Offset(0f, 0f)
        }
    }
}