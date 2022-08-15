package com.wit.findmypharmacy.order

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.wit.findmypharmacy.R
import com.wit.findmypharmacy.core.Fragment
import com.wit.findmypharmacy.databinding.FragmentOrderBinding
import com.wit.findmypharmacy.databinding.MedicationListItemCheckableBinding
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
				if (menuItem.itemId == R.id.check_out) {
					send(CheckOutClicked)
				}

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
			PharmacyListState -> showPharmacyListState()
			is PharmacyNameState -> showPharmacyNameState(state.name)
			is ProgressIndicatorState -> showProgressIndicatorState(state.visible)
			is ToastState -> showToastState(state.messageStringResId)
		}
	}

	private fun showMedicationsState(medicationUiStates: List<MedicationUiState>) {
		medicationAdapter.submitList(medicationUiStates)
	}

	private fun showPharmacyListState() {
		val navDirections = OrderFragmentDirections.actionOrderFragmentToPharmacyListFragment()
		findNavController().navigate(navDirections)
	}

	private fun showPharmacyNameState(name: String) {
		binding.pharmacyName.text = name
	}

	private fun showProgressIndicatorState(visible: Boolean) {
		binding.progressIndicator.isVisible = visible
	}

	private fun showToastState(@StringRes messageStringResId: Int) {
		Toast.makeText(context, messageStringResId, Toast.LENGTH_LONG).show()
	}

	private class MedicationAdapter(private val onMedicationClicked: (String, Boolean) -> Unit) :
			ListAdapter<MedicationUiState, MedicationAdapter.ViewHolder>(ItemCallback()) {
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
			val context = parent.context
			val layoutInflater = LayoutInflater.from(context)
			val medicationListItemCheckableBinding =
				MedicationListItemCheckableBinding.inflate(layoutInflater, parent, false)
			val viewHolder = ViewHolder(medicationListItemCheckableBinding)

			return viewHolder
		}

		override fun onBindViewHolder(holder: ViewHolder, position: Int) {
			with(holder.medicationListItemCheckableBinding) {
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

		private class ViewHolder(val medicationListItemCheckableBinding: MedicationListItemCheckableBinding) :
				RecyclerView.ViewHolder(medicationListItemCheckableBinding.root)
	}
}