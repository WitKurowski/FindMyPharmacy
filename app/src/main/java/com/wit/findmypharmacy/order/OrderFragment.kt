package com.wit.findmypharmacy.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wit.findmypharmacy.core.Fragment
import com.wit.findmypharmacy.databinding.FragmentOrderBinding
import com.wit.findmypharmacy.databinding.MedicationListItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment<FragmentOrderBinding, OrderPresenter, Event, State>() {
	private val medicationAdapter = MedicationAdapter()

	override fun getBindingRootView(): View = binding.root

	override fun inflateBinding(
			layoutInflater: LayoutInflater, container: ViewGroup?
	): FragmentOrderBinding = FragmentOrderBinding.inflate(layoutInflater, container, false)

	override fun onStart() {
		super.onStart()

		send(StartedEvent)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with(binding.medications) {
			adapter = medicationAdapter
			layoutManager = LinearLayoutManager(context)
		}
	}

	override fun show(state: State) {
		when (state) {
			is MedicationsState -> showMedicationsState(state.medications)
		}
	}

	private fun showMedicationsState(medications: List<String>) {
		medicationAdapter.submitList(medications)
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

		private class ViewHolder(medicationListItemBinding: MedicationListItemBinding) :
				RecyclerView.ViewHolder(medicationListItemBinding.root)
	}
}