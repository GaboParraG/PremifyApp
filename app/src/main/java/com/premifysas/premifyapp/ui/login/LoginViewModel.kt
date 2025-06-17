package com.premifysas.premifyapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isAdmin = MutableStateFlow<Boolean?>(null)
    val isAdmin: StateFlow<Boolean?> = _isAdmin

    fun onLoginChanged(email: String, password:String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidEmail(email: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(password: String): Boolean = password.length > 6

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _loginState.value = "success"
            } catch (e: Exception) {
                _loginState.value = "error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun checkUserSession(): Boolean = auth.currentUser != null

    fun logout() = auth.signOut()

    fun clearLoginState() {
        _loginState.value = null
    }

    fun checkIfUserIsAdmin() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            Firebase.firestore.collection("users")
                .document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    val adminValue = document.getBoolean("admin") ?: false
                    _isAdmin.value = adminValue
                }
                .addOnFailureListener {
                    _isAdmin.value = false // En caso de error, asumimos que no es admin
                }
        } ?: run {
            _isAdmin.value = false
        }
    }

}