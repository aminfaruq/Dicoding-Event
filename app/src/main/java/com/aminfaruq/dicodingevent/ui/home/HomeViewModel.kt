package com.aminfaruq.dicodingevent.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aminfaruq.dicodingevent.data.api.ApiConfig
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.data.response.EventResponse
import com.aminfaruq.dicodingevent.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _listUpcoming = MutableLiveData<List<EventDetail>>()
    val listUpcoming: LiveData<List<EventDetail>> = _listUpcoming

    private val _listFinished = MutableLiveData<List<EventDetail>>()
    val listFinished: LiveData<List<EventDetail>> = _listFinished

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    companion object {
        private const val TAG = "HomeViewModel"
        private const val UPCOMING = 1
        private const val FINISHED = 0
    }

    init {
        requestUpcoming()
        requestFinished()
    }

    fun requestUpcoming() {
        if (_listUpcoming.value.isNullOrEmpty()) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getListEvent(active = UPCOMING)
            client.enqueue(object : Callback<EventResponse> {
                override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _isError.value = false
                        _listUpcoming.value = response.body()?.listEvents ?: emptyList()
                    } else {
                        _isError.value = true
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isError.value = true
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })
        }
    }

    fun requestFinished() {
        if (_listFinished.value.isNullOrEmpty()) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getListEvent(active = FINISHED, limit = 20)
            client.enqueue(object : Callback<EventResponse> {
                override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _listFinished.value = response.body()?.listEvents ?: emptyList()
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
}
