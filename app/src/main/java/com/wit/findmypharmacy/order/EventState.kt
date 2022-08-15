package com.wit.findmypharmacy.order

sealed class Event

object StartedEvent : Event()

sealed class State

data class MedicationsState(val medications: List<String>) : State()