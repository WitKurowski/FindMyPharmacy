package com.wit.findmypharmacy.repository

import java.net.URL
import javax.inject.Inject

/**
 * The repository providing all functionality related to medications.
 */
class MedicationRepository @Inject constructor() {
	/**
	 * Fetch all medications that are available for ordering.
	 *
	 * @return All medications that are available for ordering.
	 */
	fun get(): List<String> {
		// TODO: Extract into MedicationRemoteDataSource class.
		val url = URL(
				"https",
				"s3-us-west-2.amazonaws.com/assets.nimblerx.com/prod/medicationListFromNIH",
				"medicationListFromNIH.txt"
		)
		val string = url.readText()
		val regex = Regex(",\\s*")
		val medicationNames = string.split(regex)

		// TODO: Convert to Medication objects

		return medicationNames
	}
}