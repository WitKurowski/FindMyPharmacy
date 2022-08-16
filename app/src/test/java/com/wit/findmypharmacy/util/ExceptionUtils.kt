package com.wit.findmypharmacy.util

import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

object ExceptionUtils {
	private val RESPONSE_BODY = ResponseBody.create(null, "")
	private val RESPONSE = Response.error<Any>(
			HttpURLConnection.HTTP_NOT_FOUND, RESPONSE_BODY
	)
	val HTTP_EXCEPTION = HttpException(RESPONSE)
}