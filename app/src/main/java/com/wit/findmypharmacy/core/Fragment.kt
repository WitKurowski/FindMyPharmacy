package com.wit.findmypharmacy.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

abstract class Fragment<B, P : Presenter<Event, State>, Event, State> : Fragment() {
	private var _binding: B? = null

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

	protected fun send(event: Event) {
		presenter.send(event)
	}

	protected abstract fun show(state: State)

	@Subscribe(threadMode = ThreadMode.MAIN)
	fun showInternal(state: State) {
		show(state)
	}
}