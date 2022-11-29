package com.hust.attandance.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hust.attandance.data.response.LoginResonse
import com.hust.attandance.interact.Interact
import com.hust.attandance.interact.LogoutInteract
import com.hust.attandance.interact.ProfileInteract
import com.hust.attandance.ui.common.BaseViewModel
import com.hust.attandance.utils.exceptions.ExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileInteract: ProfileInteract,
    private val logoutInteract: LogoutInteract,
    exceptionHandler: ExceptionHandler
) : BaseViewModel(exceptionHandler) {

    private val _profileResponse = MutableLiveData<LoginResonse?>()
    val profileResponse: LiveData<LoginResonse?> get() = _profileResponse

    init {
        viewModelScope.launch(Dispatchers.IO + handler) {
            _profileResponse.postValue(profileInteract.execute(Interact.Param()))
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            logoutInteract.execute(Interact.Param())
        }
    }

}