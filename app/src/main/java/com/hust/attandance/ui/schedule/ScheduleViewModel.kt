package com.hust.attandance.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hust.attandance.data.model.ScheduleResponse
import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.interact.GetSchedulesInteract
import com.hust.attandance.interact.GetStudentsInteract
import com.hust.attandance.interact.Interact
import com.hust.attandance.ui.common.BaseViewModel
import com.hust.attandance.utils.exceptions.ExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleViewModel(
    exception: ExceptionHandler,
    private val getSchedulesInteract: GetSchedulesInteract,
) : BaseViewModel(exception) {

    private val _schedules = MutableLiveData<List<ScheduleResponse>?>()
    val schedules: LiveData<List<ScheduleResponse>?> get() = _schedules

    fun getSchedules() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getSchedulesInteract.execute(Interact.Param())
            response?.let {
                _schedules.postIfChanged(it)
            }
        }
    }

}