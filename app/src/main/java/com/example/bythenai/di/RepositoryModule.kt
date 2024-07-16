package com.example.bythenai.di

import com.example.bythenai.repository.IUploadRepository
import com.example.bythenai.repository.UploadRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [ServiceModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideUploadRepository(repository: UploadRepository): IUploadRepository

}