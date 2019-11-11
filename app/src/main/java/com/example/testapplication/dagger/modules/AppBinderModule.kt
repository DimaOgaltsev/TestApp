package com.example.testapplication.dagger.modules

import com.example.testapplication.view.MainActivity
import com.example.testapplication.view.fragments.SpecialtiesListFragment
import com.example.testapplication.view.fragments.WorkerInfoFragment
import com.example.testapplication.view.fragments.WorkersListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class AppBinderModule {

  @ContributesAndroidInjector
  abstract fun bindMainActivity(): MainActivity

  @ContributesAndroidInjector
  abstract fun bindWorkersListFragment(): WorkersListFragment

  @ContributesAndroidInjector
  abstract fun bindWorkerInfoFragment(): WorkerInfoFragment

  @ContributesAndroidInjector
  abstract fun bindSpecialtiesListFragment(): SpecialtiesListFragment
}