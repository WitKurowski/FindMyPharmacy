package com.wit.findmypharmacy.repository

import com.wit.findmypharmacy.api.PharmacyApi
import com.wit.findmypharmacy.model.PharmacyApiModel
import retrofit2.HttpException
import javax.inject.Inject

/**
 * The repository providing all functionality related to pharmacies.
 */
class PharmacyRepository @Inject constructor(private val pharmacyApi: PharmacyApi) {
	/**
	 * Fetches all pharmacies that can be ordered from.
	 *
	 * @return All pharmacies that can be ordered from.
	 */
	fun get(): List<PharmacyApiModel> {
		// TODO: Retrieve from file instead
		val pharmacyIds = listOf("NRxPh-HLRS", "NRxPh-BAC1", "NRxPh-SJC1", "NRxPh-ZEREiaYq")

		// TODO: Kick off concurrent coroutines for each
		val pharmacies = pharmacyIds.map {
			get(it)
		}

		return pharmacies
	}

	/**
	 * Searches for a specific pharmacy by ID.
	 * @param id The ID of the pharmacy to search for.
	 * @return The pharmacy matching the ID.
	 */
	fun get(id: String): PharmacyApiModel {
		// TODO: Extract into PharmacyRemoteDataSource class.
		// TODO: Handle errors such as when the network is unavailable or the ID is invalid.
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