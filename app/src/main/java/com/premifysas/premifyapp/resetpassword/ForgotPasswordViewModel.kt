package com.premifysas.premifyapp.resetpassword

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _result = mutableStateOf<String?>(null)
    val result: State<String?> = _result

    fun sendPasswordResetEmail(email: String) {
        if (email.isBlank()) {
            _result.value = "Ingrese un correo válido"
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                _result.value = if (task.isSuccessful) {
                    "Se envió un correo para restablecer la contraseña"
                } else {
                    "Error al enviar el correo. Verifique el correo ingresado."
                }
            }
    }

    fun clearResult() {
        _result.value = null
    }
}