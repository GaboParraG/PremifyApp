package com.premifysas.premifyapp.ui.raffleDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.State
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class RafflesDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _usedNumbers = mutableStateOf(setOf<Int>())
    val usedNumbers: State<Set<Int>> = _usedNumbers

    fun loadUsedNumbers(raffleName: String) {
        viewModelScope.launch {
            try {
                val firestore = FirebaseFirestore.getInstance()
                val snapshot = firestore
                    .collection("confirmed_raffles")
                    .document(raffleName)
                    .collection("entries")
                    .get()
                    .await()

                val allUsedNumbers = snapshot.documents
                    .flatMap { it.get("selectedNumbers") as? List<Long> ?: emptyList() }
                    .map { it.toInt() }
                    .toSet()

                _usedNumbers.value = allUsedNumbers
            } catch (e: Exception) {
                e.printStackTrace()
                _usedNumbers.value = emptySet()
            }
        }
    }
}