package com.example.testapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.testapplication.data.data.Specialty

@Dao
interface SpecialtiesDao : BaseDao<Specialty> {

  @Query("SELECT * from specialties where specialtyId = :id LIMIT 1")
  fun getSpecialty(id: Long): LiveData<Specialty?>

  @Query("SELECT * FROM specialties where specialtyId IN (:ids)")
  fun getSpecialties(ids: List<Long>): LiveData<List<Specialty>>

  @Query("SELECT * FROM specialties")
  fun getSpecialties(): LiveData<List<Specialty>>

  @Query("DELETE FROM specialties")
  fun clear()

  @Transaction
  fun replaceAll(specialties: List<Specialty>) {
    clear()
    insert(specialties)
  }
}