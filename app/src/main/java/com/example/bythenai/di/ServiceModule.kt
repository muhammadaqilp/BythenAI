package com.example.bythenai.di

import com.example.bythenai.network.service.UploadApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun providesUploadApi(retrofit: Retrofit): UploadApi {
        return retrofit.create(UploadApi::class.java)
    }

}