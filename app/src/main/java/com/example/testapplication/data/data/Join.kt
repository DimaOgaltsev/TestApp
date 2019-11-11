package com.example.testapplication.data.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joins")
data class Join(

  @PrimaryKey(autoGenerate = true)
  var id: Long? = null,
  var workerId: Long? = null,
  var specialtyId: Long? = null
)