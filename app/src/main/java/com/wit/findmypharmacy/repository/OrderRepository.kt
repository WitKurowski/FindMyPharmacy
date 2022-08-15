package com.wit.findmypharmacy.repository

import com.wit.findmypharmacy.datasource.local.OrderLocalDataSource
import com.wit.findmypharmacy.model.OrderDatabaseModel
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderLocalDataSource: OrderLocalDataSource) {
	fun get(): List<OrderDatabaseModel> {
		val orderDatabaseModels = orderLocalDataSource.get()

		return orderDatabaseModels
	}

	fun order(pharmacyApiModelId: String, medications: List<String>) {
		val orderDatabaseModel = OrderDatabaseModel(pharmacyApiModelId, medications)

		orderLocalDataSource.saveOrder(orderDatabaseModel)
	}
}