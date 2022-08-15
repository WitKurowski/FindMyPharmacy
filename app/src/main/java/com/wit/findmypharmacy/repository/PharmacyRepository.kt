package com.wit.findmypharmacy.repository

import com.wit.findmypharmacy.api.PharmacyApi
import com.wit.findmypharmacy.model.Pharmacy
import retrofit2.HttpException
import javax.inject.Inject

class PharmacyRepository @Inject constructor(private val pharmacyApi: PharmacyApi) {
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

	fun get(id: String): Pharmacy {
		val call = pharmacyApi.getPharmacy(id)
		val response = call.execute()
		val pharmacy = if (response.isSuccessful) {
			val pharmacyResponse = response.body()!!
			pharmacyResponse.pharmacy
		} else {
			throw HttpException(response)
		}

		return pharmacy
	}
}