package com.wit.findmypharmacy.pharmacy.list

import com.wit.findmypharmacy.R
import com.wit.findmypharmacy.TestData
import com.wit.findmypharmacy.model.Order
import com.wit.findmypharmacy.repository.OrderRepository
import com.wit.findmypharmacy.repository.PharmacyRepository
import com.wit.findmypharmacy.util.ExceptionUtils
import org.greenrobot.eventbus.EventBus
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PharmacyListPresenterUnitTest {
	@Mock
	private lateinit var orderRepository: OrderRepository

	private lateinit var pharmacyListPresenter: PharmacyListPresenter

	@Mock
	private lateinit var pharmacyRepository: PharmacyRepository

	@Mock
	private lateinit var stateEventBus: EventBus

	@Before
	fun before() {
		pharmacyListPresenter =
			PharmacyListPresenter(orderRepository, pharmacyRepository, stateEventBus)
	}

	/**
	 * Scenario:
	 * - no orders returned
	 */
	@Test
	fun testStartedEvent1() {
		val pharmacyApiModels = listOf(
				TestData.pharmacy1, TestData.pharmacy2, TestData.pharmacy3, TestData.pharmacy4
		)
		BDDMockito //
				.given(pharmacyRepository.get()) //
				.willReturn(pharmacyApiModels)

		BDDMockito //
				.given(orderRepository.get()) //
				.willReturn(emptyList())

		pharmacyListPresenter.onInternal(StartedEvent)

		BDDMockito //
				.verify(stateEventBus) //
				.post(ProgressIndicatorState(visible = true))

		val pharmacyUiState1 = PharmacyUiState(
				checked = false, TestData.pharmacyId1, TestData.pharmacyName1
		)
		val pharmacyUiState2 = PharmacyUiState(
				checked = false, TestData.pharmacyId2, TestData.pharmacyName2
		)
		val pharmacyUiState3 = PharmacyUiState(
				checked = false, TestData.pharmacyId3, TestData.pharmacyName3
		)
		val pharmacyUiState4 = PharmacyUiState(
				checked = false, TestData.pharmacyId4, TestData.pharmacyName4
		)
		val pharmacyUiStates =
			listOf(pharmacyUiState1, pharmacyUiState2, pharmacyUiState3, pharmacyUiState4)
		val pharmaciesState = PharmaciesState(pharmacyUiStates)
		BDDMockito //
				.verify(stateEventBus) //
				.post(pharmaciesState)

		BDDMockito //
				.verify(stateEventBus) //
				.post(ProgressIndicatorState(visible = false))
	}

	/**
	 * Scenario:
	 * - several orders returned
	 */
	@Test
	fun testStartedEvent2() {
		val pharmacyApiModels = listOf(
				TestData.pharmacy1, TestData.pharmacy2, TestData.pharmacy3, TestData.pharmacy4
		)
		BDDMockito //
				.given(pharmacyRepository.get()) //
				.willReturn(pharmacyApiModels)

		val order1 = Order(TestData.pharmacyId2, listOf("Abelcet", "Abilify"))
		val order2 = Order(TestData.pharmacyId3, listOf("Acarbose"))
		val orders = listOf(order1, order2)
		BDDMockito //
				.given(orderRepository.get()) //
				.willReturn(orders)

		pharmacyListPresenter.onInternal(StartedEvent)

		val progressIndicatorState1 = ProgressIndicatorState(visible = true)
		BDDMockito //
				.verify(stateEventBus) //
				.post(progressIndicatorState1)

		val pharmacyUiState1 = PharmacyUiState(
				checked = false, TestData.pharmacyId1, TestData.pharmacyName1
		)
		val pharmacyUiState2 = PharmacyUiState(
				checked = true, TestData.pharmacyId2, TestData.pharmacyName2
		)
		val pharmacyUiState3 = PharmacyUiState(
				checked = true, TestData.pharmacyId3, TestData.pharmacyName3
		)
		val pharmacyUiState4 = PharmacyUiState(
				checked = false, TestData.pharmacyId4, TestData.pharmacyName4
		)
		val pharmacyUiStates =
			listOf(pharmacyUiState1, pharmacyUiState2, pharmacyUiState3, pharmacyUiState4)
		val pharmaciesState = PharmaciesState(pharmacyUiStates)
		BDDMockito //
				.verify(stateEventBus) //
				.post(pharmaciesState)

		val progressIndicatorState2 = ProgressIndicatorState(visible = false)
		BDDMockito //
				.verify(stateEventBus) //
				.post(progressIndicatorState2)
	}

	/**
	 * Scenario:
	 * - retrieving pharmacies fails with HttpException
	 */
	@Test
	fun testStartedEvent3() {
		BDDMockito //
				.given(pharmacyRepository.get()) //
				.willThrow(ExceptionUtils.HTTP_EXCEPTION)

		pharmacyListPresenter.onInternal(StartedEvent)

		val progressIndicatorState1 = ProgressIndicatorState(visible = true)
		BDDMockito //
				.verify(stateEventBus) //
				.post(progressIndicatorState1)

		val toastState = ToastState(R.string.failed_to_retrieve_pharmacies_and_orders)
		BDDMockito //
				.verify(stateEventBus) //
				.post(toastState)

		val progressIndicatorState2 = ProgressIndicatorState(visible = false)
		BDDMockito //
				.verify(stateEventBus) //
				.post(progressIndicatorState2)
	}
}