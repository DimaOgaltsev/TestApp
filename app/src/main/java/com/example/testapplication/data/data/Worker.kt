package com.example.testapplication.data.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "workers")
data class Worker(

  @PrimaryKey(autoGenerate = true)
  var id: Long? = null,
  var firstName: String = "",
  var lastName: String = "",
  var birthday: DateTime? = null,
  var avatarUrl: String? = null
)