package com.wit.findmypharmacy.repository

import com.wit.findmypharmacy.datasource.local.OrderLocalDataSource
import com.wit.findmypharmacy.model.OrderDatabaseModel
import javax.inject.Inject

/**
 * The repository providing all functionality related to orders.
 */
class OrderRepository @Inject constructor(private val orderLocalDataSource: OrderLocalDataSource) {
	/**
	 * Fetch all orders that have been made.
	 *
	 * @return All the order that have previously been made.
	 */
	fun get(): List<OrderDatabaseModel> {
		val orderDatabaseModels = orderLocalDataSource.get()

		return orderDatabaseModels
	}

	/**
	 * Sets up a new order.
	 *
	 * @param pharmacyApiModelId The ID of the pharmacy to make the order from.
	 * @param medications The medications to order.
	 */
	fun order(pharmacyApiModelId: String, medications: List<String>) {
		val orderDatabaseModel = OrderDatabaseModel(pharmacyApiModelId, medications)

		orderLocalDataSource.saveOrder(orderDatabaseModel)
	}
}