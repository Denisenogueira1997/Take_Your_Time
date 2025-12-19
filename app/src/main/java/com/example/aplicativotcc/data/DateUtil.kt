package com.example.aplicativotcc.data

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@SuppressLint("NewApi")
class DateUtil {

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun calcularNovaDuracao(
        tarefa: TarefaEntity,
        minutosTrabalhados: Int
    ): String {

        val duracaoAtual = tarefa.duracao.ifBlank { "00:00" }
        val tempoAtual = toLocalTime(duracaoAtual)

        val totalMinutosAtual =
            tempoAtual.hour * 60 + tempoAtual.minute

        val minutosRestantes =
            (totalMinutosAtual - minutosTrabalhados)
                .coerceAtLeast(0)

        val horas = minutosRestantes / 60
        val minutos = minutosRestantes % 60

        return createFormattedTime(horas, minutos)
    }


    fun calcularTempoDiario(tarefa: TarefaEntity): String {

        if (tarefa.finalizada) {
            return "00:00"
        }

        if (tarefa.dataInicial.isBlank() || tarefa.dataFinal.isBlank()) {
            return tarefa.duracao.ifBlank { "00:00" }
        }

        val hoje = LocalDate.now()

        val dataFinal = try {
            toLocalDate(tarefa.dataFinal)
        } catch (e: Exception) {
            return tarefa.duracao.ifBlank { "00:00" }
        }

        if (dataFinal.isBefore(hoje)) {
            return tarefa.duracao.ifBlank { "00:00" }
        }

        val dataInicial = getDataInicial(tarefa)

        val diasTotais = ChronoUnit.DAYS.between(dataInicial, dataFinal) + 1
        if (diasTotais <= 0) {
            return tarefa.duracao.ifBlank { "00:00" }
        }

        val duracao = tarefa.duracao.ifBlank { "00:00" }
        val duracaoSegundos = toLocalTime(duracao).toSecondOfDay()
        if (duracaoSegundos <= 0) {
            return "00:00"
        }

        val segundosPorDia = duracaoSegundos / diasTotais
        if (segundosPorDia <= 0) {
            return "00:00"
        }

        return LocalTime
            .ofSecondOfDay(segundosPorDia)
            .format(timeFormatter)
    }


    fun createFormattedDate(day: Int, month: Int, year: Int): String =
        LocalDate.of(year, month, day).format(dateFormatter)

    fun createFormattedTime(hour: Int, minute: Int): String =
        LocalTime.of(hour, minute).format(timeFormatter)

    private fun toLocalDate(date: String): LocalDate =
        LocalDate.parse(date, dateFormatter)

    private fun toLocalTime(time: String): LocalTime =
        LocalTime.parse(time, timeFormatter)

    private fun getDataInicial(tarefa: TarefaEntity): LocalDate {
        val dataInicial = toLocalDate(tarefa.dataInicial)
        val hoje = LocalDate.now()
        return if (dataInicial.isAfter(hoje)) dataInicial else hoje
    }


    fun validarIntervaloDatas(dataInicial: String, dataFinal: String): Boolean {
        return try {
            val inicio = toLocalDate(dataInicial)
            val fim = toLocalDate(dataFinal)
            !fim.isBefore(inicio)
        } catch (e: Exception) {
            false
        }
    }

    fun subtrairDoTempoDiario(
        tarefa: TarefaEntity,
        minutosTrabalhados: Int
    ): String {

        val tempoAtual = tarefa.tempoDiarioFixo.ifBlank { "00:00" }
        val localTime = toLocalTime(tempoAtual)

        val totalMinutos =
            localTime.hour * 60 + localTime.minute

        val restante =
            (totalMinutos - minutosTrabalhados)
                .coerceAtLeast(0)

        val horas = restante / 60
        val minutos = restante % 60

        return createFormattedTime(horas, minutos)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calcularTempoDiarioInicial(
        dataInicial: String,
        dataFinal: String,
        duracao: String
    ): String {

        if (dataInicial.isBlank() || dataFinal.isBlank()) return "00:00"
        if (duracao.isBlank()) return "00:00"

        val hoje = LocalDate.now()

        val inicio = try {
            LocalDate.parse(dataInicial, dateFormatter)
        } catch (e: Exception) {
            return "00:00"
        }

        val fim = try {
            LocalDate.parse(dataFinal, dateFormatter)
        } catch (e: Exception) {
            return "00:00"
        }

        if (fim.isBefore(hoje)) return "00:00"

        val dataInicioCalculo =
            if (inicio.isAfter(hoje)) inicio else hoje

        val diasTotais =
            ChronoUnit.DAYS.between(dataInicioCalculo, fim) + 1

        if (diasTotais <= 0) return "00:00"

        val segundosTotais =
            LocalTime.parse(duracao, timeFormatter).toSecondOfDay()

        val segundosPorDia = segundosTotais / diasTotais

        if (segundosPorDia <= 0) return "00:00"

        return LocalTime
            .ofSecondOfDay(segundosPorDia)
            .format(timeFormatter)
    }


}
