package com.example.testapplication.data.handlers

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class WorkersRepositoryExceptionHandler constructor(
  private val exceptionLiveData: MutableLiveData<Throwable>?
) :
  CoroutineExceptionHandler {

  override val key: CoroutineContext.Key<*> get() = CoroutineExceptionHandler.Key

  override fun handleException(context: CoroutineContext, exception: Throwable) {
    exceptionLiveData?.postValue(exception)
  }
}