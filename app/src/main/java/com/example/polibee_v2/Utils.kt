package com.example.polibee_v2

import java.util.regex.Pattern

fun isValidPassword(password: String): Boolean {
    val pattern = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
    )
    return pattern.matcher(password).matches()

}