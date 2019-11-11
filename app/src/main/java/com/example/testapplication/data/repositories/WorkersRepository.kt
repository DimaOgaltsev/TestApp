package com.example.testapplication.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapplication.data.api.GitlabApiInterface
import com.example.testapplication.data.data.Avatar
import com.example.testapplication.data.data.Join
import com.example.testapplication.data.data.Specialty
import com.example.testapplication.data.data.Worker
import com.example.testapplication.data.db.AvatarsDao
import com.example.testapplication.data.db.JoinsDao
import com.example.testapplication.data.db.SpecialtiesDao
import com.example.testapplication.data.db.WorkersDao
import com.example.testapplication.data.handlers.WorkersRepositoryExceptionHandler
import com.example.testapplication.data.livedata.AvatarMediator
import com.example.testapplication.data.livedata.FilteredSpecialtyMediator
import com.example.testapplication.data.livedata.FilteredWorkersMediator
import com.example.testapplication.utils.capitalizeWord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WorkersRepository @Inject constructor(
  private val gitlabApiInterface: GitlabApiInterface,
  private val workersDao: WorkersDao,
  private val specialtiesDao: SpecialtiesDao,
  private val joinsDao: JoinsDao,
  private val avatarsDao: AvatarsDao
) {

  private val exceptionLiveData = MutableLiveData<Throwable>()
  private val repositoryJob = Job()
  private val exceptionHandler = WorkersRepositoryExceptionHandler(exceptionLiveData)
  private val repositoryScope = CoroutineScope(Dispatchers.IO + repositoryJob + exceptionHandler)

  fun updateData() : LiveData<Boolean> {

    val finished = MutableLiveData<Boolean>(false)

    repositoryScope.launch {

      Log.i("[STATUS]", "Start update...")
      val result = gitlabApiInterface.getTestDataAsync().await()

      result.list?.let { resultList ->

        Log.i("[STATUS]", "Update data...")
        Log.d("[STATUS]", "$resultList")

        val workers = ArrayList<Worker>()
        val specialties = ArrayList<Specialty>()
        val joins = ArrayList<Join>()

        for ((index, workerJSON) in resultList.withIndex()) {

          //worker without name or last name - ignore
          if (workerJSON.firstName.isNullOrBlank() || workerJSON.lastName.isNullOrBlank())
            continue

          //add worker
          val worker = Worker(
            id = index.toLong(),
            firstName = workerJSON.firstName?.capitalizeWord() ?: "",
            lastName = workerJSON.lastName?.capitalizeWord() ?: "",
            birthday = workerJSON.birthday,
            avatarUrl = when (workerJSON.avatarUrl.isNullOrBlank()) {
              false -> workerJSON.avatarUrl
              true -> null
            }
          )
          workers.add(worker)

          workerJSON.specialties?.let { specialtiesList ->
            for (specialty in specialtiesList) {

              //add specialty
              specialty.name?.let { name ->
                specialty.name = name.capitalizeWord()
                specialties.add(specialty)
              }

              //add join
              val join = Join(
                workerId = worker.id,
                specialtyId = specialty.specialtyId
              )
              joins.add(join)
            }
          }
          }

        workersDao.replaceAll(workers)
        specialtiesDao.replaceAll(specialties)
        joinsDao.replaceAll(joins)
      }

      Log.i("[STATUS]", "Update is finished")
      finished.postValue(true)
    }

    return finished
  }

  fun getWorker(id: Long): LiveData<Worker?> {
    return workersDao.getWorker(id)
  }

  fun getWorkers(): LiveData<List<Worker>> {
    return workersDao.getWorkers()
  }

  fun getFilteredWorkers(specialtyId: Long): LiveData<List<Worker>> {
    return FilteredWorkersMediator(specialtyId, workersDao, joinsDao)
  }

  fun getSpecialties(): LiveData<List<Specialty>> {
    return specialtiesDao.getSpecialties()
  }

  fun getSpecialties(workerId: Long): LiveData<List<Specialty>> {
    return FilteredSpecialtyMediator(workerId, specialtiesDao, joinsDao)
  }

  fun getAvatar(id: Long): LiveData<Avatar?> {
    return AvatarMediator(id, workersDao, avatarsDao, repositoryScope)
  }

  fun getExceptionLiveData(): LiveData<Throwable> {
    return exceptionLiveData
  }
}