package com.wit.findmypharmacy.order

import com.wit.findmypharmacy.core.Presenter
import com.wit.findmypharmacy.repository.MedicationRepository
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class OrderPresenter @Inject constructor(
		private val medicationRepository: MedicationRepository, stateEventBus: EventBus
) : Presenter<Event, State>(stateEventBus) {
	override fun on(event: Event) {
		when (event) {
			StartedEvent -> onStartedEvent()
		}
	}

	private fun onStartedEvent() {
		val medications = medicationRepository.get()
		show(MedicationsState(medications))
	}
}