package com.jarvis.stormreminder.network.`interface`

import android.util.Log
import com.jarvis.stormreminder.model.Weather
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MasterService {
    @GET("/weatherAPI/opendata/weather.php")
    fun getDetail(
        @Query("dataType") dataType: String,
        @Query("lang") language: String
    ): Observable<Weather>

    companion object Factory {
        private val TIMEOUT: Long = 5

        private val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                    Log.d(
                        "OkHttp",
                        "log: $message"
                    )
                }).setLevel(
                    HttpLoggingInterceptor.Level.BASIC
                )
            )
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()

        fun retrofitService(): MasterService = Retrofit.Builder()
            .baseUrl("https://data.weather.gov.hk/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(MasterService::class.java)
    }
}