package com.wit.findmypharmacy.pharmacy.list

import com.wit.findmypharmacy.repository.PharmacyRepository
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class PharmacyListPresenter @Inject constructor(
		private val pharmacyRepository: PharmacyRepository, private val stateEventBus: EventBus
) {
	private val eventEventBus = EventBus.getDefault()

	@Subscribe(threadMode = ThreadMode.BACKGROUND)
	fun on(event: Event) {
		when (event) {
			StartedEvent -> onStartedEvent()
		}
	}

	private fun onStartedEvent() {
		val pharmacies = pharmacyRepository.get()
		val pharmaciesState = PharmaciesState(pharmacies)

		show(pharmaciesState)
	}

	fun register(subscriber: Any) {
		eventEventBus.register(this)
		stateEventBus.register(subscriber)
	}

	fun send(event: Event) {
		eventEventBus.post(event)
	}

	private fun show(state: State) {
		stateEventBus.post(state)
	}

	fun unregister(subscriber: Any) {
		eventEventBus.unregister(this)
		stateEventBus.unregister(subscriber)
	}
}