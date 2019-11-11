package com.example.testapplication.data.livedata

import android.util.Base64
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.testapplication.data.data.Avatar
import com.example.testapplication.data.db.AvatarsDao
import com.example.testapplication.data.db.WorkersDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.URL

class AvatarMediator constructor(
  workerId: Long,
  workersDao: WorkersDao,
  avatarsDao: AvatarsDao,
  scope: CoroutineScope
) : MediatorLiveData<Avatar>() {

  private val url = MutableLiveData<String>()

  init {

    //source - worker
    addSource(workersDao.getWorker(workerId)) { worker ->
      worker?.let {
        if (worker.avatarUrl != null) {
          url.postValue(worker.avatarUrl)
        } else {
          postValue(null)
        }
      }
    }

    //convert url from Worker to Avatar from database
    val liveDataURL = Transformations.switchMap(url) { url ->
      avatarsDao.getAvatar(url)
    }

    //source avatar
    addSource(liveDataURL) { avatar ->
      scope.launch(Dispatchers.IO) {

        //if avatar is found in database
        if (avatar != null) {

          Log.i("[STATUS]", "Avatar is found in database")

          avatar.dataStream = ByteArrayInputStream(Base64.decode(avatar.data, Base64.DEFAULT))
          postValue(avatar)
        }

        //request new avatar anyway, for compare hash
        val loadURL = url.value
        if (loadURL.isNullOrBlank()) {
          postValue(null)
        } else {

          Log.i("[STATUS]", "Avatar request URL: $loadURL")

          try {
            val urlLoader = URL(loadURL).openConnection()
            if (urlLoader.contentType.contains("image")) {
              val inputStream = urlLoader.content as InputStream
              val imageData = Base64.encodeToString(inputStream.readBytes(), Base64.DEFAULT)
              val imageHash = imageData.hashCode()
              if (avatar == null || imageHash != avatar.hash) {
                val newAvatar = Avatar(loadURL, imageData, imageHash, inputStream)
                avatarsDao.insert(newAvatar)
                postValue(newAvatar)
              }
            } else {
              Log.w("[WARNING]", "Can't load avatar URL (not image): $loadURL")
              postValue(null)
            }
          } catch (e: Exception) {
            Log.w("[WARNING]", "Can't load avatar URL: $loadURL")
            postValue(null)
          }
        }
      }
    }
  }
}