package com.wit.findmypharmacy.pharmacy.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wit.findmypharmacy.core.Fragment
import com.wit.findmypharmacy.databinding.FragmentPharmacyListBinding
import com.wit.findmypharmacy.databinding.PharmacyListItemBinding
import com.wit.findmypharmacy.model.PharmacyApiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PharmacyListFragment :
		Fragment<FragmentPharmacyListBinding, PharmacyListPresenter, Event, State>() {
	private val pharmacyAdapter = PharmacyAdapter {
		val actionPharmacyListFragmentToPharmacyDetailsFragment =
			PharmacyListFragmentDirections.actionPharmacyListFragmentToPharmacyDetailsFragment(it)
		findNavController().navigate(
				actionPharmacyListFragmentToPharmacyDetailsFragment
		)
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

		with(binding.pharmacies) {
			adapter = pharmacyAdapter
			layoutManager = LinearLayoutManager(context)
		}
	}

	override fun show(state: State) {
		when (state) {
			is PharmaciesState -> showPharmaciesState(state.pharmacyApiModels)
		}
	}

	private fun showPharmaciesState(pharmacyApiModels: List<PharmacyApiModel>) {
		pharmacyAdapter.submitList(pharmacyApiModels)
	}

	private class PharmacyAdapter(private val onPharmacyClicked: (String) -> Unit) :
			ListAdapter<PharmacyApiModel, PharmacyAdapter.ViewHolder>(ItemCallback()) {
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

				root.setOnClickListener {
					val id = pharmacyApiModel.id
					onPharmacyClicked(id)
				}
			}
		}

		private class ItemCallback : DiffUtil.ItemCallback<PharmacyApiModel>() {
			override fun areContentsTheSame(
					oldItem: PharmacyApiModel, newItem: PharmacyApiModel
			): Boolean {
				return oldItem == newItem
			}

			override fun areItemsTheSame(
					oldItem: PharmacyApiModel, newItem: PharmacyApiModel
			): Boolean {
				return oldItem.id == newItem.id
			}
		}

		private class ViewHolder(val pharmacyListItemBinding: PharmacyListItemBinding) :
				RecyclerView.ViewHolder(pharmacyListItemBinding.root)
	}
}