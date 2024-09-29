package com.aminfaruq.dicodingevent.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.ui.EventRepository
import com.aminfaruq.dicodingevent.data.Result

class FinishedViewModel(private val repository: EventRepository) : ViewModel() {

    private val _listFinished = MutableLiveData<List<EventDetail>>()
    val listFinished: LiveData<List<EventDetail>> = _listFinished

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

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
        repository.getEvents(active, q).observeForever { result ->
            handleResult(result)
        }
    }

    private fun handleResult(result: Result<List<EventDetail>>) {
        when (result) {
            is Result.Loading -> _isLoading.value = true
            is Result.Success -> {
                _isLoading.value = false
                _listFinished.value = result.data
                _isError.value = false
            }

            is Result.Error -> {
                _isLoading.value = false
                _isError.value = true
            }
        }
    }

    companion object {
        private const val FINISHED = 0
    }
}
