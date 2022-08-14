package com.wit.findmypharmacy.model

import com.google.gson.annotations.SerializedName

data class Pharmacy(@SerializedName("id") val id: String, @SerializedName("name") val name: String)