package com.wit.findmypharmacy.pharmacy.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
			is PharmacyState -> showPharmacyState(state.address, state.name)
		}
	}

	private fun showPharmacyState(address: String, name: String) {
		with(binding) {
			this.address.text = address
			this.name.text = name
		}
	}
}