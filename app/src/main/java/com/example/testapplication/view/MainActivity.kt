package com.example.testapplication.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testapplication.R
import com.example.testapplication.utils.injectViewModel
import com.example.testapplication.view.handlers.ContextExceptionsHandler
import com.example.testapplication.viewmodel.WorkersViewModel
import com.google.android.material.navigation.NavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var workersViewModelFactory: ViewModelProvider.Factory
  private lateinit var workersViewModel: WorkersViewModel

  private lateinit var appBarConfiguration: AppBarConfiguration

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val toolbar: Toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)

    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
    val navView: NavigationView = findViewById(R.id.nav_view)
    val navController = nav_host_fragment.findNavController()

    appBarConfiguration = AppBarConfiguration(
      setOf(R.id.nav_workers, R.id.nav_specialties), drawerLayout
    )
    setupActionBarWithNavController(navController, appBarConfiguration)
    navView.setupWithNavController(navController)

    workersViewModel = injectViewModel(workersViewModelFactory)
    workersViewModel.getExceptionLiveData().observe(this, ContextExceptionsHandler(this))

    Toast.makeText(this, getString(R.string.update_data), Toast.LENGTH_SHORT).show()
    workersViewModel.update().observe(this, Observer { finished ->
      if (finished) {
        Toast.makeText(this, getString(R.string.updated), Toast.LENGTH_SHORT).show()
      }
    })
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment)
    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
  }
}
