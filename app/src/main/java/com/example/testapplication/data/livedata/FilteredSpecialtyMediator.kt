package com.example.testapplication.data.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.testapplication.data.data.Specialty
import com.example.testapplication.data.db.JoinsDao
import com.example.testapplication.data.db.SpecialtiesDao

class FilteredSpecialtyMediator constructor(
  workerId: Long,
  specialtiesDao: SpecialtiesDao,
  joinsDao: JoinsDao
) : MediatorLiveData<List<Specialty>>() {

  private val ids = MutableLiveData<List<Long>>()

  init {
    //source - joins
    addSource(joinsDao.getByWorker(workerId)) { list ->
      val newIds = ArrayList<Long>()
      for (join in list) {
        join.specialtyId?.let { id -> newIds.add(id) }
      }
      ids.postValue(newIds)
    }

    //convert joins from database to specialties
    val liveDataSpecialties = Transformations.switchMap(ids) { ids ->
      specialtiesDao.getSpecialties(ids)
    }

    //source - specialties
    addSource(liveDataSpecialties) { list ->
      postValue(list)
    }
  }
}