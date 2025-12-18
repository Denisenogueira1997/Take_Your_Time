package com.example.aplicativotcc.di

import android.app.Application
import androidx.room.Room
import com.example.aplicativotcc.data.AppDatabase
import com.example.aplicativotcc.data.TarefaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "db_tarefas"
        )
            .build()

    @Provides
    @Singleton
    fun provideTarefaDao(db: AppDatabase): TarefaDao =
        db.tarefaDao()
}
