package com.hust.attandance.ui.student

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hust.attandance.interact.GetClassInteract
import com.hust.attandance.interact.Interact
import com.hust.attandance.ui.common.BaseViewModel
import com.hust.attandance.utils.exceptions.ExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddStudentViewModel(
    exceptionHandler: ExceptionHandler,
    private val getClassInteract: GetClassInteract
) :
    BaseViewModel(exceptionHandler) {
        init {
            viewModelScope.launch(Dispatchers.IO+ handler){
               val rrr= getClassInteract.execute(Interact.Param())
                Log.d("zxcvbnm,.",rrr.toString())
                Log.d("zxcvbnm,.",rrr?.size.toString())
            }
        }
}