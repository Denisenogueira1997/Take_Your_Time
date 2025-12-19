package com.example.aplicativotcc.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TarefasRepositorio @Inject constructor(
    private val dao: TarefaDao
) {

    fun recuperarTarefas(): Flow<List<TarefaEntity>> = dao.listarTarefas()

    fun recuperarTarefasFinalizadas(): Flow<List<TarefaEntity>> = dao.listarFinalizadas()

    fun recuperarPorId(id: Int): Flow<TarefaEntity> = dao.getById(id)

    suspend fun adicionar(
        titulo: String,
        descricao: String,
        dataInicial: String,
        dataFinal: String,
        duracao: String,
        prioridade: Int,
        tempoDiarioFixo: String,
        dataTempoDiario: String
    ) {
        dao.inserir(
            TarefaEntity(
                titulo = titulo,
                descricao = descricao,
                dataInicial = dataInicial,
                dataFinal = dataFinal,
                duracao = duracao,
                prioridade = prioridade,
                tempoDiarioFixo = tempoDiarioFixo,
                dataTempoDiario = dataTempoDiario
            )
        )
    }

    suspend fun atualizarTarefa(tarefa: TarefaEntity) {
        dao.atualizar(tarefa)
    }

    suspend fun excluir(tarefa: TarefaEntity) {
        dao.excluir(tarefa)
    }

    suspend fun finalizar(id: Int) {
        dao.finalizarTarefa(id)
    }

    suspend fun recuperarPorIdOnce(id: Int): TarefaEntity {
        return dao.getByIdOnce(id)
    }

}
