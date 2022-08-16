package com.wit.findmypharmacy.pharmacy.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.wit.findmypharmacy.core.Fragment
import com.wit.findmypharmacy.databinding.FragmentPharmacyListBinding
import com.wit.findmypharmacy.databinding.PharmacyListItemBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * The screen that shows all available pharmacies.
 */
@AndroidEntryPoint
class PharmacyListFragment :
		Fragment<FragmentPharmacyListBinding, PharmacyListPresenter, Event, State>() {
	private val pharmacyAdapter = PharmacyAdapter {
		val navDirections =
			PharmacyListFragmentDirections.actionPharmacyListFragmentToPharmacyDetailsFragment(it)
		findNavController().navigate(navDirections)
	}

	override fun getBindingRootView(): View = binding.root

	override fun inflateBinding(
			layoutInflater: LayoutInflater, container: ViewGroup?
	): FragmentPharmacyListBinding =
		FragmentPharmacyListBinding.inflate(layoutInflater, container, false)

	override fun onStart() {
		super.onStart()

		send(StartedEvent)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with(binding) {
			with(pharmacies) {
				adapter = pharmacyAdapter

				val dividerItemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
				addItemDecoration(dividerItemDecoration)

				layoutManager = LinearLayoutManager(context)
			}

			order.setOnClickListener {
				val navDirections =
					PharmacyListFragmentDirections.actionPharmacyListFragmentToOrderFragment()
				findNavController().navigate(navDirections)
			}
		}
	}

	override fun show(state: State) {
		when (state) {
			is PharmaciesState -> showPharmaciesState(state.pharmacyUiStates)
			is ProgressIndicatorState -> showProgressIndicatorState(state.visible)
		}
	}

	private fun showPharmaciesState(pharmacyApiModels: List<PharmacyUiState>) {
		pharmacyAdapter.submitList(pharmacyApiModels)
	}

	private fun showProgressIndicatorState(visible: Boolean) {
		binding.progressIndicator.isVisible = visible
	}

	private class PharmacyAdapter(private val onPharmacyClicked: (String) -> Unit) :
			ListAdapter<PharmacyUiState, PharmacyAdapter.ViewHolder>(ItemCallback()) {
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
			val context = parent.context
			val layoutInflater = LayoutInflater.from(context)
			val pharmacyListItemBinding =
				PharmacyListItemBinding.inflate(layoutInflater, parent, false)
			val viewHolder = ViewHolder(pharmacyListItemBinding)

			return viewHolder
		}

		override fun onBindViewHolder(holder: ViewHolder, position: Int) {
			with(holder.pharmacyListItemBinding) {
				val pharmacyApiModel = getItem(position)
				name.text = pharmacyApiModel.name

				hasOrder.isVisible = pharmacyApiModel.checked

				root.setOnClickListener {
					val id = pharmacyApiModel.id
					onPharmacyClicked(id)
				}
			}
		}

		private class ItemCallback : DiffUtil.ItemCallback<PharmacyUiState>() {
			override fun areContentsTheSame(
					oldItem: PharmacyUiState, newItem: PharmacyUiState
			): Boolean {
				return oldItem == newItem
			}

			override fun areItemsTheSame(
					oldItem: PharmacyUiState, newItem: PharmacyUiState
			): Boolean {
				return oldItem.id == newItem.id
			}
		}

		private class ViewHolder(val pharmacyListItemBinding: PharmacyListItemBinding) :
				RecyclerView.ViewHolder(pharmacyListItemBinding.root)
	}
}