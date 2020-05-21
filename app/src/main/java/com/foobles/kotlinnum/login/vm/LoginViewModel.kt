package com.foobles.kotlinnum.login.vm

import android.text.TextUtils
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foobles.kotlinnum.login.repo.LoginRepo
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val loginRepo = LoginRepo()
    var email:String = ""


    var validate: Pair<String?, String?>? = Pair(null, null)

    var authData: MediatorLiveData<AuthResult>? = MediatorLiveData()

    fun signOut() = loginRepo.signOut()

    fun reloadUser() = viewModelScope.launch { loginRepo.reload() }

    fun getCurrentUser() = loginRepo.currentUser

    fun signUp(email: String, password: String) {
        if (validateForm(email, password)) {
            viewModelScope.launch {
                val authResult = loginRepo.createAccount(email, password)
                authData?.value = authResult
            }
        }
    }

    fun signIn(email: String, password: String) {
        if (validateForm(email, password)) {
            viewModelScope.launch {
                val authResult = loginRepo.accessAccount(email, password)
                authData?.value = authResult
            }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        var valid = true

        val isEmailValid = if (TextUtils.isEmpty(email)) {
            valid = false
            "Required."
        } else {
            null
        }

        val isPasswordValid = if (TextUtils.isEmpty(password)) {
            valid = false
            "Required."
        } else {
            null
        }

        validate = Pair(isEmailValid, isPasswordValid)

        return valid
    }

}