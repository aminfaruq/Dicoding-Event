package com.aminfaruq.dicodingevent.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aminfaruq.dicodingevent.data.Result
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.ui.EventRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: EventRepository) : ViewModel() {

    private val _eventDetail = MutableLiveData<EventDetail>()
    val eventDetail: LiveData<EventDetail> = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun requestDetail(id: Int) {
        _isLoading.value = true

        repository.getEventById(id).observeForever { result ->
            handleResult(result)
        }
    }

    private fun handleResult(result: Result<EventDetail>) {
        when (result) {
            is Result.Loading -> _isLoading.value = true
            is Result.Success -> {
                _isLoading.value = false
                _eventDetail.value = result.data
                _isError.value = false
            }
            is Result.Error -> {
                _isLoading.value = false
                _isError.value = true
            }
        }
    }

    fun getEventById(id: Int): LiveData<List<EventDetail>> {
        return repository.isFavoriteEvent(id)
    }

    fun addToFavorite(eventDetail: EventDetail) {
        viewModelScope.launch {
            repository.insertFavoriteEvent(eventDetail)
        }
    }

    fun removeFromFavorite(id: Int) {
        viewModelScope.launch {
            repository.deleteFavoriteEvent(id)
        }
    }

    companion object
}
