package com.example.aplicativotcc.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TarefaDao {

    @Query("SELECT * FROM tarefas WHERE finalizada = 0 ORDER BY id DESC")
    fun listarTarefas(): Flow<List<TarefaEntity>>

    @Query("SELECT * FROM tarefas WHERE finalizada = 1")
    fun listarFinalizadas(): Flow<List<TarefaEntity>>

    @Query("SELECT * FROM tarefas WHERE id = :id LIMIT 1")
    fun getById(id: Int): Flow<TarefaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(tarefa: TarefaEntity)

    @Update
    suspend fun atualizar(tarefa: TarefaEntity)

    @Delete
    suspend fun excluir(tarefa: TarefaEntity)

    @Query("UPDATE tarefas SET finalizada = 1 WHERE id = :id")
    suspend fun finalizarTarefa(id: Int)

    @Query("SELECT * FROM tarefas WHERE id = :id LIMIT 1")
    suspend fun getByIdOnce(id: Int): TarefaEntity
}
