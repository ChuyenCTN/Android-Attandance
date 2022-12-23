package com.hust.attandance.ui.attandance

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hust.attandance.data.model.ScheduleResponse
import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.interact.CreateScheduleInteract
import com.hust.attandance.interact.GetStudentsInteract
import com.hust.attandance.interact.Interact
import com.hust.attandance.ui.common.BaseViewModel
import com.hust.attandance.utils.exceptions.ExceptionHandler
import com.hust.attandance.utils.extensions.toISO8601DMY
import com.hust.attandance.utils.extensions.toISO8601HH
import com.hust.attandance.utils.lifecycle.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.*

class FaceAttandanceViewModel(
    exceptionHandler: ExceptionHandler,
    private val getStudentsInteract: GetStudentsInteract,
    private val createScheduleInteract: CreateScheduleInteract,
) :
    BaseViewModel(exceptionHandler) {

    private val _students = MutableLiveData<List<StudentResponse>?>()
    val students: LiveData<List<StudentResponse>?> get() = _students

    var studentList: MutableList<StudentResponse> = mutableListOf()

    private val listType: Type = object : TypeToken<SimilarityClassifier.Recognition>() {}.type

    var registeredList: MutableList<android.util.Pair<String, SimilarityClassifier.Recognition>> =
        mutableListOf()

    private val _createSchedule = SingleLiveEvent<Unit>()
    val createSchedule: LiveData<Unit> get() = _createSchedule

    fun getStudents() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getStudentsInteract.execute(Interact.Param())
            registeredList.clear()
            response?.let {
                studentList = it as MutableList<StudentResponse>
                it.map {
                    getRecognition(it.faceId)
                    registeredList.add(android.util.Pair(it.code, getRecognition(it.faceId)))
                }
                _students.postIfChanged(studentList)
                registeredList.map {
                    Log.d("qazx ${it.first}", it.second.toString())
                }
            }
        }
    }

    private fun getRecognition(json: String): SimilarityClassifier.Recognition {
        val data: SimilarityClassifier.Recognition = Gson().fromJson(json, listType)
//        val ja = data.extra as ArrayList<ArrayList<Any>>
//        Log.d("qazxcvbn", data.extra.toString())
//        Log.d("qazxcvbn", ja.toString())
        return data
    }

    fun checkStudentAttan(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            studentList.map {
                if (it.code == code) {
                    it.checked = true
                }
                _students.postValue(studentList)
            }
        }
    }

    fun saveAttandance() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val number = studentList.count { it.checked }
            createScheduleInteract.execute(
                CreateScheduleInteract.Param(
                    ScheduleResponse(
                        UUID.randomUUID().toString(),
                        System.currentTimeMillis().toISO8601HH(),
                        System.currentTimeMillis().toISO8601DMY(),
                        number,
                        studentList.size,
                        "Ghi chú",
                        "Ca sáng"
                    )
                )
            )
            delay(1000)
            _createSchedule.postValue(Unit)
        }
    }
}
