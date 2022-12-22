package com.hust.attandance.ui.classes

import androidx.lifecycle.viewModelScope
import com.hust.attandance.data.response.LoginResonse
import com.hust.attandance.interact.CreateClassInteract
import com.hust.attandance.ui.common.BaseViewModel
import com.hust.attandance.utils.exceptions.ExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailClassesViewModel(
    exception: ExceptionHandler,
    private val createClassInteract: CreateClassInteract
) : BaseViewModel(exception) {


    init {
        viewModelScope.launch(Dispatchers.IO + handler) {
            createClassInteract.execute(CreateClassInteract.Param(
                mutableListOf(LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                    LoginResonse("Ha Noi","7456784gcg","bacfgi47qv34fvyf","84gyeqrukabferburehe","0589033596",6734,97486,"kjvnjvbsa"),
                )))
        }
    }
}