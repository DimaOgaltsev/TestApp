package com.example.testapplication.data.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.InputStream

@Entity(tableName = "avatars")
data class Avatar(

  @PrimaryKey
  var avatarUrl: String = "",
  var data: String? = null,
  var hash: Int = 0,

  @Ignore
  var dataStream: InputStream? = null
)