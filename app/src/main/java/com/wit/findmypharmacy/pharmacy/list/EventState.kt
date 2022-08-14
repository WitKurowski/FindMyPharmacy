package com.wit.findmypharmacy.pharmacy.list

import com.wit.findmypharmacy.model.Pharmacy

sealed class Event

object StartedEvent : Event()

sealed class State

data class PharmaciesState(val pharmacies: List<Pharmacy>) : State()