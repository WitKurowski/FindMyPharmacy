package com.wit.findmypharmacy.pharmacy.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.*
import com.wit.findmypharmacy.core.Fragment
import com.wit.findmypharmacy.databinding.FragmentPharmacyInfoBinding
import com.wit.findmypharmacy.databinding.MedicationListItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PharmacyInfoFragment :
		Fragment<FragmentPharmacyInfoBinding, PharmacyInfoPresenter, Event, State>() {
	private val pharmacyInfoFragmentArgs by navArgs<PharmacyInfoFragmentArgs>()

	private val medicationAdapter = MedicationAdapter()

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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with(binding.medications) {
			adapter = medicationAdapter

			val dividerItemDecoration =
				DividerItemDecoration(context, android.widget.LinearLayout.VERTICAL)
			addItemDecoration(dividerItemDecoration)

			layoutManager = LinearLayoutManager(context)
		}
	}

	override fun show(state: State) {
		when (state) {
			is HoursLabelState -> showHoursLabelState(state.visible)
			is MedicationsState -> showMedicationsState(state.names)
			is OrderedMedicationsLabelState -> showOrderedMedicationsLabelState(state.visible)
			is PharmacyState -> showPharmacyState(
					state.address, state.hours, state.name, state.phoneNumber
			)
			is ProgressIndicatorState -> showProgressIndicatorState(state.visible)
		}
	}

	private fun showHoursLabelState(visible: Boolean) {
		binding.hoursLabel.isVisible = visible
	}

	private fun showMedicationsState(names: List<String>) {
		medicationAdapter.submitList(names)
	}

	private fun showOrderedMedicationsLabelState(visible: Boolean) {
		binding.orderedMedicationsLabel.isVisible = visible
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

	private fun showProgressIndicatorState(visible: Boolean) {
		binding.progressIndicator.isVisible = visible
	}

	private class MedicationAdapter :
			ListAdapter<String, MedicationAdapter.ViewHolder>(ItemCallback()) {
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
			val context = parent.context
			val layoutInflater = LayoutInflater.from(context)
			val medicationListItemBinding =
				MedicationListItemBinding.inflate(layoutInflater, parent, false)
			val viewHolder = ViewHolder(medicationListItemBinding)

			return viewHolder
		}

		override fun onBindViewHolder(holder: ViewHolder, position: Int) {
			with(holder.medicationListItemBinding) {
				val medicationName = getItem(position)
				name.text = medicationName
			}
		}

		private class ItemCallback : DiffUtil.ItemCallback<String>() {
			override fun areContentsTheSame(
					oldItem: String, newItem: String
			): Boolean {
				return oldItem == newItem
			}

			override fun areItemsTheSame(
					oldItem: String, newItem: String
			): Boolean {
				return oldItem == newItem
			}
		}

		private class ViewHolder(val medicationListItemBinding: MedicationListItemBinding) :
				RecyclerView.ViewHolder(medicationListItemBinding.root)
	}
}