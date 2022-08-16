package com.wit.findmypharmacy.pharmacy.info

import androidx.annotation.StringRes
import com.wit.findmypharmacy.R
import com.wit.findmypharmacy.core.Presenter
import com.wit.findmypharmacy.repository.OrderRepository
import com.wit.findmypharmacy.repository.PharmacyRepository
import org.greenrobot.eventbus.EventBus
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PharmacyInfoPresenter @Inject constructor(
		private val orderRepository: OrderRepository,
		private val pharmacyRepository: PharmacyRepository,
		stateEventBus: EventBus
) : Presenter<Event, State>(stateEventBus) {
	override fun on(event: Event) {
		when (event) {
			is StartedEvent -> onStartedEvent(event.pharmacyId)
		}
	}

	private fun onStartedEvent(pharmacyId: String) {
		showProgressIndicatorState(visible = true)

		val hoursLabelState = HoursLabelState(visible = false)
		show(hoursLabelState)

		showOrderedMedicationsLabelState(visible = false)

		try {
			val pharmacy = pharmacyRepository.get(pharmacyId)
			val address = pharmacy.address
			val streetNumberAndName = address.streetNumberAndName
			val city = address.city
			val state = address.state
			val zipCode = address.zipCode
			// Note: This format should be extracted out for reuse and for localization.
			val addressString = "$streetNumberAndName\n$city, $state\n$zipCode"

			// Replace needed because some of the hour strings have "\n" as two characters rather than
			// the '\n' character.
			val newLineStringWithWhitespaceRegex = Regex("\\s*\\\\n\\s*")
			val hours = pharmacy.hours?.replace(newLineStringWithWhitespaceRegex, "\n")

			val name = pharmacy.name
			val phoneNumber = pharmacy.phoneNumber
			val pharmacyState = PharmacyState(addressString, hours, name, phoneNumber)
			show(pharmacyState)

			val visible = hours != null && hours.isNotBlank()
			val updatedHoursLabelState = hoursLabelState.copy(visible = visible)
			show(updatedHoursLabelState)
		} catch (httpException: HttpException) {
			showToastState(R.string.failed_to_retrieve_pharmacy)
		} catch (ioException: IOException) {
			showToastState(R.string.failed_to_retrieve_pharmacy)
		}

		try {
			val orders = orderRepository.get()
			val matchingOrder = orders.find {
				it.pharmacyId == pharmacyId
			}

			if (matchingOrder != null) {
				val medications = matchingOrder.medications
				val medicationsState = MedicationsState(medications)
				show(medicationsState)

				showOrderedMedicationsLabelState(visible = true)
			}
		} catch (httpException: HttpException) {
			showToastState(R.string.failed_to_retrieve_order_history)
		} catch (ioException: IOException) {
			showToastState(R.string.failed_to_retrieve_order_history)
		}

		showProgressIndicatorState(visible = false)
	}

	private fun showOrderedMedicationsLabelState(visible: Boolean) {
		val orderedMedicationsLabelState = OrderedMedicationsLabelState(visible)
		show(orderedMedicationsLabelState)
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