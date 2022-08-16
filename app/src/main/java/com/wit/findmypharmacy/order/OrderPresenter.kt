package com.wit.findmypharmacy.order

import android.location.Location
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import com.wit.findmypharmacy.R
import com.wit.findmypharmacy.core.Presenter
import com.wit.findmypharmacy.model.PharmacyApiModel
import com.wit.findmypharmacy.repository.MedicationRepository
import com.wit.findmypharmacy.repository.OrderRepository
import com.wit.findmypharmacy.repository.PharmacyRepository
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class OrderPresenter @Inject constructor(
		private val medicationRepository: MedicationRepository,
		private val orderRepository: OrderRepository,
		private val pharmacyRepository: PharmacyRepository,
		stateEventBus: EventBus
) : Presenter<Event, State>(stateEventBus) {
	companion object {
		private const val CURRENT_LOCATION_LATITUDE = 37.48771670017411
		private const val CURRENT_LOCATION_LONGITUDE = -122.22652739630438
	}

	private val medicationUiStates = mutableListOf<MedicationUiState>()

	@VisibleForTesting
	lateinit var nearestPharmacyApiModel: PharmacyApiModel

	override fun on(event: Event) {
		when (event) {
			CheckOutClicked -> onCheckOutClicked()
			is MedicationClicked -> onMedicationClicked(event.medication, event.checked)
			StartedEvent -> onStartedEvent()
		}
	}

	private fun onCheckOutClicked() {
		val ordersDatabaseModel = orderRepository.get()
		val previousOrderFromPharmacyExists = ordersDatabaseModel.any {
			it.pharmacyApiModelId == nearestPharmacyApiModel.id
		}

		if (previousOrderFromPharmacyExists) {
			showToastState(R.string.order_already_exists_with_pharmacy)
		} else {
			val hasMedicationsSelected = medicationUiStates.any {
				it.checked
			}

			if (hasMedicationsSelected) {
				val pharmacyApiModelId = nearestPharmacyApiModel.id
				val checkedMedicationUiStates = medicationUiStates.filter {
					it.checked
				}
				val checkedMedications = checkedMedicationUiStates.map {
					it.name
				}
				orderRepository.order(pharmacyApiModelId, checkedMedications)

				showToastState(R.string.successfully_submitted_order)

				show(PharmacyListState)
			} else {
				showToastState(R.string.no_medications_selected)
			}
		}
	}

	private fun onMedicationClicked(name: String, checked: Boolean) {
		val oldMedicationUiState = medicationUiStates.first {
			it.name == name
		}
		val newMedicationUiState = oldMedicationUiState.copy(checked = checked)
		val index = medicationUiStates.indexOf(oldMedicationUiState)

		medicationUiStates.remove(oldMedicationUiState)
		medicationUiStates.add(index, newMedicationUiState)

		showMedicationsState()
	}

	private fun onStartedEvent() {
		showProgressIndicatorState(visible = true)

		val pharmacies = pharmacyRepository.get()
		val results = FloatArray(1)
		nearestPharmacyApiModel = pharmacies.minByOrNull {
			val addressApiModel = it.addressApiModel
			val latitude = addressApiModel.latitude
			val longitude = addressApiModel.longitude

			// TODO: Extract into injected class to allow for unit tests.
			Location.distanceBetween(
					CURRENT_LOCATION_LATITUDE,
					CURRENT_LOCATION_LONGITUDE,
					latitude,
					longitude,
					results
			)

			results[0]
		}!!
		val pharmacyNameState = PharmacyNameState(nearestPharmacyApiModel.name)
		show(pharmacyNameState)

		val medications = medicationRepository.get()

		medications.forEach {
			val medicationUiState = MedicationUiState(checked = false, name = it)
			medicationUiStates.add(medicationUiState)
		}

		showMedicationsState()

		showProgressIndicatorState(visible = false)
	}

	private fun showMedicationsState() {
		val medicationsState = MedicationsState(medicationUiStates)
		show(medicationsState)
	}

	private fun showProgressIndicatorState(visible: Boolean) {
		val progressIndicatorState = ProgressIndicatorState(visible)
		show(progressIndicatorState)
	}

	private fun showToastState(@StringRes messageStringResId: Int) {
		val toastState = ToastState(messageStringResId)
		show(toastState)
	}
}