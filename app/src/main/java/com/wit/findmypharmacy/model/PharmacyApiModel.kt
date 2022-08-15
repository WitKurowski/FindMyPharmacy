package com.wit.findmypharmacy.model

import com.google.gson.annotations.SerializedName

data class PharmacyApiModel(
		@SerializedName("address") val addressApiModel: AddressApiModel?,
		@SerializedName("id") val id: String,
		@SerializedName("name") val name: String
) {
	data class AddressApiModel(@SerializedName("streetAddress1") val streetNumberAndName: String)
}