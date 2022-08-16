package com.wit.findmypharmacy.repository

import com.wit.findmypharmacy.datasource.remote.MedicationRemoteDataSource
import com.wit.findmypharmacy.model.Medication
import javax.inject.Inject

/**
 * The repository providing all functionality related to medications.
 */
class MedicationRepository @Inject constructor(private val medicationRemoteDataSource: MedicationRemoteDataSource) {
	/**
	 * Fetch all medications that are available for ordering.
	 *
	 * @return All medications that are available for ordering.
	 */
	fun get(): List<Medication> {
		val medicationRemoteModels = medicationRemoteDataSource.get()
		val medications = medicationRemoteModels.map {
			Medication(name = it.name)
		}

		return medications
	}
}