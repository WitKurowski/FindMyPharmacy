package com.wit.findmypharmacy

import com.wit.findmypharmacy.model.PharmacyApiModel

/**
 * Stores commonly reused objects for the tests that reflect real-world data.
 */
object TestData {
	private const val addressApiModelCity1 = "SEATTLE"
	private const val addressApiModelState1 = "WA"
	private const val addressApiModelStateStreetNumberAndName1 = "605 1ST AVE"
	private const val addressApiModelZipCode1 = "98104"
	private val addressApiModel1 = PharmacyApiModel.AddressApiModel(
			city = addressApiModelCity1,
			latitude = 47.60174179077148,
			longitude = -122.33425903320313,
			state = addressApiModelState1,
			streetNumberAndName = addressApiModelStateStreetNumberAndName1,
			zipCode = addressApiModelZipCode1
	)
	private const val pharmacyHours1 =
		"9:00am-7:00pm Mon-Fri \\n  9:00a - 5:00p Sat \\n 10:00a- 5:00p Sun"
	const val pharmacyApiModelId1 = "NRxPh-HLRS"
	const val pharmacyApiModelName1 = "ReCept"
	private const val pharmacyPhoneNumber1 = "+12062841353"
	val pharmacyApiModel1 = PharmacyApiModel(
			addressApiModel = addressApiModel1,
			hours = pharmacyHours1,
			id = pharmacyApiModelId1,
			name = pharmacyApiModelName1,
			phoneNumber = pharmacyPhoneNumber1
	)
	private const val addressApiModelCity2 = "San Francisco"
	private const val addressApiModelState2 = "CA"
	private const val addressApiModelStateStreetNumberAndName2 = "1501 8th Ave # A"
	private const val addressApiModelZipCode2 = "94122"
	private val addressApiModel2 = PharmacyApiModel.AddressApiModel(
			city = addressApiModelCity2,
			latitude = 37.76015540000000,
			longitude = -122.46518950000000,
			state = addressApiModelState2,
			streetNumberAndName = addressApiModelStateStreetNumberAndName2,
			zipCode = addressApiModelZipCode2
	)
	private const val pharmacyHours2 = "9:00a-7:30p Mon-Fri \\n 10:00a-4:00p Sat"
	const val pharmacyApiModelId2 = "NRxPh-BAC1"
	const val pharmacyApiModelName2 = "My Community Pharmacy"
	val pharmacyPhoneNumber2 = null
	val pharmacyApiModel2 = PharmacyApiModel(
			addressApiModel = addressApiModel2,
			hours = pharmacyHours2,
			id = pharmacyApiModelId2,
			name = pharmacyApiModelName2,
			phoneNumber = pharmacyPhoneNumber2
	)
	private const val addressApiModelCity3 = "REDWOOD CITY"
	private const val addressApiModelState3 = "CA"
	private const val addressApiModelStateStreetNumberAndName3 = "2317 BROADWAY ST"
	private const val addressApiModelZipCode3 = "94063"
	private val addressApiModel3 = PharmacyApiModel.AddressApiModel(
			city = addressApiModelCity3,
			latitude = 37.48632812500000,
			longitude = -122.23024749755859,
			state = addressApiModelState3,
			streetNumberAndName = addressApiModelStateStreetNumberAndName3,
			zipCode = addressApiModelZipCode3
	)
	private const val pharmacyHours3 =
		"9:00am-7:00pm Mon-Fri \\n  9:00a - 5:00p Sat \\n 10:00a- 5:00p Sun"
	const val pharmacyApiModelId3 = "NRxPh-SJC1"
	const val pharmacyApiModelName3 = "MedTime Pharmacy"
	const val pharmacyPhoneNumber3 = "+16508894199"
	val pharmacyApiModel3 = PharmacyApiModel(
			addressApiModel = addressApiModel3,
			hours = pharmacyHours3,
			id = pharmacyApiModelId3,
			name = pharmacyApiModelName3,
			phoneNumber = pharmacyPhoneNumber3
	)
	private const val addressApiModelCity4 = "NEW YORK"
	private const val addressApiModelState4 = "NY"
	private const val addressApiModelStateStreetNumberAndName4 = "3 E 52ND ST"
	private const val addressApiModelZipCode4 = "10022"
	private val addressApiModel4 = PharmacyApiModel.AddressApiModel(
			city = addressApiModelCity4,
			latitude = 40.75967025756836,
			longitude = -73.97579193115234,
			state = addressApiModelState4,
			streetNumberAndName = addressApiModelStateStreetNumberAndName4,
			zipCode = addressApiModelZipCode4
	)
	private val pharmacyHours4 = null
	const val pharmacyApiModelId4 = "NRxPh-ZEREiaYq"
	const val pharmacyApiModelName4 = "NY Pharmacy"
	const val pharmacyPhoneNumber4 = "+12127521495"
	val pharmacyApiModel4 = PharmacyApiModel(
			addressApiModel = addressApiModel4,
			hours = pharmacyHours4,
			id = pharmacyApiModelId4,
			name = pharmacyApiModelName4,
			phoneNumber = pharmacyPhoneNumber4
	)
}