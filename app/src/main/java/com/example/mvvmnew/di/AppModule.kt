package com.example.mvvmnew.di

import android.app.Application
import androidx.room.Room
import com.example.mvvmnew.BuildConfig
import com.example.mvvmnew.database.UserDatabase
import com.example.mvvmnew.service.GithubService
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .create()

    @Singleton
    @Provides
    fun provideUserDatabase(application: Application): UserDatabase =
        Room.inMemoryDatabaseBuilder(application, UserDatabase::class.java)
            .build()

    @Provides
    @Singleton
    @Named("gitService")
    fun provideServiceAuth(@Named("client") retrofit: Retrofit): GithubService =
        retrofit.create(GithubService::class.java)

    @Provides
    @Singleton
    @Named("client")
    fun provideRetrofitAuthClient(
        gson: Gson,
        @Named("okhttp") okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    @Named("okhttp")
    fun provideClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .connectTimeout(BuildConfig.CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(BuildConfig.READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .addInterceptor(logging)
            .build()
    }
}