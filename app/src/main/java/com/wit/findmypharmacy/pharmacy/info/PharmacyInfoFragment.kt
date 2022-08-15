package com.wit.findmypharmacy.pharmacy.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.wit.findmypharmacy.core.Fragment
import com.wit.findmypharmacy.databinding.FragmentPharmacyInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PharmacyInfoFragment :
		Fragment<FragmentPharmacyInfoBinding, PharmacyInfoPresenter, Event, State>() {
	private val pharmacyInfoFragmentArgs by navArgs<PharmacyInfoFragmentArgs>()

	override fun getBindingRootView(): View = binding.root

	override fun inflateBinding(
			layoutInflater: LayoutInflater, container: ViewGroup?
	): FragmentPharmacyInfoBinding =
		FragmentPharmacyInfoBinding.inflate(layoutInflater, container, false)

	override fun onStart() {
		super.onStart()

		val startedEvent = StartedEvent(pharmacyInfoFragmentArgs.pharmacyId)

		send(startedEvent)
	}

	override fun show(state: State) {
		when (state) {
			is HoursLabelState -> showHoursLabelState(state.visible)
			is PharmacyState -> showPharmacyState(
					state.address, state.hours, state.name, state.phoneNumber
			)
		}
	}

	private fun showHoursLabelState(visible: Boolean) {
		binding.hoursLabel.isVisible = visible
	}

	private fun showPharmacyState(
			address: String, hours: String?, name: String, phoneNumber: String
	) {
		with(binding) {
			this.address.text = address
			this.hours.text = hours
			this.name.text = name
			this.phoneNumber.text = phoneNumber
		}
	}
}