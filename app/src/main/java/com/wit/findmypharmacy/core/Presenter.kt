package com.wit.findmypharmacy.core

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class Presenter<Event, State>(private val stateEventBus: EventBus) {
	private val eventEventBus = EventBus.getDefault()

	protected abstract fun on(event: Event)

	@Subscribe(threadMode = ThreadMode.BACKGROUND)
	fun onInternal(event: Event) {
		on(event)
	}

	fun register(subscriber: Any) {
		eventEventBus.register(this)
		stateEventBus.register(subscriber)
	}

	fun send(event: Event) {
		eventEventBus.post(event)
	}

	protected fun show(state: State) {
		stateEventBus.post(state)
	}

	fun unregister(subscriber: Any) {
		eventEventBus.unregister(this)
		stateEventBus.unregister(subscriber)
	}
}