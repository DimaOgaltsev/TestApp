package com.example.testapplication.data.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.testapplication.data.data.Worker
import com.example.testapplication.data.db.JoinsDao
import com.example.testapplication.data.db.WorkersDao

class FilteredWorkersMediator constructor(
  specialtyId: Long,
  workersDao: WorkersDao,
  joinsDao: JoinsDao
) : MediatorLiveData<List<Worker>>() {

  private val ids = MutableLiveData<List<Long>>()

  init {
    //source - joins
    addSource(joinsDao.getBySpecialty(specialtyId)) { list ->
      val newIds = ArrayList<Long>()
      for (join in list) {
        join.workerId?.let { id -> newIds.add(id) }
      }
      ids.postValue(newIds)
    }

    //convert joins from database to workers
    val liveDataWorkers = Transformations.switchMap(ids) { ids ->
      workersDao.getWorkers(ids)
    }

    //source - workers
    addSource(liveDataWorkers) { list ->
      postValue(list)
    }
  }
}