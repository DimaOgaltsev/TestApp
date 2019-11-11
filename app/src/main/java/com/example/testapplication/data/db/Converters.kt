package com.example.testapplication.data.db

import androidx.room.TypeConverter
import org.joda.time.DateTime

class Converters {

  @TypeConverter
  fun fromTimestampToDate(value: Long?): DateTime? {
    return value?.let { DateTime(it) }
  }

  @TypeConverter
  fun fromDateToTimestamp(date: DateTime?): Long? {
    return date?.millis
  }
}