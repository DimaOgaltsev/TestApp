package com.example.testapplication

import com.example.testapplication.dagger.components.DaggerAppComponent
import com.example.testapplication.dagger.modules.DatabaseModule
import com.example.testapplication.dagger.modules.NetModule
import com.google.android.gms.security.ProviderInstaller
import dagger.android.AndroidInjector

import dagger.android.DaggerApplication
import net.danlew.android.joda.JodaTimeAndroid

class MainApplication : DaggerApplication() {

  private var dbName = "workers_db"
  private var baseURL = "http://gitlab.65apps.com/"

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent.factory()
      .create(DatabaseModule(this, dbName), NetModule(baseURL), this)
  }

  override fun onCreate() {
    super.onCreate()
    ProviderInstaller.installIfNeeded(applicationContext)
    JodaTimeAndroid.init(this)
  }
}