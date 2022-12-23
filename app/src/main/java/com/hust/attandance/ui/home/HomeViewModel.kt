package com.hust.attandance.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hust.attandance.data.model.ClassResponse
import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.di.viewModelModule
import com.hust.attandance.interact.GetClassInteract
import com.hust.attandance.interact.GetClassesInteract
import com.hust.attandance.interact.Interact
import com.hust.attandance.ui.common.BaseViewModel
import com.hust.attandance.utils.exceptions.ExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    exceptionHandler: ExceptionHandler,
    private val getClassesInteract: GetClassesInteract
) :
    BaseViewModel(exceptionHandler) {

    private val _classes = MutableLiveData<List<ClassResponse>?>()
    val classes: LiveData<List<ClassResponse>?> get() = _classes


    fun getData() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            _classes.postValue(getClassesInteract.execute(Interact.Param()))
        }
    }

}