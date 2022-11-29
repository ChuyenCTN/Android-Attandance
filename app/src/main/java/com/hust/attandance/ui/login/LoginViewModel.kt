package com.hust.attandance.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hust.attandance.data.model.LoginRequest
import com.hust.attandance.interact.LoginInteract
import com.hust.attandance.ui.common.BaseViewModel
import com.hust.attandance.utils.exceptions.ExceptionHandler
import com.hust.attandance.utils.lifecycle.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginInteract: LoginInteract,
    exceptionHandler: ExceptionHandler
) : BaseViewModel(exceptionHandler) {

    var userName = ""
    var password = ""

    private val _loginSuccessful = SingleLiveEvent<Boolean>()
    val loginSuccessful: LiveData<Boolean> get() = _loginSuccessful

    fun login() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            if (userName.isNotEmpty() && password.isNotEmpty()) {
                loginInteract.execute(
                    LoginInteract.Param(
                        LoginRequest(
                            password = password,
                            username = userName
                        )
                    )
                )?.let {
                    _loginSuccessful.postValue(true)
                } ?: kotlin.run { loginSuccessful.postValue(false) }
            } else {
                _loginSuccessful.postValue(false)
            }
        }
    }
}