package com.wit.findmypharmacy.repository

import com.wit.findmypharmacy.api.PharmacyApi
import com.wit.findmypharmacy.model.PharmacyApiModel
import retrofit2.HttpException
import javax.inject.Inject

class PharmacyRepository @Inject constructor(private val pharmacyApi: PharmacyApi) {
	fun get(): List<PharmacyApiModel> {
		// TODO: Retrieve from file instead
		val pharmacies = listOf(
				PharmacyApiModel(null, "NRxPh-HLRS", "ReCept"),
				PharmacyApiModel(null, "NRxPh-BAC1", "My Community Pharmacy"),
				PharmacyApiModel(null, "NRxPh-SJC1", "MedTime Pharmacy"),
				PharmacyApiModel(null, "NRxPh-ZEREiaYq", "NY Pharmacy")
		)

		return pharmacies
	}

	fun get(id: String): PharmacyApiModel {
		val call = pharmacyApi.getPharmacy(id)
		val response = call.execute()
		val pharmacy = if (response.isSuccessful) {
			val pharmacyResponse = response.body()!!
			pharmacyResponse.pharmacyApiModel
		} else {
			throw HttpException(response)
		}

		return pharmacy
	}
}