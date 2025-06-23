package com.premifysas.premifyapp.ui.confirmraffle

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConfirmRaffleViewModel@Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    private val _isSaving = MutableStateFlow(false)
    val isSaving = _isSaving.asStateFlow()

    private val _saveResult = MutableStateFlow<Boolean?>(null)
    val saveResult: StateFlow<Boolean?> = _saveResult

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage = _errorMessage

    private val _sendEmailEvent = mutableStateOf(false)
    val sendEmailEvent get() = _sendEmailEvent

    fun saveData(
        selectedNumbers: List<Int>,
        name: String,
        phone: String,
        address: String,
        imageUri: Uri?,
        raffleName: String
    ) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                // 👇 Aquí defines si quieres cargar la imagen o no
                val enableImageUpload = false

                // 👇 Esta línea usa esa bandera para decidir si sube la imagen o la ignora
                val imageUrl = if (enableImageUpload && imageUri != null) {
                    uploadImage(imageUri)  // Esta línea aún necesita definición si se activa luego
                } else {
                    null
                }

                val firestore = FirebaseFirestore.getInstance()
                val raffleDoc = firestore.collection("confirmed_raffles").document(raffleName)

                val data = hashMapOf(
                    "name" to name,
                    "phone" to phone,
                    "address" to address,
                    "imageUrl" to imageUrl,
                    "selectedNumbers" to selectedNumbers
                )

                raffleDoc.collection("entries").add(data).await()

                _saveResult.value = true
                _saveResult.value = true
                triggerEmailSend()

            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = e.message
                _saveResult.value = false
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun triggerEmailSend() {
        _sendEmailEvent.value = true
    }

    fun resetEmailEvent() {
        _sendEmailEvent.value = false
    }

    private suspend fun uploadImage(imageUri: Uri): String {
        val storageRef = Firebase.storage.reference
        val fileName = UUID.randomUUID().toString()
        val imageRef = storageRef.child("raffle_images/$fileName")

        // Sube la imagen
        imageRef.putFile(imageUri).await()

        // Obtiene la URL de descarga
        return imageRef.downloadUrl.await().toString()
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}