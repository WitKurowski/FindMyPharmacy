package com.wit.findmypharmacy.model

import com.google.gson.annotations.SerializedName

data class PharmacyApiModel(
		@SerializedName("address") val addressApiModel: AddressApiModel?,
		@SerializedName("id") val id: String,
		@SerializedName("name") val name: String,
		@SerializedName("primaryPhoneNumber") val phoneNumber: String?
) {
	data class AddressApiModel(
			@SerializedName("city") val city: String,
			@SerializedName("usTerritory") val state: String,
			@SerializedName("streetAddress1") val streetNumberAndName: String,
			@SerializedName("postalCode") val zipCode: String
	)
}