package com.example.testapplication.view.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.R
import com.example.testapplication.data.data.Avatar
import com.example.testapplication.utils.DateTimeUtils
import com.example.testapplication.utils.injectViewModel
import com.example.testapplication.viewmodel.WorkersViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_worker_info.*
import java.lang.Exception
import javax.inject.Inject

class WorkerInfoFragment : DaggerFragment() {

  companion object {
    const val WORKER_ID = "WORKER_ID"
  }

  @Inject
  lateinit var workersViewModelFactory: ViewModelProvider.Factory
  private lateinit var workersViewModel: WorkersViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_worker_info, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val workerId = arguments?.getLong(WORKER_ID) ?: -1L
    Log.i("[STATUS]", "Start WorkerInfoFragment, worker id: $workerId")

    if (workerId < 0) {
      Log.e("[ERROR]","Incorrect worker ID for WorkerInfoFragment")
      return
    }

    workersViewModel = injectViewModel(workersViewModelFactory)
    workersViewModel.getWorker(workerId).observe(this, Observer { worker ->
      textFirstName.text = worker?.firstName ?: ""
      textLastName.text = worker?.lastName ?: ""
      textAge.text = DateTimeUtils.getAgeString(worker?.birthday)
      textBirthday.text = DateTimeUtils.getFormatString(worker?.birthday, ("dd.MM.yyyy"))
    })

    workersViewModel.getSpecialties(workerId).observe(this, Observer { list ->

      listSpecialties.removeAllViews()

      for (specialty in list) {
        specialty.name?.let { name ->
          val specialtyText = TextView(context).apply {
            text = name
            textSize = 18.0f
          }
          listSpecialties.addView(specialtyText)
        }
      }
    })

    avatarLoading.visibility = View.VISIBLE
    avatarView.visibility = View.INVISIBLE

    workersViewModel.getAvatar(workerId).observe(this, Observer {
        avatar -> setAvatar(avatar)
    })
  }

  private fun setAvatar(avatar: Avatar?) {
    try {
      if (avatar != null) {
        val bitmap = BitmapFactory.decodeStream(avatar.dataStream)
        if (bitmap != null)
          avatarView.setImageBitmap(bitmap)
      }
    } catch (e: Exception) {
    }

    avatarLoading.visibility = View.INVISIBLE
    avatarView.visibility = View.VISIBLE
  }
}
