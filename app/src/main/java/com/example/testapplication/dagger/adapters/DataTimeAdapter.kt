package com.example.testapplication.dagger.adapters

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatterBuilder
import java.lang.Exception
import java.lang.reflect.Type

class DataTimeAdapter : JsonDeserializer<DateTime> {

  companion object {
    private val formatter = DateTimeFormatterBuilder().append(null, arrayOf(
      DateTimeFormat.forPattern("yyyy-MM-dd").parser,
      DateTimeFormat.forPattern("dd-MM-yyyy").parser,
      DateTimeFormat.forPattern("yyyy/MM/dd").parser,
      DateTimeFormat.forPattern("dd/MM/yyyy").parser,
      DateTimeFormat.forPattern("yyyy.MM.dd").parser,
      DateTimeFormat.forPattern("dd.MM.yyyy").parser
    )).toFormatter()
  }

  override fun deserialize(
    json: JsonElement?,
    typeOfT: Type?,
    context: JsonDeserializationContext?
  ): DateTime? {
    try {
      if (json != null && json.asString.isNotBlank()) {
        return formatter.parseDateTime(json.asString)
      }
    } catch (e: UnsupportedOperationException) {
      Log.w("[WARNING]", "Incorrect date format")
    } catch (e: IllegalArgumentException) {
      Log.w("[WARNING]", "Incorrect argument")
    } catch (e: Exception) {
      Log.e("[ERROR]", e.message?: "Unknown error")
      e.printStackTrace()
    }
    return null
  }
}