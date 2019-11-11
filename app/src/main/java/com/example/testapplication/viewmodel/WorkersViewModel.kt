package com.example.testapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.data.data.Avatar
import com.example.testapplication.data.data.Specialty
import com.example.testapplication.data.data.Worker
import com.example.testapplication.data.repositories.WorkersRepository
import javax.inject.Inject

class WorkersViewModel @Inject constructor(private val workersRepository: WorkersRepository) :
  ViewModel() {

  fun getWorker(id: Long): LiveData<Worker?> {
    return workersRepository.getWorker(id)
  }

  fun getWorkers(): LiveData<List<Worker>> {
    return workersRepository.getWorkers()
  }

  fun getWorkers(specialtyId: Long): LiveData<List<Worker>> {
    if (specialtyId < 0) {
      return workersRepository.getWorkers()
    }
    return workersRepository.getFilteredWorkers(specialtyId)
  }

  fun getSpecialties(): LiveData<List<Specialty>> {
    return workersRepository.getSpecialties()
  }

  fun getSpecialties(workerId: Long): LiveData<List<Specialty>> {
    return workersRepository.getSpecialties(workerId)
  }

  fun getAvatar(workerId: Long): LiveData<Avatar?> {
    return workersRepository.getAvatar(workerId)
  }

  fun update() : LiveData<Boolean>{
    return workersRepository.updateData()
  }

  fun getExceptionLiveData() : LiveData<Throwable> {
    return workersRepository.getExceptionLiveData()
  }
}