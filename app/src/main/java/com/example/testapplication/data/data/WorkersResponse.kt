package com.example.testapplication.data.data

import com.google.gson.annotations.SerializedName

data class WorkersResponse(

  @SerializedName("response")
  var list: List<WorkerJSON>? = null
)