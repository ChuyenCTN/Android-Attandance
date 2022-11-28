package com.hust.attandance.data.exceptions

enum class ExceptionEnum(val value: Int) {
    GENERAL(0),
    UNDEFINED(999),

    // Others
    NO_INTERNET(9001), // When there is no internet connection but user still try to do something with api
    SOCKET_TIMEOUT(9002);


}