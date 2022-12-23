package com.hust.attandance.ui.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.interact.GetStudentsInteract
import com.hust.attandance.interact.Interact
import com.hust.attandance.ui.common.BaseViewModel
import com.hust.attandance.utils.exceptions.ExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(
    exception: ExceptionHandler,
    private val getStudentsInteract: GetStudentsInteract,
) : BaseViewModel(exception) {

    private val _students = MutableLiveData<List<StudentResponse>?>()
    val students: LiveData<List<StudentResponse>?> get() = _students

    var studentList: MutableList<StudentResponse> = mutableListOf()

    init {
        getStudents()
    }

    fun getStudents() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getStudentsInteract.execute(Interact.Param())
            response?.let {
                studentList = it as MutableList<StudentResponse>
                _students.postIfChanged(studentList)
            }
        }
    }

}