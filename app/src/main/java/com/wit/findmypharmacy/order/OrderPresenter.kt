package com.wit.findmypharmacy.order

import com.wit.findmypharmacy.core.Presenter
import com.wit.findmypharmacy.repository.MedicationRepository
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class OrderPresenter @Inject constructor(
		private val medicationRepository: MedicationRepository, stateEventBus: EventBus
) : Presenter<Event, State>(stateEventBus) {
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