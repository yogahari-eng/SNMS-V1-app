package com.snms.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.snms.app.SNMSApplication
import com.snms.app.database.NotificationEntity
import com.snms.app.database.Priority
import com.snms.app.repository.NotificationRepository
import kotlinx.coroutines.launch

class NotificationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotificationRepository = NotificationRepository(
        (application as SNMSApplication).database.notificationDao()
    )

    val allNotifications: LiveData<List<NotificationEntity>> = repository.allNotifications
    val totalCount: LiveData<Int> = repository.totalCount
    val highCount: LiveData<Int> = repository.highCount
    val mediumCount: LiveData<Int> = repository.mediumCount
    val lowCount: LiveData<Int> = repository.lowCount

    // Focus mode: when true, only HIGH priority notifications are displayed
    private val _focusMode = MutableLiveData(false)
    val focusMode: LiveData<Boolean> = _focusMode

    val displayedNotifications: LiveData<List<NotificationEntity>> =
        MediatorLiveData<List<NotificationEntity>>().apply {
            var currentAll: List<NotificationEntity> = emptyList()
            var isFocusMode = false

            fun update() {
                value = if (isFocusMode) {
                    currentAll.filter { it.priority == Priority.HIGH }
                } else {
                    currentAll
                }
            }

            addSource(allNotifications) { list ->
                currentAll = list ?: emptyList()
                update()
            }
            addSource(_focusMode) { focus ->
                isFocusMode = focus
                update()
            }
        }

    fun setFocusMode(enabled: Boolean) {
        _focusMode.value = enabled
    }

    fun markAsRead(id: Long) = viewModelScope.launch {
        repository.markAsRead(id)
    }

    fun delete(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }

    fun clearAll() = viewModelScope.launch {
        repository.clearAll()
    }

    fun getByPriority(priority: Priority) = repository.getByPriority(priority)
}

class NotificationViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotificationViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
