package com.example.testapplication.data.db

import androidx.room.*

@Dao
interface BaseDao<T> {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(obj: T): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(obj: List<T>): List<Long>

  @Update
  fun update(obj: T)

  @Update
  fun update(obj: List<T>)

  @Delete
  fun delete(obj: T)
}