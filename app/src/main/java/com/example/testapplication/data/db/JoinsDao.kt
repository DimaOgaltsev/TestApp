package com.example.testapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.testapplication.data.data.Join

@Dao
interface JoinsDao : BaseDao<Join> {

  @Query("SELECT * FROM joins WHERE specialtyId = :id")
  fun getBySpecialty(id: Long): LiveData<List<Join>>

  @Query("SELECT * FROM joins WHERE workerId = :id")
  fun getByWorker(id: Long): LiveData<List<Join>>

  @Query("DELETE FROM joins")
  fun clear()

  @Transaction
  fun replaceAll(joins: List<Join>) {
    clear()
    insert(joins)
  }
}