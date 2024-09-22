package com.aminfaruq.dicodingevent.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aminfaruq.dicodingevent.data.api.ApiConfig
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.data.response.EventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _listUpcoming = MutableLiveData<List<EventDetail>>()
    val listUpcoming: LiveData<List<EventDetail>> = _listUpcoming

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "UpcomingViewModel"
        private const val UPCOMING = 1
    }

    init {
        requestUpcoming()
    }

    private fun requestUpcoming() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListEvent(active = UPCOMING)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUpcoming.value = response.body()?.listEvents ?: emptyList()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}