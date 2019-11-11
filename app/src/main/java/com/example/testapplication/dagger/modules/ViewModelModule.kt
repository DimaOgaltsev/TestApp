package com.example.testapplication.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.dagger.keys.ViewModelKey
import com.example.testapplication.viewmodel.ViewModelFactory
import com.example.testapplication.viewmodel.WorkersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(WorkersViewModel::class)
  internal abstract fun workersViewModel(viewModel: WorkersViewModel): ViewModel
}