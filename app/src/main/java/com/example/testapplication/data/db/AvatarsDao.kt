package com.example.testapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.testapplication.data.data.Avatar

@Dao
interface AvatarsDao : BaseDao<Avatar> {

  @Query("SELECT * FROM avatars WHERE avatarUrl = :url")
  fun getAvatar(url: String): LiveData<Avatar?>
}