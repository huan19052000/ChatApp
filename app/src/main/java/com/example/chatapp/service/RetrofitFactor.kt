package com.example.chatapp.service

import com.google.gson.*
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat


object RetrofitFactor {

     fun createRetrofit(): ServiceAppCallApi {
        return Retrofit.Builder()
//            .baseUrl("https://huanbeo.herokuapp.com")
            .baseUrl("http:///192.168.16.107:8088")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ServiceAppCallApi::class.java)
    }

    fun createRetrofitToken(token : String) : ServiceAppCallApi {
        val httpClient = OkHttpClient.Builder()
        httpClient.networkInterceptors().add(Interceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            requestBuilder.header("Authorization", "Bearer $token")
            chain.proceed(requestBuilder.build())
        })
        return Retrofit.Builder()
//            .baseUrl("https://huanbeo.herokuapp.com")
            .baseUrl("http:///192.168.16.107:8088")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build().create(ServiceAppCallApi::class.java)
    }

    fun getGson(): Gson {
        val ser: JsonSerializer<Date> =
            JsonSerializer { src, typeOfSrc, context -> if (src == null) null else JsonPrimitive(src.time) }

        val deser: JsonDeserializer<Date> = object : JsonDeserializer<Date> {
            override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): Date? {
                if (json == null) {
                    return null
                }
                try {
                    return Date(json.asLong)
                } catch (e: Exception) {
                    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(json.asString)
                }
            }
        }
        return GsonBuilder()
            .registerTypeAdapter(Date::class.java, ser)
            .registerTypeAdapter(Date::class.java, deser).create()
    }
}