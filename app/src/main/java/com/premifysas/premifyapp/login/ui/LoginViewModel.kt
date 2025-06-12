package com.premifysas.premifyapp.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState = _loginState.asStateFlow()

    private val  _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val  _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val  _loginEnable = MutableLiveData<Boolean>()
    val loginEnable : LiveData<Boolean> = _loginEnable

    fun onLoginChanged(email: String, password:String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidEmail(email: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(password: String): Boolean = password.length > 6

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _loginState.value = if (task.isSuccessful) "success" else "error"
            }
    }

    fun checkUserSession(): Boolean = auth.currentUser != null

    fun logout() = auth.signOut()
}