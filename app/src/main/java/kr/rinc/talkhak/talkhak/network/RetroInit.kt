package kr.rinc.talkhak.talkhak.network

import android.annotation.SuppressLint
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


@SuppressLint("StaticFieldLeak")
object RetroInit {
    val networkList: NetworkList
    val SERVER_URL: String = "http://appjam14.rinc.kr"

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addNetworkInterceptor(interceptor).build()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        networkList = retrofit.create(NetworkList::class.java)
    }
}