package com.wit.findmypharmacy.pharmacy.list

import androidx.annotation.StringRes
import com.wit.findmypharmacy.R
import com.wit.findmypharmacy.core.Presenter
import com.wit.findmypharmacy.repository.OrderRepository
import com.wit.findmypharmacy.repository.PharmacyRepository
import org.greenrobot.eventbus.EventBus
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PharmacyListPresenter @Inject constructor(
		private val orderRepository: OrderRepository,
		private val pharmacyRepository: PharmacyRepository,
		stateEventBus: EventBus
) : Presenter<Event, State>(stateEventBus) {
	override fun on(event: Event) {
		when (event) {
			StartedEvent -> onStartedEvent()
		}
	}

	private fun onStartedEvent() {
		showProgressIndicatorState(visible = true)

		try {
			// TODO: Perform these 2 calls simultaneously with coroutines
			val pharmacies = pharmacyRepository.get()
			val orders = orderRepository.get()
			val pharmacyUiStates = pharmacies.map { pharmacy ->
				val checked = orders.any {
					it.pharmacyId == pharmacy.id
				}
				val id = pharmacy.id
				val name = pharmacy.name
				PharmacyUiState(checked, id, name)
			}
			val pharmaciesState = PharmaciesState(pharmacyUiStates)
			show(pharmaciesState)
		} catch (httpException: HttpException) {
			showToastState(R.string.failed_to_retrieve_pharmacies_and_orders)
		} catch (ioException: IOException) {
			showToastState(R.string.failed_to_retrieve_pharmacies_and_orders)
		}

		showProgressIndicatorState(visible = false)
	}

	private fun showProgressIndicatorState(visible: Boolean) {
		val progressIndicatorState = ProgressIndicatorState(visible)
		show(progressIndicatorState)
	}

	private fun showToastState(@StringRes messageStringResId: Int) {
		val toastState = ToastState(messageStringResId)
		show(toastState)
	}
}