package com.wit.findmypharmacy.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * The base class for all fragments.  It centralizes and hides all fragment-side logic specific to
 * how communication is handled between a fragment and its presenter.
 */
abstract class Fragment<B, P : Presenter<Event, State>, Event, State> : Fragment() {
	/**
	 * Internal view binding class that is cleaned up when it is no longer needed.
	 */
	private var _binding: B? = null

	/**
	 * The view binding.  Only valid after onCreateView() and before onDestroy().
	 */
	protected val binding get() = _binding!!

	@Inject
	lateinit var presenter: P

	protected abstract fun getBindingRootView(): View

	protected abstract fun inflateBinding(layoutInflater: LayoutInflater, container: ViewGroup?): B

	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		_binding = inflateBinding(inflater, container)
		val rootView = getBindingRootView()

		return rootView
	}

	override fun onDestroyView() {
		super.onDestroyView()

		_binding = null
	}

	override fun onStart() {
		super.onStart()

		presenter.register(this)
	}

	override fun onStop() {
		super.onStop()

		presenter.unregister(this)
	}

	/**
	 * Sends the given event to the presenter.
	 */
	protected fun send(event: Event) {
		presenter.send(event)
	}

	/**
	 * Renders each possible state sent from the associated presenter.
	 */
	protected abstract fun show(state: State)

	/**
	 * Entry-point for all states sent from the associated presenter.
	 */
	@Subscribe(threadMode = ThreadMode.MAIN)
	fun showInternal(state: State) {
		show(state)
	}
}