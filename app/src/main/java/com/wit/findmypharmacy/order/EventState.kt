package com.wit.findmypharmacy.order

sealed class Event

data class MedicationClicked(val medication: String, val checked: Boolean) : Event()

object StartedEvent : Event()

sealed class State

data class MedicationsState(val medicationUiStates: List<MedicationUiState>) : State()

data class MedicationUiState(val checked: Boolean, val name: String)

data class PharmacyNameState(val name: String) : State()