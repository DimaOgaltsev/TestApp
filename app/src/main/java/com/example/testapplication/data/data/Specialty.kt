package com.example.testapplication.data.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "specialties")
data class Specialty(

    @PrimaryKey(autoGenerate = true)
    @SerializedName("specialty_id")
    var specialtyId : Long? = null,

    @SerializedName("name")
    var name: String? = null
)