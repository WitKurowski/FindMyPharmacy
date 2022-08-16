package com.wit.findmypharmacy.datasource.local

import com.wit.findmypharmacy.datasource.local.model.OrderDatabaseModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides all local functions related to orders.
 */
@Singleton
class OrderLocalDataSource @Inject constructor() {
	// Note: This would be ideally saved into a persistent database (e.g., Room).
	private val orderDatabaseModels = mutableListOf<OrderDatabaseModel>()

	/**
	 * Returns all stored orders.
	 *
	 * @return All stored orders.
	 */
	fun get(): List<OrderDatabaseModel> {
		return orderDatabaseModels
	}

	/**
	 * Stores the given order.
	 *
	 * @param orderDatabaseModel The order to store.
	 */
	fun saveOrder(orderDatabaseModel: OrderDatabaseModel) {
		orderDatabaseModels.add(orderDatabaseModel)
	}
}