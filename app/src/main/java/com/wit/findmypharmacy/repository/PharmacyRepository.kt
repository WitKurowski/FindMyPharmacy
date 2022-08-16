package com.wit.findmypharmacy.repository

import com.wit.findmypharmacy.api.model.PharmacyApiModel
import com.wit.findmypharmacy.datasource.remote.PharmacyRemoteDataSource
import com.wit.findmypharmacy.model.Pharmacy
import javax.inject.Inject

/**
 * The repository providing all functionality related to pharmacies.
 */
class PharmacyRepository @Inject constructor(private val pharmacyRemoteDataSource: PharmacyRemoteDataSource) {
	private fun convert(addressApiModel: PharmacyApiModel.AddressApiModel): Pharmacy.Address {
		val city = addressApiModel.city
		val latitude = addressApiModel.latitude
		val longitude = addressApiModel.longitude
		val state = addressApiModel.state
		val streetNumberAndName = addressApiModel.streetNumberAndName
		val zipCode = addressApiModel.zipCode
		val address = Pharmacy.Address(
				city, latitude, longitude, state, streetNumberAndName, zipCode
		)
		return address
	}

	private fun convert(pharmacyApiModel: PharmacyApiModel): Pharmacy {
		val addressApiModel = pharmacyApiModel.addressApiModel
		val address = convert(addressApiModel)
		val hours = pharmacyApiModel.hours
		val id = pharmacyApiModel.id
		val name = pharmacyApiModel.name
		val phoneNumber = pharmacyApiModel.phoneNumber
		val pharmacy = Pharmacy(
				address, hours, id, name, phoneNumber
		)

		return pharmacy
	}

	/**
	 * Fetches all pharmacies that can be ordered from.
	 *
	 * @return All pharmacies that can be ordered from.
	 */
	fun get(): List<Pharmacy> {
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
	 *
	 * @param id The ID of the pharmacy to search for.
	 * @return The pharmacy matching the ID.
	 */
	fun get(id: String): Pharmacy {
		val pharmacyApiModel = pharmacyRemoteDataSource.get(id)
		val pharmacy = convert(pharmacyApiModel)

		return pharmacy
	}
}