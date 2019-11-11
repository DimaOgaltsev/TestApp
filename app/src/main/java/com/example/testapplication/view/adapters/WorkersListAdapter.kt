package com.example.testapplication.view.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.testapplication.R
import com.example.testapplication.data.data.Worker
import com.example.testapplication.utils.DateTimeUtils

class WorkersListAdapter(private val context: Activity, private val list: List<Worker>) :
  ArrayAdapter<Worker>(context, android.R.layout.simple_list_item_2, list) {

  override fun getView(position: Int, view: View?, parent: ViewGroup): View {
    val rowView =
      view ?: context.layoutInflater.inflate(android.R.layout.simple_list_item_2, parent, false)

    val text1View = rowView.findViewById(android.R.id.text1) as TextView
    val text2View = rowView.findViewById(android.R.id.text2) as TextView

    text1View.text = StringBuilder().also {
      it.append(list[position].firstName)
      it.append(" ")
      it.append(list[position].lastName)
    }.toString()

    text2View.text = StringBuilder().also {
      it.append(context.getString(R.string.age_info))
      it.append(" ")
      it.append(DateTimeUtils.getAgeString(list[position].birthday))
    }.toString()

    return rowView
  }

  override fun getItemId(position: Int): Long {
    return getItem(position)?.id ?: -1
  }
}