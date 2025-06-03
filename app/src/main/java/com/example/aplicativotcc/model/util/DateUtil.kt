package com.example.aplicativotcc.model.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.aplicativotcc.model.Tarefa
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("NewApi")
class DateUtil {

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun calcularNovaDuracao(tarefa: Tarefa, tempoDedicado: LocalTime): String? {
        val duration = tarefa.duracao?.let { toLocalTime(it) }

        if(tempoDedicado.isAfter(duration)) {
            return createFormattedTime(0, 0)
        }

        return duration?.minusHours(tempoDedicado.hour.toLong())
            ?.minusMinutes(tempoDedicado.minute.toLong())
            ?.format(timeFormatter)
    }

    fun calcularTempoDiario(tarefa: Tarefa): String {
        val dataInicial = getDataInicial(tarefa)
        val dataFinal = toLocalDate(tarefa.dataFinal)
        val diasTotais = ChronoUnit.DAYS.between(dataInicial, dataFinal) + 1
         val duracao = tarefa.duracao ?: "00:00"
        val duracaoSegundos = toLocalTime(duracao).toSecondOfDay()
        val segundosPorDia = duracaoSegundos.div(diasTotais)
        val tempoPorDia = LocalTime.ofSecondOfDay(segundosPorDia.toLong())

        if (tempoPorDia.second > 0) {
            return tempoPorDia.plusMinutes(1)
                .format(timeFormatter)
        }
        return tempoPorDia.format(timeFormatter)
    }

    fun createFormattedDate(day: Int, month: Int, year: Int): String {
        return LocalDate.of(year, month, day)
            .format(dateFormatter)
    }

    fun createFormattedTime(hour: Int, minute: Int): String {
        return LocalTime.of(hour, minute)
            .format(timeFormatter)
    }

    private fun toLocalDate(date: String): LocalDate {
        return LocalDate.parse(date, dateFormatter)
    }

    private fun toLocalTime(time: String): LocalTime {
        return LocalTime.parse(time, timeFormatter)
    }

    private fun getDataInicial(tarefa: Tarefa): LocalDate {
        val dataInicial = toLocalDate(tarefa.dataInicial)
        val diaAtual = LocalDate.now()

        if (dataInicial.isAfter(diaAtual)) {
            return dataInicial
        }
        return diaAtual
    }

    fun getCurrentDate(): String {
        return LocalDate.now().format(dateFormatter)
    }

    fun isDateAfter(startDate: String, endDate: String): Boolean {
        return try {
            val start = toLocalDate(startDate)
            val end = toLocalDate(endDate)
            end.isAfter(start) || end.isEqual(start)
        } catch (e: Exception) {
            false
        }
    }

    fun isDateBefore(startDate: String, endDate: String): Boolean {
        return try {
            val start = toLocalDate(startDate)
            val end = toLocalDate(endDate)
            end.isBefore(start)
        } catch (e: Exception) {
            false
        }
    }
}