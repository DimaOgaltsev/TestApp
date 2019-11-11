package com.example.testapplication.view.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.testapplication.data.data.Specialty

class SpecialitiesListAdapter(private val context: Activity, private val list: List<Specialty>) :
  ArrayAdapter<Specialty>(context, android.R.layout.simple_list_item_1, list) {

  override fun getView(position: Int, view: View?, parent: ViewGroup): View {
    val rowView =
      view ?: context.layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)

    val titleText = rowView.findViewById(android.R.id.text1) as TextView
    titleText.text = list[position].name

    return rowView
  }

  override fun getItemId(position: Int): Long {
    return getItem(position)?.specialtyId ?: -1
  }
}