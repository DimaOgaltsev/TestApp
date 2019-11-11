package com.example.testapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.testapplication.data.data.Worker

@Dao
interface WorkersDao : BaseDao<Worker> {

  @Query("SELECT * from workers where id = :id LIMIT 1")
  fun getWorker(id: Long): LiveData<Worker?>

  @Query("SELECT * FROM workers where id IN (:ids)")
  fun getWorkers(ids: List<Long>): LiveData<List<Worker>>

  @Query("SELECT * FROM workers")
  fun getWorkers(): LiveData<List<Worker>>

  @Query("DELETE FROM workers")
  fun clear()

  @Transaction
  fun replaceAll(workers: List<Worker>) {
    clear()
    insert(workers)
  }
}