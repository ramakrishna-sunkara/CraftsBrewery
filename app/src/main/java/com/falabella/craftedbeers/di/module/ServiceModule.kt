package com.falabella.craftedbeers.di.module

import android.content.Context


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.falabella.craftedbeers.BuildConfig
import com.falabella.craftedbeers.data.remote.ApiCallInterface

import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Ramakrishna Sunkara on 22-05-2019
 */

@Module
class ServiceModule(private val context: Context) {

    internal val requestHeader: OkHttpClient
        @Provides
        @Singleton
        get() {

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                        .build()
                chain.proceed(request)
            }.connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)

            return httpClient.build()
        }

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    internal fun provideApiCallInterface(retrofit: Retrofit): ApiCallInterface {
        return retrofit.create(ApiCallInterface::class.java)
    }
}
