package com.wit.findmypharmacy.pharmacy.list

import com.wit.findmypharmacy.model.PharmacyApiModel

sealed class Event

object StartedEvent : Event()

sealed class State

data class PharmaciesState(val pharmacyApiModels: List<PharmacyApiModel>) : State()