package com.example.testapplication.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testapplication.R
import com.example.testapplication.data.data.Worker
import com.example.testapplication.utils.injectViewModel
import com.example.testapplication.view.adapters.WorkersListAdapter
import com.example.testapplication.viewmodel.WorkersViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_workers_list.*
import javax.inject.Inject

class WorkersListFragment : DaggerFragment() {

  companion object {
    const val SPECIALTY_ID = "SPECIALTY_ID"
  }

  @Inject
  lateinit var workersViewModelFactory: ViewModelProvider.Factory
  private lateinit var workersViewModel: WorkersViewModel

  private val workersArray = ArrayList<Worker>()
  private var adapterList: WorkersListAdapter? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_workers_list, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val specialtyId = arguments?.getLong(SPECIALTY_ID) ?: -1L
    Log.i("[STATUS]", "Start WorkersListFragment, specialty id: $specialtyId")

    adapterList = WorkersListAdapter(activity!!, workersArray)
    workersList.adapter = adapterList
    workersList.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, id ->
      val bundle = bundleOf(WorkerInfoFragment.WORKER_ID to id)
      nav_host_fragment.findNavController().navigate(R.id.action_workers_to_workerInfo, bundle)
    }

    workersViewModel = injectViewModel(workersViewModelFactory)
    workersViewModel.getWorkers(specialtyId).observe(this, Observer { workers ->
      updateList(workers)
    })
  }

  private fun updateList(workers: List<Worker>) {
    workersArray.clear()
    for (worker in workers) {
      workersArray.add(worker)
    }
    adapterList?.notifyDataSetChanged()
  }
}
