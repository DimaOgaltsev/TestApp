package com.example.testapplication.utils

fun String.capitalizeWord(): String {
  if (isNullOrBlank())
    return this
  return substring(0, 1).toUpperCase() + substring(1).toLowerCase()
}