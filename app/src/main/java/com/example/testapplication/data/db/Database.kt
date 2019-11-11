package com.example.testapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testapplication.data.data.Avatar
import com.example.testapplication.data.data.Join
import com.example.testapplication.data.data.Specialty
import com.example.testapplication.data.data.Worker

@Database(
  entities = [Worker::class, Specialty::class, Join::class, Avatar::class],
  version = 1,
  exportSchema = false
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
  abstract fun workersDao(): WorkersDao
  abstract fun specialtiesDao(): SpecialtiesDao
  abstract fun joinsDao(): JoinsDao
  abstract fun avatarsDao(): AvatarsDao
}