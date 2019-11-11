package com.example.testapplication.dagger.modules

import android.util.Log
import com.example.testapplication.dagger.adapters.DataTimeAdapter
import com.example.testapplication.data.api.GitlabApiInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule(private val baseUrl: String) {

  @Provides
  @Singleton
  fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(httpLoggingInterceptor)
      .build()
  }

  @Provides
  @Singleton
  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
      Log.i("[NETWORK]", message)
    })
    interceptor.level = HttpLoggingInterceptor.Level.BASIC
    return interceptor
  }

  @Provides
  @Singleton
  fun providesGitlabGson(): Gson {
    return GsonBuilder()
      .registerTypeAdapter(DateTime::class.java, DataTimeAdapter())
      .create()
  }

  @Provides
  @Singleton
  fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .baseUrl(baseUrl)
      .build()
  }

  @Provides
  @Singleton
  fun providesGitlabApiInterface(retrofit: Retrofit): GitlabApiInterface =
    retrofit.create(GitlabApiInterface::class.java)
}