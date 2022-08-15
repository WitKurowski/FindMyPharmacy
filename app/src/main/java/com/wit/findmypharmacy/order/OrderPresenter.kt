package com.wit.findmypharmacy.order

import com.wit.findmypharmacy.core.Presenter
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class OrderPresenter @Inject constructor(stateEventBus: EventBus) :
		Presenter<Event, State>(stateEventBus) {
	override fun on(event: Event) {
	}
}