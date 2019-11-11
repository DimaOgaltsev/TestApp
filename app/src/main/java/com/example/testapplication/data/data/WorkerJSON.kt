package com.example.testapplication.data.data

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class WorkerJSON(

  @SerializedName("f_name")
  var firstName: String? = null,

  @SerializedName("l_name")
  var lastName: String? = null,

  @SerializedName("birthday")
  var birthday: DateTime? = null,

  @SerializedName("avatr_url")
  var avatarUrl: String? = null,

  @SerializedName("specialty")
  var specialties: List<Specialty>? = null
)