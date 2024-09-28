package com.aminfaruq.dicodingevent.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.ui.EventRepository
import com.aminfaruq.dicodingevent.data.Result

class UpcomingViewModel(private val repository: EventRepository) : ViewModel() {

    private val _listUpcoming = MutableLiveData<List<EventDetail>>()
    val listUpcoming: LiveData<List<EventDetail>> = _listUpcoming

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    init {
        requestUpcoming()
    }

    fun requestUpcoming() {
        if (_listUpcoming.value.isNullOrEmpty()) {
            _isLoading.value = true
            repository.getEvents(active = UPCOMING).observeForever { result ->
                handleResult(result)
            }
        }
    }

    private fun handleResult(result: Result<List<EventDetail>>) {
        when (result) {
            is Result.Loading -> _isLoading.value = true
            is Result.Success -> {
                _isLoading.value = false
                _listUpcoming.value = result.data
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
    }
}
