package com.premifysas.premifyapp.ui.raffles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.premifysas.premifyapp.model.Raffle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RafflesViewModel@Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {

    private val _raffles = MutableStateFlow<List<Raffle>>(emptyList())
    val raffles = _raffles.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _activeRaffles = MutableStateFlow<List<Raffle>>(emptyList())
    val activeRaffles: StateFlow<List<Raffle>> = _activeRaffles


    init {
        loadRaffles()
    }

    fun loadActiveRaffles() {
        _isLoading.value = true
        Firebase.firestore.collection("raffle")
            .whereEqualTo("status", true) // 👈 Solo rifas activas
            .get()
            .addOnSuccessListener { result ->
                val raffleList = result.map { document ->
                    val raffle = document.toObject(Raffle::class.java)
                    raffle.id = document.id
                    raffle
                }
                _activeRaffles.value = raffleList
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
    }

    fun loadRaffles() {
        _isLoading.value = true
        Firebase.firestore.collection("raffle")
            .get()
            .addOnSuccessListener { result ->
                val raffleList = result.map { document ->
                    val raffle = document.toObject(Raffle::class.java)
                    raffle.id = document.id // 👈 Aquí asignamos el documentId
                    raffle
                }
                _raffles.value = raffleList
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
    }

    fun deleteRaffle(raffleId: String) {
        viewModelScope.launch {
            Firebase.firestore.collection("raffle")
                .document(raffleId)
                .delete()
                .addOnSuccessListener {
                    loadRaffles()
                }
                .addOnFailureListener {
                    Log.e("DeleteRaffle", "Error al eliminar: ${it.message}")
                }
        }
    }

}