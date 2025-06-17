package com.premifysas.premifyapp.ui.singup

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore // opcional si guardas más datos
) :ViewModel() {
    private val _registerState = MutableStateFlow<String?>(null)
    val registerState = _registerState.asStateFlow()

    fun register(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val userMap = mapOf(
                            "uid" to uid,
                            "email" to email,
                            "name" to name
                        )
                        firestore.collection("users").document(uid).set(userMap)
                    }
                    _registerState.value = "registered"
                } else {
                    _registerState.value = "error"
                }
            }
    }

}