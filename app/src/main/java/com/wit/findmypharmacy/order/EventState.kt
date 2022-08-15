package com.wit.findmypharmacy.order

import androidx.annotation.StringRes

sealed class Event

object CheckOutClicked : Event()

data class MedicationClicked(val medication: String, val checked: Boolean) : Event()

object StartedEvent : Event()

sealed class State

data class MedicationsState(val medicationUiStates: List<MedicationUiState>) : State()

data class MedicationUiState(val checked: Boolean, val name: String)

object PharmacyListState : State()

data class PharmacyNameState(val name: String) : State()

data class ProgressIndicatorState(val visible: Boolean) : State()

data class ToastState(@StringRes val messageStringResId: Int) : State()