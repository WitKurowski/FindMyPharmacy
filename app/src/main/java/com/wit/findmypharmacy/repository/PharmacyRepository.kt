package com.wit.findmypharmacy.repository

import com.wit.findmypharmacy.model.Pharmacy
import javax.inject.Inject

class PharmacyRepository @Inject constructor() {
	fun get(): List<Pharmacy> {
		// TODO: Retrieve from file instead
		val pharmacies = listOf(
				Pharmacy("NRxPh-HLRS", "ReCept"),
				Pharmacy("NRxPh-BAC1", "My Community Pharmacy"),
				Pharmacy("NRxPh-SJC1", "MedTime Pharmacy"),
				Pharmacy("NRxPh-ZEREiaYq", "NY Pharmacy")
		)

		return pharmacies
	}
}