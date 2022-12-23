package com.hust.attandance.ui.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hust.attandance.data.model.ClassResponse
import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.interact.CreateStudentInteract
import com.hust.attandance.interact.GetStudentsInteract
import com.hust.attandance.interact.Interact
import com.hust.attandance.ui.common.BaseViewModel
import com.hust.attandance.utils.exceptions.ExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class DetailClassesViewModel(
    exception: ExceptionHandler,
    private val createStudentInteract: CreateStudentInteract,
    private val getStudentsInteract: GetStudentsInteract,
) : BaseViewModel(exception) {

    private val _students = MutableLiveData<List<StudentResponse>?>()
    val students: LiveData<List<StudentResponse>?> get() = _students

    private val _classDetail = MutableLiveData<ClassResponse>()
    val classDetail: LiveData<ClassResponse> get() = _classDetail

    var studentList: MutableList<StudentResponse> = mutableListOf()


    fun getStudents() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getStudentsInteract.execute(Interact.Param())
            response?.let {
                studentList = it as MutableList<StudentResponse>
            }
        }
    }

    fun createStudent(student: StudentResponse) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            createStudentInteract.execute(CreateStudentInteract.Param(student))
            studentList.add(student)
            _students.postIfChanged(studentList)
        }
    }

    fun getDetail(item: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val listType: Type = object : TypeToken<ClassResponse>() {}.type
            _classDetail.postValue(Gson().fromJson(item, listType))
        }
    }

}