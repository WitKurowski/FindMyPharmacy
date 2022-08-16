package com.wit.findmypharmacy.datasource.local

import com.wit.findmypharmacy.model.OrderDatabaseModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides all local functions related to orders.
 */
@Singleton
class OrderLocalDataSource @Inject constructor() {
	// Note: This would be ideally saved into a persistent database (e.g., Room).
	private val orders = mutableListOf<OrderDatabaseModel>()

	/**
	 * Returns all stored orders.
	 *
	 * @return All stored orders.
	 */
	fun get(): List<OrderDatabaseModel> {
		return orders
	}

	/**
	 * Stores the given order.
	 *
	 * @param orderDatabaseModel The order to store.
	 */
	fun saveOrder(orderDatabaseModel: OrderDatabaseModel) {
		orders.add(orderDatabaseModel)
	}
}