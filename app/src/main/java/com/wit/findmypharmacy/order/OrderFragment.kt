package com.wit.findmypharmacy.order

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.*
import com.wit.findmypharmacy.R
import com.wit.findmypharmacy.core.Fragment
import com.wit.findmypharmacy.databinding.FragmentOrderBinding
import com.wit.findmypharmacy.databinding.MedicationListItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment<FragmentOrderBinding, OrderPresenter, Event, State>() {
	private val medicationAdapter = MedicationAdapter { medication, checked ->
		val medicationClicked = MedicationClicked(medication, checked)
		send(medicationClicked)
	}

	private fun addMenuProvider() {
		requireActivity().addMenuProvider(object : MenuProvider {
			override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
				menuInflater.inflate(R.menu.fragment_order, menu)
			}

			override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
				return true
			}
		}, viewLifecycleOwner, Lifecycle.State.RESUMED)
	}

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

		addMenuProvider()

		with(binding.medications) {
			adapter = medicationAdapter

			val dividerItemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
			addItemDecoration(dividerItemDecoration)

			layoutManager = LinearLayoutManager(context)
		}
	}

	override fun show(state: State) {
		when (state) {
			is MedicationsState -> showMedicationsState(state.medicationUiStates)
			is PharmacyNameState -> showPharmacyNameState(state.name)
		}
	}

	private fun showMedicationsState(medicationUiStates: List<MedicationUiState>) {
		medicationAdapter.submitList(medicationUiStates)
	}

	private fun showPharmacyNameState(name: String) {
		binding.pharmacyName.text = name
	}

	private class MedicationAdapter(private val onMedicationClicked: (String, Boolean) -> Unit) :
			ListAdapter<MedicationUiState, MedicationAdapter.ViewHolder>(ItemCallback()) {
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
				with(name) {
					val medicationUiState = getItem(position)
					val name = medicationUiState.name
					text = name

					setOnCheckedChangeListener(null)

					val checked = medicationUiState.checked
					isChecked = checked

					setOnCheckedChangeListener { _, b ->
						onMedicationClicked(name, b)
					}
				}
			}
		}

		private class ItemCallback : DiffUtil.ItemCallback<MedicationUiState>() {
			override fun areContentsTheSame(
					oldItem: MedicationUiState, newItem: MedicationUiState
			): Boolean {
				return oldItem == newItem
			}

			override fun areItemsTheSame(
					oldItem: MedicationUiState, newItem: MedicationUiState
			): Boolean {
				return oldItem.name == newItem.name
			}
		}

		private class ViewHolder(val medicationListItemBinding: MedicationListItemBinding) :
				RecyclerView.ViewHolder(medicationListItemBinding.root)
	}
}