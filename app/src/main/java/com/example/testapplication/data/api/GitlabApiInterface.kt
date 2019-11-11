package com.example.testapplication.data.api

import com.example.testapplication.data.data.WorkersResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface GitlabApiInterface {
  @GET("65gb/static/raw/master/testTask.json")
  fun getTestDataAsync(): Deferred<WorkersResponse>
}