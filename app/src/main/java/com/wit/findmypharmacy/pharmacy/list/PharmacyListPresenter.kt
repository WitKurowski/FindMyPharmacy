package com.wit.findmypharmacy.pharmacy.list

import com.wit.findmypharmacy.core.Presenter
import com.wit.findmypharmacy.repository.PharmacyRepository
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class PharmacyListPresenter @Inject constructor(
		private val pharmacyRepository: PharmacyRepository, stateEventBus: EventBus
) : Presenter<Event, State>(stateEventBus) {
	override fun on(event: Event) {
		when (event) {
			StartedEvent -> onStartedEvent()
		}
	}

	private fun onStartedEvent() {
		val pharmacies = pharmacyRepository.get()
		val pharmaciesState = PharmaciesState(pharmacies)

		show(pharmaciesState)
	}
}