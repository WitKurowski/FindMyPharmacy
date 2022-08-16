package com.wit.findmypharmacy.datasource.remote

import com.wit.findmypharmacy.api.PharmacyApi
import com.wit.findmypharmacy.api.model.PharmacyApiModel
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides all remote functions related to pharmacies.
 */
@Singleton
class PharmacyRemoteDataSource @Inject constructor(private val pharmacyApi: PharmacyApi) {
	/**
	 * Searches for a specific pharmacy by ID.
	 *
	 * @param id The ID of the pharmacy to search for.
	 * @return The pharmacy matching the ID.
	 */
	fun get(id: String): PharmacyApiModel {
		val call = pharmacyApi.getPharmacy(id)
		val response = call.execute()
		val pharmacyApiModel = if (response.isSuccessful) {
			val pharmacyResponse = response.body()!!
			pharmacyResponse.pharmacyApiModel
		} else {
			throw HttpException(response)
		}

		return pharmacyApiModel
	}
}