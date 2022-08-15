package com.wit.findmypharmacy.pharmacy.info

sealed class Event

data class StartedEvent(val pharmacyId: String) : Event()

sealed class State

data class PharmacyState(val address: String, val name: String, val phoneNumber: String) : State()