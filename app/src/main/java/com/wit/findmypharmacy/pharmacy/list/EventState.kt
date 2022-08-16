package com.wit.findmypharmacy.pharmacy.list

import androidx.annotation.StringRes

sealed class Event

object StartedEvent : Event()

sealed class State

data class PharmaciesState(val pharmacyUiStates: List<PharmacyUiState>) : State()

data class PharmacyUiState(val checked: Boolean, val id: String, val name: String)

data class ProgressIndicatorState(val visible: Boolean) : State()

data class ToastState(@StringRes val messageStringResId: Int) : State()