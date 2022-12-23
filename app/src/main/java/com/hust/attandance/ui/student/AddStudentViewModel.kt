package com.hust.attandance.ui.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.interact.CreateStudentInteract
import com.hust.attandance.ui.common.BaseViewModel
import com.hust.attandance.utils.exceptions.ExceptionHandler
import com.hust.attandance.utils.lifecycle.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddStudentViewModel(
    exceptionHandler: ExceptionHandler,
    private val createStudentInteract: CreateStudentInteract,
) :
    BaseViewModel(exceptionHandler) {

    var faceId: String? = null

    private val _createStudent = SingleLiveEvent<Unit>()
    val createStudent: LiveData<Unit> get() = _createStudent

    fun createStudent(student: StudentResponse) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            createStudentInteract.execute(CreateStudentInteract.Param(student))
            delay(1000)
            _createStudent.postValue(Unit)
        }
    }
}