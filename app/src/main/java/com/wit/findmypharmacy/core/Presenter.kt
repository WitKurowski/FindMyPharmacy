package com.wit.findmypharmacy.core

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * The base class for all presenters.  It centralizes and hides all presenter-side logic specific to
 * how communication is handled between a fragment and its presenter.
 */
abstract class Presenter<Event, State>(private val stateEventBus: EventBus) {
	/**
	 * The EventBus used to deliver events from the fragment to the presenter.
	 */
	private val eventEventBus = EventBus.builder().build()

	/**
	 * Handles each possible event sent from the associated fragment.
	 */
	protected abstract fun on(event: Event)

	/**
	 * Entry-point for all events sent from the associated fragment.
	 */
	@Subscribe(threadMode = ThreadMode.BACKGROUND)
	fun onInternal(event: Event) {
		on(event)
	}

	/**
	 * Registers the given object as a receiver of states from this presenter.
	 */
	fun register(subscriber: Any) {
		eventEventBus.register(this)
		stateEventBus.register(subscriber)
	}

	/**
	 * Sends the given event to the event-specific EventBus.
	 */
	fun send(event: Event) {
		eventEventBus.post(event)
	}

	/**
	 * Sends the given state to the state-specific EventBus.
	 */
	protected fun show(state: State) {
		stateEventBus.post(state)
	}

	/**
	 * Unregisters the given object as a receiver of states from this presenter.
	 */
	fun unregister(subscriber: Any) {
		eventEventBus.unregister(this)
		stateEventBus.unregister(subscriber)
	}
}