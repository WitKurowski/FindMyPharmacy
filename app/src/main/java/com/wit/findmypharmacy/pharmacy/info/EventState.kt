package com.wit.findmypharmacy.pharmacy.info

sealed class Event

data class StartedEvent(val pharmacyId: String) : Event()

sealed class State

data class HoursLabelState(val visible: Boolean) : State()

data class MedicationsState(val names: List<String>) : State()

data class OrderedMedicationsLabelState(val visible: Boolean) : State()

data class PharmacyState(
		val address: String, val hours: String?, val name: String, val phoneNumber: String
) : State()