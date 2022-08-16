package com.wit.findmypharmacy.datasource.remote

import com.wit.findmypharmacy.datasource.remote.model.MedicationRemoteModel
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides all remote functions related to medications.
 */
@Singleton
class MedicationRemoteDataSource @Inject constructor() {
	/**
	 * Fetch all medications that are available for ordering.
	 *
	 * @return All medications that are available for ordering.
	 */
	fun get(): List<MedicationRemoteModel> {
		val url = URL(
				"https",
				"s3-us-west-2.amazonaws.com/assets.nimblerx.com/prod/medicationListFromNIH",
				"medicationListFromNIH.txt"
		)
		val string = url.readText()
		val regex = Regex(",\\s*")
		val medicationNames = string.split(regex)
		val medicationRemoteModels = medicationNames.map {
			MedicationRemoteModel(name = it)
		}

		return medicationRemoteModels
	}
}