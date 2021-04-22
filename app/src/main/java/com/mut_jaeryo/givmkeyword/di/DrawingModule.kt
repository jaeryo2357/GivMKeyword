package com.mut_jaeryo.givmkeyword.di

import com.mut_jaeryo.data.api.firebase.FirebaseService
import com.mut_jaeryo.data.api.firebase.FirebaseDrawingServiceImpl
import com.mut_jaeryo.data.source.drawing.DrawingDataSource
import com.mut_jaeryo.data.source.drawing.local.LocalDrawingDataSourceImpl
import com.mut_jaeryo.data.source.drawing.remote.RemoteDrawingDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class DrawingModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteLabel

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalLabel

    @Binds
    abstract fun bindDrawingService(
            firebaseDrawingServiceImpl: FirebaseDrawingServiceImpl
    ): FirebaseService

    @RemoteLabel
    @Binds
    abstract fun bindRemoteDrawingDataSource(
            remoteDrawingDataSourceImpl: RemoteDrawingDataSourceImpl
    ): DrawingDataSource

    @LocalLabel
    @Binds
    abstract fun bindLocalDrawingDataSource(
            localDrawingDataSourceImpl: LocalDrawingDataSourceImpl
    ): DrawingDataSource
}