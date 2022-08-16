package com.wit.findmypharmacy.repository

import com.wit.findmypharmacy.datasource.local.OrderLocalDataSource
import com.wit.findmypharmacy.datasource.local.model.OrderDatabaseModel
import com.wit.findmypharmacy.model.Order
import javax.inject.Inject

/**
 * The repository providing all functionality related to orders.
 */
class OrderRepository @Inject constructor(private val orderLocalDataSource: OrderLocalDataSource) {
	private fun convert(orderDatabaseModel: OrderDatabaseModel): Order {
		val pharmacyId = orderDatabaseModel.pharmacyApiModelId
		val medications = orderDatabaseModel.medications
		val order = Order(pharmacyId, medications)

		return order
	}

	private fun convert(orderDatabaseModels: List<OrderDatabaseModel>): List<Order> {
		val orders = orderDatabaseModels.map {
			convert(it)
		}

		return orders
	}

	/**
	 * Fetch all orders that have been made.
	 *
	 * @return All the order that have previously been made.
	 */
	fun get(): List<Order> {
		val orderDatabaseModels = orderLocalDataSource.get()
		val orders = convert(orderDatabaseModels)

		return orders
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