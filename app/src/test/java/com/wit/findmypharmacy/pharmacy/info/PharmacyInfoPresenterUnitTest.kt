package com.wit.findmypharmacy.pharmacy.info

import com.wit.findmypharmacy.TestData
import com.wit.findmypharmacy.model.Order
import com.wit.findmypharmacy.repository.OrderRepository
import com.wit.findmypharmacy.repository.PharmacyRepository
import org.greenrobot.eventbus.EventBus
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PharmacyInfoPresenterUnitTest {
	@Mock
	private lateinit var orderRepository: OrderRepository

	private lateinit var pharmacyInfoPresenter: PharmacyInfoPresenter

	@Mock
	private lateinit var pharmacyRepository: PharmacyRepository

	@Mock
	private lateinit var stateEventBus: EventBus

	@Before
	fun before() {
		pharmacyInfoPresenter =
			PharmacyInfoPresenter(orderRepository, pharmacyRepository, stateEventBus)
	}

	/**
	 * Scenario:
	 * - no orders returned
	 * - pharmacy hours are returned
	 * - no pharmacy phone number returned
	 */
	@Test
	fun testStartedEvent1() {
		BDDMockito //
				.given(pharmacyRepository.get(TestData.pharmacyId2)) //
				.willReturn(TestData.pharmacy2)

		BDDMockito //
				.given(orderRepository.get()) //
				.willReturn(emptyList())

		val startedEvent = StartedEvent(TestData.pharmacyId2)
		pharmacyInfoPresenter.onInternal(startedEvent)

		val progressIndicatorState1 = ProgressIndicatorState(visible = true)
		BDDMockito //
				.verify(stateEventBus) //
				.post(progressIndicatorState1)

		val hoursLabelState = HoursLabelState(visible = false)
		BDDMockito //
				.verify(stateEventBus) //
				.post(hoursLabelState)

		val orderedMedicationsLabelState = OrderedMedicationsLabelState(visible = false)
		BDDMockito //
				.verify(stateEventBus) //
				.post(orderedMedicationsLabelState)

		val address = "1501 8th Ave # A\nSan Francisco, CA\n94122"
		val hours = "9:00a-7:30p Mon-Fri\n10:00a-4:00p Sat"
		val pharmacyState = PharmacyState(
				address, hours, TestData.pharmacyName2, TestData.pharmacyPhoneNumber2
		)
		BDDMockito //
				.verify(stateEventBus) //
				.post(pharmacyState)

		val updatedHoursLabelState = HoursLabelState(visible = true)
		BDDMockito //
				.verify(stateEventBus) //
				.post(updatedHoursLabelState)

		val progressIndicatorState2 = ProgressIndicatorState(visible = false)
		BDDMockito //
				.verify(stateEventBus) //
				.post(progressIndicatorState2)
	}

	/**
	 * Scenario:
	 * - orders returned
	 * - no order matches pharmacy shown
	 * - no pharmacy hours are returned
	 * - pharmacy phone number is returned
	 */
	@Test
	fun testStartedEvent2() {
		BDDMockito //
				.given(pharmacyRepository.get(TestData.pharmacyId4)) //
				.willReturn(TestData.pharmacy4)

		val medications1 = listOf("Abelcet", "Acarbose")
		val order1 = Order(TestData.pharmacyId2, medications1)
		val medications2 = listOf("Abelcet", "Acarbose")
		val order2 = Order(TestData.pharmacyId3, medications2)
		BDDMockito //
				.given(orderRepository.get()) //
				.willReturn(listOf(order1, order2))

		val startedEvent = StartedEvent(TestData.pharmacyId4)
		pharmacyInfoPresenter.onInternal(startedEvent)

		val progressIndicatorState1 = ProgressIndicatorState(visible = true)
		BDDMockito //
				.verify(stateEventBus) //
				.post(progressIndicatorState1)

		val hoursLabelState = HoursLabelState(visible = false)
		BDDMockito //
				.verify(stateEventBus, Mockito.times(2)) //
				.post(hoursLabelState)

		val orderedMedicationsLabelState = OrderedMedicationsLabelState(visible = false)
		BDDMockito //
				.verify(stateEventBus) //
				.post(orderedMedicationsLabelState)

		val address = "3 E 52ND ST\nNEW YORK, NY\n10022"
		val hours = null
		val pharmacyState = PharmacyState(
				address, hours, TestData.pharmacyName4, TestData.pharmacyPhoneNumber4
		)
		BDDMockito //
				.verify(stateEventBus) //
				.post(pharmacyState)

		val progressIndicatorState2 = ProgressIndicatorState(visible = false)
		BDDMockito //
				.verify(stateEventBus) //
				.post(progressIndicatorState2)
	}

	/**
	 * Scenario:
	 * - orders returned
	 * - order matches pharmacy shown
	 * - pharmacy hours are returned
	 * - pharmacy phone number is returned
	 */
	@Test
	fun testStartedEvent3() {
		BDDMockito //
				.given(pharmacyRepository.get(TestData.pharmacyId3)) //
				.willReturn(TestData.pharmacy3)

		val medications1 = listOf("Abelcet", "Acarbose")
		val order1 = Order(TestData.pharmacyId2, medications1)
		val medications2 = listOf("Abilify")
		val order2 = Order(TestData.pharmacyId3, medications2)
		BDDMockito //
				.given(orderRepository.get()) //
				.willReturn(listOf(order1, order2))

		val startedEvent = StartedEvent(TestData.pharmacyId3)
		pharmacyInfoPresenter.onInternal(startedEvent)

		val progressIndicatorState1 = ProgressIndicatorState(visible = true)
		BDDMockito //
				.verify(stateEventBus) //
				.post(progressIndicatorState1)

		val hoursLabelState = HoursLabelState(visible = false)
		BDDMockito //
				.verify(stateEventBus) //
				.post(hoursLabelState)

		val orderedMedicationsLabelState1 = OrderedMedicationsLabelState(visible = false)
		BDDMockito //
				.verify(stateEventBus) //
				.post(orderedMedicationsLabelState1)

		val address = "2317 BROADWAY ST\nREDWOOD CITY, CA\n94063"
		val hours = "9:00am-7:00pm Mon-Fri\n9:00a - 5:00p Sat\n10:00a- 5:00p Sun"
		val pharmacyState = PharmacyState(
				address, hours, TestData.pharmacyName3, TestData.pharmacyPhoneNumber3
		)
		BDDMockito //
				.verify(stateEventBus) //
				.post(pharmacyState)

		val updatedHoursLabelState = HoursLabelState(visible = false)
		BDDMockito //
				.verify(stateEventBus) //
				.post(updatedHoursLabelState)

		val medicationsState = MedicationsState(medications2)
		BDDMockito //
				.verify(stateEventBus) //
				.post(medicationsState)

		val orderedMedicationsLabelState2 = OrderedMedicationsLabelState(visible = true)
		BDDMockito //
				.verify(stateEventBus) //
				.post(orderedMedicationsLabelState2)

		val progressIndicatorState2 = ProgressIndicatorState(visible = false)
		BDDMockito //
				.verify(stateEventBus) //
				.post(progressIndicatorState2)
	}
}