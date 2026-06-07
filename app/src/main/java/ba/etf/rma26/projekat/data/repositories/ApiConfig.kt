package ba.etf.rma26.projekat.data.repositories

import ba.etf.rma26.projekat.data.KvizApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ApiConfig {
    private var baseUrl = "http://192.168.0.4:3000/"
    private var apiKey: String? = null
    private var _retrofit: Retrofit? = null

    fun postaviBaseURL(url: String) {
        baseUrl = if (url.endsWith("/")) url else "$url/"
        _retrofit = null
    }

    fun postaviApiKey(key: String?) {
        apiKey = key
        _retrofit = null
    }

    fun getApiKey(): String? = apiKey

    private fun getRetrofit(): Retrofit {
        if (_retrofit == null) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val gson = GsonBuilder()
                .registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer { json, _, _ ->
                    val str = json.asString
                    try {
                        LocalDateTime.parse(str, DateTimeFormatter.ISO_DATE_TIME)
                    } catch (e: Exception) {
                        null
                    }
                })
                .create()

            _retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return _retrofit!!
    }

    fun getApi(): KvizApi = getRetrofit().create(KvizApi::class.java)
}