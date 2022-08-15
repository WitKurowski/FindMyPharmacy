package com.wit.findmypharmacy.pharmacy.info

import com.wit.findmypharmacy.core.Presenter
import com.wit.findmypharmacy.repository.OrderRepository
import com.wit.findmypharmacy.repository.PharmacyRepository
import org.greenrobot.eventbus.EventBus
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
		val hoursLabelState = HoursLabelState(visible = false)
		show(hoursLabelState)

		val pharmacyApiModel = pharmacyRepository.get(pharmacyId)
		val addressApiModel = pharmacyApiModel.addressApiModel!!
		val streetNumberAndName = addressApiModel.streetNumberAndName
		val city = addressApiModel.city
		val state = addressApiModel.state
		// Note: This format could be extracted out for reuse and for localization.
		val zipCode = addressApiModel.zipCode
		val address = "$streetNumberAndName\n$city, $state\n$zipCode"

		// Replace needed because some of the hour strings have "\n" as two characters rather than
		// the '\n' character.
		// TODO: Remove whitespace around newlines
		val hours = pharmacyApiModel.hours?.replace("\\n", "\n")

		val name = pharmacyApiModel.name
		val phoneNumber = pharmacyApiModel.phoneNumber!!
		val pharmacyState = PharmacyState(address, hours, name, phoneNumber)
		show(pharmacyState)

		val visible = hours != null && hours.isNotBlank()
		val updatedHoursLabelState = hoursLabelState.copy(visible = visible)
		show(updatedHoursLabelState)

		val orderDatabaseModels = orderRepository.get()
		val matchingOrderDatabaseModels = orderDatabaseModels.find {
			it.pharmacyApiModelId == pharmacyId
		}

		if (matchingOrderDatabaseModels != null) {
			val medications = matchingOrderDatabaseModels.medications
			val medicationsState = MedicationsState(medications)
			show(medicationsState)
		}
	}
}