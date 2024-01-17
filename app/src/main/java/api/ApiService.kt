// ApiService.kt
import data.HorizonResponse
import data.PlanetaryPosition
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Query

interface ApiService {
    @GET("horizons.api")
    suspend fun getHorizons(
        @Query("format") format: String = "json",
        @Query("COMMAND") command: String,
        @Query("MAKE_EPHEM") makeEphem: String = "YES",
        @Query("EPHEM_TYPE") ephemType: String = "VECTORS",
        @Query("CENTER") center: String = "'@sun'",
        @Query("START_TIME") startTime: String = "now",  // Adjust to get current time
        @Query("STOP_TIME") stopTime: String = "now",   // Adjust to get current time
        @Query("STEP_SIZE") stepSize: String = "1d"
    ): Response<HorizonResponse>
}
