package com.aminfaruq.dicodingevent.ui.finished

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

class FinishedViewModel : ViewModel() {
    private val _listFinished = MutableLiveData<List<EventDetail>>()
    val listFinished: LiveData<List<EventDetail>> = _listFinished

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    companion object {
        private const val TAG = "FinishedViewModel"
        private const val FINISHED = 0
    }

    init {
        requestFinished(canSearch = false)
    }

    fun requestFinished(active: Int? = FINISHED, q: String? = null, canSearch: Boolean) {
        if (canSearch) {
            makeApiCall(active, q)
        } else {
            if (_listFinished.value.isNullOrEmpty()) {
                _isLoading.value = true
                makeApiCall(active, q)
            }
        }
    }

    private fun makeApiCall(active: Int?, q: String?) {
        val client = ApiConfig.getApiService().getListEvent(active = active, q = q)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isError.value = false
                    _listFinished.value = response.body()?.listEvents ?: emptyList()
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
