package com.example.testapplication.dagger.modules

import android.app.Application
import androidx.room.Room
import com.example.testapplication.data.db.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val app: Application, private val databaseName: String) {

  @Provides
  @Singleton
  fun provideApplication(): Application = app

  @Provides
  @Singleton
  fun provideDatabase(app: Application): Database =
    Room.databaseBuilder(app, Database::class.java, databaseName).build()

  @Provides
  @Singleton
  fun provideWorkersDao(database: Database): WorkersDao =
    database.workersDao()

  @Provides
  @Singleton
  fun provideSpecialtiesDao(database: Database): SpecialtiesDao =
    database.specialtiesDao()

  @Provides
  @Singleton
  fun provideJoinsDao(database: Database): JoinsDao =
    database.joinsDao()

  @Provides
  @Singleton
  fun provideAvatarsDao(database: Database): AvatarsDao =
    database.avatarsDao()
}