package com.premifysas.premifyapp.ui.newraffle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.premifysas.premifyapp.model.Raffle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewRaffleViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {


    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess


    fun createRaffle(raffle: Raffle) {
        viewModelScope.launch {
            _isSaving.value = true
            Firebase.firestore.collection("raffle")
                .add(raffle)
                .addOnSuccessListener { documentRef ->
                    // Guarda el documentId dentro del objeto si lo necesitas
                    val updatedRaffle = raffle.copy(id = documentRef.id)
                    documentRef.set(updatedRaffle) // Actualiza el documento con el ID
                    _saveSuccess.value = true
                    _isSaving.value = false
                }
                .addOnFailureListener {
                    _isSaving.value = false
                }
        }
    }

    fun resetState() {
        _saveSuccess.value = false
    }
    }
