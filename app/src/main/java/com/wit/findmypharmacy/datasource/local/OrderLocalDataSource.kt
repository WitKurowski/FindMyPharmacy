package com.wit.findmypharmacy.datasource.local

import com.wit.findmypharmacy.model.OrderDatabaseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderLocalDataSource @Inject constructor() {
	// This would be nicer saved into a Room database, although it would make most sense as a set of
	// objects from an orders API.
	private val orders = mutableListOf<OrderDatabaseModel>()

	fun saveOrder(orderDatabaseModel: OrderDatabaseModel) {
		orders.add(orderDatabaseModel)
	}
}