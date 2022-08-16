package com.wit.findmypharmacy.model

data class Pharmacy(
		val address: Address,
		val hours: String?,
		val id: String,
		val name: String,
		val phoneNumber: String?
) {
	data class Address(
			val city: String,
			val latitude: Double,
			val longitude: Double,
			val state: String,
			val streetNumberAndName: String,
			val zipCode: String
	)
}