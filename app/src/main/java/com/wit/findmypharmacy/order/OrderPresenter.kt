package com.wit.findmypharmacy.order

import android.location.Location
import com.wit.findmypharmacy.core.Presenter
import com.wit.findmypharmacy.repository.MedicationRepository
import com.wit.findmypharmacy.repository.PharmacyRepository
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class OrderPresenter @Inject constructor(
		private val medicationRepository: MedicationRepository,
		private val pharmacyRepository: PharmacyRepository,
		stateEventBus: EventBus
) : Presenter<Event, State>(stateEventBus) {
	companion object {
		private const val CURRENT_LOCATION_LATITUDE = 37.48771670017411
		private const val CURRENT_LOCATION_LONGITUDE = -122.22652739630438
	}

	private val medicationUiStates = mutableListOf<MedicationUiState>()

	override fun on(event: Event) {
		when (event) {
			is MedicationClicked -> onMedicationClicked(event.medication, event.checked)
			StartedEvent -> onStartedEvent()
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
		val nearestPharmacy = pharmacies.minByOrNull {
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
		val pharmacyNameState = PharmacyNameState(nearestPharmacy.name)
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