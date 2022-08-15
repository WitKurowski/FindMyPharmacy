package com.wit.findmypharmacy.pharmacy.info

sealed class Event

data class StartedEvent(val pharmacyId: String) : Event()

sealed class State