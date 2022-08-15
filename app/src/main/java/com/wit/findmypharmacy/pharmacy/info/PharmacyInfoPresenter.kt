package com.wit.findmypharmacy.pharmacy.info

import com.wit.findmypharmacy.core.Presenter
import com.wit.findmypharmacy.repository.PharmacyRepository
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class PharmacyInfoPresenter @Inject constructor(
		private val pharmacyRepository: PharmacyRepository, stateEventBus: EventBus
) : Presenter<Event, State>(stateEventBus) {
	override fun on(event: Event) {
		when (event) {
			is StartedEvent -> onStartedEvent(event.pharmacyId)
		}
	}

	private fun onStartedEvent(pharmacyId: String) {
		val pharmacy = pharmacyRepository.get(pharmacyId)
		val addressApiModel = pharmacy.addressApiModel!!
		val streetNumberAndName = addressApiModel.streetNumberAndName
		val city = addressApiModel.city
		val state = addressApiModel.state
		// Note: This format could be extracted out for reuse and for localization.
		val zipCode = addressApiModel.zipCode
		val address = "$streetNumberAndName\n$city, $state\n$zipCode"
		val name = pharmacy.name
		val pharmacyState = PharmacyState(address, name)
		show(pharmacyState)
	}
}