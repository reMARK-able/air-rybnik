package com.gmail.remarkable.development.airrybnik.di

import android.content.Context
import androidx.room.Room
import com.gmail.remarkable.development.airrybnik.database.SensorDatabase
import com.gmail.remarkable.development.airrybnik.network.GiosApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGiosApiService(moshi: Moshi): GiosApiService {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("http://api.gios.gov.pl/pjp-api/rest/")
            .build()
            .create(GiosApiService::class.java)
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SensorDatabase {
        return Room.databaseBuilder(
            context,
            SensorDatabase::class.java,
            "Sensor.db"
        ).build()
    }
}