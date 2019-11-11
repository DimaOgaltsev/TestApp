package com.example.testapplication.dagger.components

import com.example.testapplication.MainApplication
import com.example.testapplication.dagger.modules.AppBinderModule
import com.example.testapplication.dagger.modules.DatabaseModule
import com.example.testapplication.dagger.modules.NetModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AndroidSupportInjectionModule::class,
    DatabaseModule::class,
    NetModule::class,
    AppBinderModule::class
  ]
)

interface AppComponent : AndroidInjector<MainApplication> {
  @Component.Factory
  interface Factory {
    fun create(
      databaseModule: DatabaseModule,
      netModule: NetModule, @BindsInstance application: MainApplication
    ): AppComponent
  }
}