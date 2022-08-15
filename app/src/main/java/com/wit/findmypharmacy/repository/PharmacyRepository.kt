package com.wit.findmypharmacy.repository

import com.wit.findmypharmacy.api.PharmacyApi
import com.wit.findmypharmacy.model.PharmacyApiModel
import retrofit2.HttpException
import javax.inject.Inject

class PharmacyRepository @Inject constructor(private val pharmacyApi: PharmacyApi) {
	fun get(): List<PharmacyApiModel> {
		// TODO: Retrieve from file instead
		val pharmacyIds = listOf("NRxPh-HLRS", "NRxPh-BAC1", "NRxPh-SJC1", "NRxPh-ZEREiaYq")

		// TODO: Kick off concurrent coroutines for each
		val pharmacies = pharmacyIds.map {
			get(it)
		}

		return pharmacies
	}

	fun get(id: String): PharmacyApiModel {
		// TODO: Extract into PharmacyRemoteDataSource class.
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