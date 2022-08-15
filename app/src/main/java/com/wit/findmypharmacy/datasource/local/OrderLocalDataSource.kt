package com.wit.findmypharmacy.datasource.local

import com.wit.findmypharmacy.model.OrderDatabaseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderLocalDataSource @Inject constructor() {
	// This would be nicer saved into a Room database.
	private val orders = mutableListOf<OrderDatabaseModel>()

	fun get(): List<OrderDatabaseModel> {
		return orders
	}

	fun saveOrder(orderDatabaseModel: OrderDatabaseModel) {
		orders.add(orderDatabaseModel)
	}
}