package com.wit.findmypharmacy.api

import com.google.gson.annotations.SerializedName
import com.wit.findmypharmacy.api.model.PharmacyApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Provides all REST API-specific functions related to pharmacies.
 */
interface PharmacyApi {
	@GET("/pharmacies/info/{id}")
	fun getPharmacy(@Path("id") id: String): Call<PharmacyResponse>

	data class PharmacyResponse(@SerializedName("value") val pharmacyApiModel: PharmacyApiModel)
}