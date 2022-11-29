package com.hust.attandance.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hust.attandance.interact.CheckCurrentUserInteract
import com.hust.attandance.interact.Interact
import com.hust.attandance.ui.common.BaseViewModel
import com.hust.attandance.utils.exceptions.ExceptionHandler
import com.hust.attandance.utils.lifecycle.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val checkCurrentUserInteract: CheckCurrentUserInteract,
    exceptionHandler: ExceptionHandler
) : BaseViewModel(exceptionHandler) {

    private val _navToMain = SingleLiveEvent<Boolean>()
    val navToMain: LiveData<Boolean> get() = _navToMain

    init {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val user = checkCurrentUserInteract.execute(Interact.Param())
            _navToMain.postValue(user != null)
        }
    }
}