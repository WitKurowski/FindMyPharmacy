package com.wit.findmypharmacy.util

import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

object ExceptionUtils {
	fun generateHttpException(): HttpException {
		val responseBody = ResponseBody.create(null, "")
		val response = Response.error<Any>(
				HttpURLConnection.HTTP_NOT_FOUND, responseBody
		)
		val httpException = HttpException(response)

		return httpException
	}
}