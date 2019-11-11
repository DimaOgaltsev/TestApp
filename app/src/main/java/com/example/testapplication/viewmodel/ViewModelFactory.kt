package com.example.testapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject constructor(
  private val viewModelsMap: Map<Class<out ViewModel>,
      @JvmSuppressWildcards Provider<ViewModel>>
) :
  ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    val viewModelProvider = viewModelsMap[modelClass]
      ?: throw IllegalArgumentException("model class $modelClass not found")
    return viewModelProvider.get() as T
  }
}