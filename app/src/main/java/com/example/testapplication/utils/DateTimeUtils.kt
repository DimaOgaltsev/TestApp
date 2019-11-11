package com.example.testapplication.utils

import org.joda.time.DateTime
import org.joda.time.Years
import org.joda.time.format.DateTimeFormat

abstract class DateTimeUtils {

  companion object {

    private const val noResultString = "-"

    fun getAgeString(date: DateTime?): String {
      if (date == null)
        return noResultString
      return Years.yearsBetween(date, DateTime.now()).years.toString()
    }

    fun getFormatString(date: DateTime?, format: String): String {
      return date?.toString(DateTimeFormat.forPattern(format)) ?: noResultString
    }
  }
}