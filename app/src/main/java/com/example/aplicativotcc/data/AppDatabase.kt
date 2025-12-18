package com.example.aplicativotcc.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [TarefaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tarefaDao(): TarefaDao
}

