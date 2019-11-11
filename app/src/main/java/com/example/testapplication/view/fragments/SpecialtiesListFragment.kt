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
import androidx.navigation.Navigation
import com.example.testapplication.R
import com.example.testapplication.data.data.Specialty
import com.example.testapplication.utils.injectViewModel
import com.example.testapplication.view.adapters.SpecialitiesListAdapter
import com.example.testapplication.viewmodel.WorkersViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_specialties_list.*
import javax.inject.Inject

class SpecialtiesListFragment : DaggerFragment() {

  @Inject
  lateinit var workersViewModelFactory: ViewModelProvider.Factory
  private lateinit var workersViewModel: WorkersViewModel

  private val specialtiesArray = ArrayList<Specialty>()
  private lateinit var adapterList: SpecialitiesListAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_specialties_list, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    Log.i("[STATUS]", "Start SpecialtiesListFragment")

    adapterList = SpecialitiesListAdapter(activity!!, specialtiesArray)
    specialtiesList.adapter = adapterList
    specialtiesList.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, id ->
      val bundle = bundleOf(WorkersListFragment.SPECIALTY_ID to id)
      if (id < 0) {
        navController.navigate(R.id.action_specialties_to_workers, bundle)
      } else {
        navController.navigate(R.id.action_specialties_to_workers_filtered, bundle)
      }
    }

    workersViewModel = injectViewModel(workersViewModelFactory)
    workersViewModel.getSpecialties().observe(this, Observer { specialties ->
      updateList(specialties)
    })
  }

  private fun updateList(specialties: List<Specialty>) {
    specialtiesArray.clear()
    specialtiesArray.add(Specialty(-1, context?.getString(R.string.all) ?: "All"))
    for (specialty in specialties) {
      specialtiesArray.add(specialty)
    }
    adapterList.notifyDataSetChanged()
  }
}
