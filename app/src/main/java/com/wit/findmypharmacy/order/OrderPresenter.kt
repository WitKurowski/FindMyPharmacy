package com.wit.findmypharmacy.order

import android.location.Location
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

	private lateinit var nearestPharmacyApiModel: PharmacyApiModel

	override fun on(event: Event) {
		when (event) {
			CheckOutClicked -> onCheckOutClicked()
			is MedicationClicked -> onMedicationClicked(event.medication, event.checked)
			StartedEvent -> onStartedEvent()
		}
	}

	private fun onCheckOutClicked() {
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

			show(PharmacyListState)
		} else {
			val toastState = ToastState(R.string.no_medications_selected)
			show(toastState)
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
		val pharmacies = pharmacyRepository.get()
		val results = FloatArray(1)
		nearestPharmacyApiModel = pharmacies.minByOrNull {
			val addressApiModel = it.addressApiModel!!
			val latitude = addressApiModel.latitude
			val longitude = addressApiModel.longitude
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
	}

	private fun showMedicationsState() {
		val medicationsState = MedicationsState(medicationUiStates)
		show(medicationsState)
	}
}