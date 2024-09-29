package com.aminfaruq.dicodingevent.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.ui.EventRepository
import com.aminfaruq.dicodingevent.data.Result

class HomeViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _listUpcoming = MutableLiveData<List<EventDetail>>()
    val listUpcoming: LiveData<List<EventDetail>> = _listUpcoming

    private val _listFinished = MutableLiveData<List<EventDetail>>()
    val listFinished: LiveData<List<EventDetail>> = _listFinished

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    init {
        requestUpcoming()
        requestFinished()
    }

    fun requestUpcoming() {
        if (_listUpcoming.value.isNullOrEmpty()) {
            _isLoading.value = true
            eventRepository.getEvents(active = UPCOMING).observeForever { result ->
                handleResult(result, _listUpcoming)
            }
        }
    }

    fun requestFinished() {
        if (_listFinished.value.isNullOrEmpty()) {
            _isLoading.value = true
            eventRepository.getEvents(active = FINISHED, limit = 20).observeForever { result ->
                handleResult(result, _listFinished)
            }
        }
    }

    private fun <T> handleResult(result: Result<T>, liveData: MutableLiveData<T>) {
        when (result) {
            is Result.Loading -> _isLoading.value = true
            is Result.Success -> {
                _isLoading.value = false
                liveData.value = result.data
                _isError.value = false
            }

            is Result.Error -> {
                _isLoading.value = false
                _isError.value = true
            }
        }
    }

    companion object {
        private const val UPCOMING = 1
        private const val FINISHED = 0
    }
}
