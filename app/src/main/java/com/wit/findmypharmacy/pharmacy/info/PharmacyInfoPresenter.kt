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
		val name = pharmacy.name
		val pharmacyState = PharmacyState(name)
		show(pharmacyState)
	}
}