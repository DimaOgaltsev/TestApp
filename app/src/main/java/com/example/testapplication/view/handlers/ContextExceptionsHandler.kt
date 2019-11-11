package com.example.testapplication.view.handlers

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.testapplication.R
import retrofit2.HttpException
import java.net.UnknownHostException

class ContextExceptionsHandler constructor(private val activity: Activity): Observer<Throwable> {

  override fun onChanged(t: Throwable) {
    when (t::class) {
      UnknownHostException::class -> Toast.makeText(activity.applicationContext, R.string.error_no_connection, Toast.LENGTH_SHORT).show()
      HttpException::class -> Toast.makeText(activity.applicationContext, R.string.error_no_host, Toast.LENGTH_SHORT).show()
      else -> {
        t.message?.let { msg ->
          //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
          Log.e("[ERROR]", msg)
        }
        t.printStackTrace()
      }
    }
  }
}