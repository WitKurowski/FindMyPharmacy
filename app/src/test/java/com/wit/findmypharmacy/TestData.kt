package com.wit.findmypharmacy

import com.wit.findmypharmacy.model.Pharmacy

/**
 * Stores commonly reused objects for the tests that reflect real-world data.
 */
object TestData {
	private const val addressCity1 = "SEATTLE"
	private const val addressState1 = "WA"
	private const val addressStateStreetNumberAndName1 = "605 1ST AVE"
	private const val addressZipCode1 = "98104"
	private val address1 = Pharmacy.Address(
			city = addressCity1,
			latitude = 47.60174179077148,
			longitude = -122.33425903320313,
			state = addressState1,
			streetNumberAndName = addressStateStreetNumberAndName1,
			zipCode = addressZipCode1
	)
	private const val pharmacyHours1 =
		"9:00am-7:00pm Mon-Fri \\n  9:00a - 5:00p Sat \\n 10:00a- 5:00p Sun"
	const val pharmacyId1 = "NRxPh-HLRS"
	const val pharmacyName1 = "ReCept"
	private const val pharmacyPhoneNumber1 = "+12062841353"
	val pharmacy1 = Pharmacy(
			address = address1,
			hours = pharmacyHours1,
			id = pharmacyId1,
			name = pharmacyName1,
			phoneNumber = pharmacyPhoneNumber1
	)
	private const val addressCity2 = "San Francisco"
	private const val addressState2 = "CA"
	private const val addressStateStreetNumberAndName2 = "1501 8th Ave # A"
	private const val addressZipCode2 = "94122"
	private val address2 = Pharmacy.Address(
			city = addressCity2,
			latitude = 37.76015540000000,
			longitude = -122.46518950000000,
			state = addressState2,
			streetNumberAndName = addressStateStreetNumberAndName2,
			zipCode = addressZipCode2
	)
	private const val pharmacyHours2 = "9:00a-7:30p Mon-Fri \\n 10:00a-4:00p Sat"
	const val pharmacyId2 = "NRxPh-BAC1"
	const val pharmacyName2 = "My Community Pharmacy"
	val pharmacyPhoneNumber2 = null
	val pharmacy2 = Pharmacy(
			address = address2,
			hours = pharmacyHours2,
			id = pharmacyId2,
			name = pharmacyName2,
			phoneNumber = pharmacyPhoneNumber2
	)
	private const val addressCity3 = "REDWOOD CITY"
	private const val addressState3 = "CA"
	private const val addressStateStreetNumberAndName3 = "2317 BROADWAY ST"
	private const val addressZipCode3 = "94063"
	private val address3 = Pharmacy.Address(
			city = addressCity3,
			latitude = 37.48632812500000,
			longitude = -122.23024749755859,
			state = addressState3,
			streetNumberAndName = addressStateStreetNumberAndName3,
			zipCode = addressZipCode3
	)
	private const val pharmacyHours3 =
		"9:00am-7:00pm Mon-Fri \\n  9:00a - 5:00p Sat \\n 10:00a- 5:00p Sun"
	const val pharmacyId3 = "NRxPh-SJC1"
	const val pharmacyName3 = "MedTime Pharmacy"
	const val pharmacyPhoneNumber3 = "+16508894199"
	val pharmacy3 = Pharmacy(
			address = address3,
			hours = pharmacyHours3,
			id = pharmacyId3,
			name = pharmacyName3,
			phoneNumber = pharmacyPhoneNumber3
	)
	private const val addressCity4 = "NEW YORK"
	private const val addressState4 = "NY"
	private const val addressStateStreetNumberAndName4 = "3 E 52ND ST"
	private const val addressZipCode4 = "10022"
	private val address4 = Pharmacy.Address(
			city = addressCity4,
			latitude = 40.75967025756836,
			longitude = -73.97579193115234,
			state = addressState4,
			streetNumberAndName = addressStateStreetNumberAndName4,
			zipCode = addressZipCode4
	)
	private val pharmacyHours4 = null
	const val pharmacyId4 = "NRxPh-ZEREiaYq"
	const val pharmacyName4 = "NY Pharmacy"
	const val pharmacyPhoneNumber4 = "+12127521495"
	val pharmacy4 = Pharmacy(
			address = address4,
			hours = pharmacyHours4,
			id = pharmacyId4,
			name = pharmacyName4,
			phoneNumber = pharmacyPhoneNumber4
	)
}