import android.media.Image.Plane
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planetwatchface.presentation.theme.earthColor
import com.example.planetwatchface.presentation.theme.jupiterColor
import com.example.planetwatchface.presentation.theme.marsColor
import com.example.planetwatchface.presentation.theme.mercuryColor
import com.example.planetwatchface.presentation.theme.neptuneColor
import com.example.planetwatchface.presentation.theme.plutoColor
import com.example.planetwatchface.presentation.theme.saturnColor
import com.example.planetwatchface.presentation.theme.uranusColor
import com.example.planetwatchface.presentation.theme.venusColor
import data.Planet
import data.PlanetVelocity
import data.PlanetaryPosition
import data.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.sqrt

class PlanetViewModel : ViewModel() {
    private val _earthPosition = MutableStateFlow<PlanetaryPosition?>(null)
    private val _mercuryPosition = MutableStateFlow<PlanetaryPosition?>(null)
    private val _venusPosition = MutableStateFlow<PlanetaryPosition?>(null)
    private val _marsPosition = MutableStateFlow<PlanetaryPosition?>(null)
    private val _jupiterPosition = MutableStateFlow<PlanetaryPosition?>(null)
    private val _saturnPosition = MutableStateFlow<PlanetaryPosition?>(null)
    private val _uranusPosition = MutableStateFlow<PlanetaryPosition?>(null)
    private val _neptunePosition = MutableStateFlow<PlanetaryPosition?>(null)
    private val _plutoPosition = MutableStateFlow<PlanetaryPosition?>(null)

    private val _earthVelocity = MutableStateFlow<PlanetVelocity?>(null)
    private val _mercuryVelocity = MutableStateFlow<PlanetVelocity?>(null)
    private val _venusVelocity = MutableStateFlow<PlanetVelocity?>(null)
    private val _marsVelocity = MutableStateFlow<PlanetVelocity?>(null)
    private val _jupiterVelocity = MutableStateFlow<PlanetVelocity?>(null)
    private val _saturnVelocity = MutableStateFlow<PlanetVelocity?>(null)
    private val _uranusVelocity = MutableStateFlow<PlanetVelocity?>(null)
    private val _neptuneVelocity = MutableStateFlow<PlanetVelocity?>(null)
    private val _plutoVelocity = MutableStateFlow<PlanetVelocity?>(null)


    private val _planetList = MutableStateFlow<List<Planet>>(emptyList())
    val planetList: StateFlow<List<Planet>> = _planetList

    private val earthPosition: StateFlow<PlanetaryPosition?> = _earthPosition
    private val mercuryPosition: StateFlow<PlanetaryPosition?> = _mercuryPosition
    private val venusPosition: StateFlow<PlanetaryPosition?> = _venusPosition
    private val marsPosition: StateFlow<PlanetaryPosition?> = _marsPosition
    private val jupiterPosition: StateFlow<PlanetaryPosition?> = _jupiterPosition
    private val saturnPosition: StateFlow<PlanetaryPosition?> = _saturnPosition
    private val uranusPosition: StateFlow<PlanetaryPosition?> = _uranusPosition
    private val neptunePosition: StateFlow<PlanetaryPosition?> = _neptunePosition
    private val plutoPosition: StateFlow<PlanetaryPosition?> = _plutoPosition

    private val earthVelocity: StateFlow<PlanetVelocity?> = _earthVelocity
    private val mercuryVelocity: StateFlow<PlanetVelocity?> = _mercuryVelocity
    private val venusVelocity: StateFlow<PlanetVelocity?> = _venusVelocity
    private val marsVelocity: StateFlow<PlanetVelocity?> = _marsVelocity
    private val jupiterVelocity: StateFlow<PlanetVelocity?> = _jupiterVelocity
    private val saturnVelocity: StateFlow<PlanetVelocity?> = _saturnVelocity
    private val uranusVelocity: StateFlow<PlanetVelocity?> = _uranusVelocity
    private val neptuneVelocity: StateFlow<PlanetVelocity?> = _neptuneVelocity
    private val plutoVelocity: StateFlow<PlanetVelocity?> = _plutoVelocity

    private val apiService = RetrofitClient.apiService

    private fun getFormattedDate(daysAgo: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private val startTime = getFormattedDate(1) // One day ago
    private val stopTime = getFormattedDate(0) // Today

    init {
        fetchPlanetaryPositions()
    }

    private var timeIncrement = 0 // Days

    fun incrementTime(days: Int) {
        timeIncrement += days
        updatePlanetPositions()
    }

    private fun updatePlanetPositions() {
        _planetList.value = _planetList.value.map { planet ->
            val timeIncrementFloat = timeIncrement.toFloat() / 10
            Log.e("Jenny", "TimeIncrement: $timeIncrementFloat")
            Log.e("Jenny", "Old Position: ${planet.position}")

            val defaultPosition = 0f  // Default value in case of null
            val newX = (planet.position.x ?: defaultPosition) + (planet.velocity.x ?: 0f) * timeIncrementFloat * 10000000
            val newY = (planet.position.y ?: defaultPosition) + (planet.velocity.y ?: 0f) * timeIncrementFloat * 10000000

            val result = planet.copy(position = planet.position.copy(
                x = newX,
                y = newY,
            ))

            Log.e("Jenny", "New Position: ${result.position.x}, ${result.position.y}")

            result
        }
    }




    private fun fetchPlanetaryPositions() {
        viewModelScope.launch {
            try {
                Log.e("Jenny", "Fetching Planetary Positions")
                val newPlanets = mutableListOf<Planet>()
                val responseMercury = apiService.getHorizons(
                    command = "'199'",
                    startTime = startTime,
                    stopTime = stopTime
                )
                Log.e("Jenny", "Mercury Response: ${responseMercury.body()}")
                val responseVenus = apiService.getHorizons(
                    command = "'299'",
                    startTime = startTime, stopTime = stopTime
                )  // Venus
                val responseEarth = apiService.getHorizons(
                    command = "'399'",
                    startTime = startTime,
                    stopTime = stopTime
                )  // Earth
                val responseMars = apiService.getHorizons(
                    command = "'499'",
                    startTime = startTime,
                    stopTime = stopTime
                )  // Mars
                val responseJupiter = apiService.getHorizons(
                    command = "'599'",
                    startTime = startTime,
                    stopTime = stopTime
                )  // Jupiter
                val responseSaturn = apiService.getHorizons(
                    command = "'699'",
                    startTime = startTime,
                    stopTime = stopTime
                )  // Saturn
                val responseUranus = apiService.getHorizons(
                    command = "'799'",
                    startTime = startTime,
                    stopTime = stopTime
                )  // Uranus
                val responseNeptune = apiService.getHorizons(
                    command = "'899'",
                    startTime = startTime,
                    stopTime = stopTime
                )  // Neptune
                val responsePluto = apiService.getHorizons(
                    command = "'999'",
                    startTime = startTime,
                    stopTime = stopTime
                )  // Pluto

                _mercuryPosition.value =
                    parsePlanetaryPosition(responseMercury.body()?.toString() ?: "")
                _venusPosition.value =
                    parsePlanetaryPosition(responseVenus.body()?.toString() ?: "")
                _earthPosition.value =
                    parsePlanetaryPosition(responseEarth.body()?.toString() ?: "")
                _marsPosition.value = parsePlanetaryPosition(responseMars.body()?.toString() ?: "")
                _jupiterPosition.value =
                    parsePlanetaryPosition(responseJupiter.body()?.toString() ?: "")
                _saturnPosition.value =
                    parsePlanetaryPosition(responseSaturn.body()?.toString() ?: "")
                _uranusPosition.value =
                    parsePlanetaryPosition(responseUranus.body()?.toString() ?: "")
                _neptunePosition.value =
                    parsePlanetaryPosition(responseNeptune.body()?.toString() ?: "")
                _plutoPosition.value =
                    parsePlanetaryPosition(responsePluto.body()?.toString() ?: "")

                _mercuryVelocity.value =
                    parsePlanetaryVelocity(responseMercury.body()?.toString() ?: "")
                _venusVelocity.value =
                    parsePlanetaryVelocity(responseVenus.body()?.toString() ?: "")
                _earthVelocity.value =
                    parsePlanetaryVelocity(responseEarth.body()?.toString() ?: "")
                _marsVelocity.value = parsePlanetaryVelocity(responseMars.body()?.toString() ?: "")
                _jupiterVelocity.value =
                    parsePlanetaryVelocity(responseJupiter.body()?.toString() ?: "")
                _saturnVelocity.value =
                    parsePlanetaryVelocity(responseSaturn.body()?.toString() ?: "")
                _uranusVelocity.value =
                    parsePlanetaryVelocity(responseUranus.body()?.toString() ?: "")
                _neptuneVelocity.value =
                    parsePlanetaryVelocity(responseNeptune.body()?.toString() ?: "")
                _plutoVelocity.value =
                    parsePlanetaryVelocity(responsePluto.body()?.toString() ?: "")

                newPlanets.add(
                    Planet(
                        "Mercury",
                        position = mercuryPosition.value!!,
                        scale = 0.8f,
                        color = mercuryColor,
                        velocity = mercuryVelocity.value!!
                    )
                )
                newPlanets.add(
                    Planet(
                        "Venus",
                        position = venusPosition.value!!,
                        scale = 0.9f,
                        color = venusColor,
                        velocity = venusVelocity.value!!
                    )
                )
                newPlanets.add(
                    Planet(
                        "Earth",
                        position = earthPosition.value!!,
                        scale = 1f,
                        color = earthColor,
                        velocity = earthVelocity.value!!
                    )
                )
                newPlanets.add(
                    Planet(
                        "Mars",
                        position = marsPosition.value!!,
                        scale = 1f,
                        color = marsColor,
                        velocity = marsVelocity.value!!
                    )
                )
                newPlanets.add(
                    Planet(
                        "Jupiter",
                        position = jupiterPosition.value!!,
                        scale = 2.2f,
                        color = jupiterColor,
                        velocity = jupiterVelocity.value!!
                    )
                )
                newPlanets.add(
                    Planet(
                        "Saturn",
                        position = saturnPosition.value!!,
                        scale = 1.5f,
                        color = saturnColor,
                        velocity = saturnVelocity.value!!
                    )
                )
                newPlanets.add(
                    Planet(
                        "Uranus",
                        position = uranusPosition.value!!,
                        scale = 1.1f,
                        color = uranusColor,
                        velocity = uranusVelocity.value!!
                    )
                )
                newPlanets.add(
                    Planet(
                        "Neptune",
                        position = neptunePosition.value!!,
                        scale = 1.1f,
                        color = neptuneColor,
                        velocity = neptuneVelocity.value!!
                    )
                )
                newPlanets.add(
                    Planet(
                        "Pluto",
                        position = plutoPosition.value!!,
                        scale = 0.6f,
                        color = plutoColor,
                        velocity = plutoVelocity.value!!
                    )
                )

                _planetList.value = newPlanets
                Log.e(
                    "Jenny", "All planet positions: \n " +
                            "Mercury: ${_mercuryPosition.value} \n" +
                            "Venus: ${_venusPosition.value} \n" +
                            "Earth: ${_earthPosition.value} \n" +
                            "Mars: ${_marsPosition.value} \n" +
                            "Jupiter: ${_jupiterPosition.value} \n" +
                            "Saturn: ${_saturnPosition.value} \n" +
                            "Uranus: ${_uranusPosition.value} \n" +
                            "Neptune: ${_neptunePosition.value} \n" +
                            "Pluto: ${_plutoPosition.value} \n"
                )

            } catch (e: Exception) {
                Log.e("Jenny", "PlanetError: $e")
            }
        }
    }

    private fun parsePlanetaryPosition(resultString: String): PlanetaryPosition {
        val ephemerisDataStartIndex = resultString.indexOf("\$\$SOE")
        val ephemerisDataEndIndex = resultString.indexOf("\$\$EOE", ephemerisDataStartIndex)

        val ephemerisData =
            resultString.substring(ephemerisDataStartIndex + 6, ephemerisDataEndIndex).trim()
        Log.e("Jenny", "EphemerisData: $ephemerisData")

        if (ephemerisData.isEmpty()) {
            return PlanetaryPosition(0f, 0f, 0f)
        }

        val xPattern = """X =(\s?-?\d+\.\d+E[+-]?\d+)""".toRegex()
        val yPattern = """Y =(\s?-?\d+\.\d+E[+-]?\d+)""".toRegex()
        val zPattern = """Z =(\s?-?\d+\.\d+E[+-]?\d+)""".toRegex()

        val x = xPattern.find(ephemerisData)?.groups?.get(1)?.value?.toFloat()
        val y = yPattern.find(ephemerisData)?.groups?.get(1)?.value?.toFloat()
        val z = zPattern.find(ephemerisData)?.groups?.get(1)?.value?.toFloat()

        val result = PlanetaryPosition(x, y, z)
        Log.e("Jenny", "Position result: $result")
        return result
    }

    private fun parsePlanetaryVelocity(resultString: String): PlanetVelocity {
        val ephemerisDataStartIndex = resultString.indexOf("\$\$SOE")
        val ephemerisDataEndIndex = resultString.indexOf("\$\$EOE", ephemerisDataStartIndex)

        val ephemerisData =
            resultString.substring(ephemerisDataStartIndex + 6, ephemerisDataEndIndex).trim()
        Log.e("Jenny", "EphemerisData: $ephemerisData")

        if (ephemerisData.isEmpty()) {
            return PlanetVelocity(0f, 0f, 0f)
        }

        val xPattern = """VX=(\s?-?\d+\.\d+E[+-]?\d+)""".toRegex()
        val yPattern = """VY=(\s?-?\d+\.\d+E[+-]?\d+)""".toRegex()
        val zPattern = """VZ=(\s?-?\d+\.\d+E[+-]?\d+)""".toRegex()

        val x = xPattern.find(ephemerisData)?.groups?.get(1)?.value?.toFloat()
        val y = yPattern.find(ephemerisData)?.groups?.get(1)?.value?.toFloat()
        val z = zPattern.find(ephemerisData)?.groups?.get(1)?.value?.toFloat()

        val result = PlanetVelocity(x, y, z)
        Log.e("Jenny", "Velocity result: ${result.x}, ${result.y}, ${result.z}")
        return result
    }

    fun calculateVelocityMagnitude(vx: Double, vy: Double, vz: Double): Double {
        return sqrt(vx * vx + vy * vy + vz * vz)
    }
}
