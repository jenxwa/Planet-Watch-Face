package data

import ApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://ssd.jpl.nasa.gov/api/"
    private val lenientGson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(lenientGson)) // Use the custom Gson instance
        .build()


    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}
