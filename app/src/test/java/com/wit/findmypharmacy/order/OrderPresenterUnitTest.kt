package com.wit.findmypharmacy.order

import com.wit.findmypharmacy.repository.MedicationRepository
import com.wit.findmypharmacy.repository.OrderRepository
import com.wit.findmypharmacy.repository.PharmacyRepository
import org.greenrobot.eventbus.EventBus
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OrderPresenterUnitTest {
	@Mock
	private lateinit var medicationRepository: MedicationRepository

	private lateinit var orderPresenter: OrderPresenter

	@Mock
	private lateinit var orderRepository: OrderRepository

	@Mock
	private lateinit var pharmacyRepository: PharmacyRepository

	@Mock
	private lateinit var stateEventBus: EventBus

	@Before
	fun before() {
		orderPresenter =
			OrderPresenter(medicationRepository, orderRepository, pharmacyRepository, stateEventBus)
	}

	@Test
	fun testStartedEvent1() {
		// TODO: Refactor presenter to extract out static location logic to make testing easier.
//		val pharmacyApiModels = listOf(
//				TestData.pharmacyApiModel1,
//				TestData.pharmacyApiModel2,
//				TestData.pharmacyApiModel3,
//				TestData.pharmacyApiModel4
//		)
//		BDDMockito //
//				.given(pharmacyRepository.get()) //
//				.willReturn(pharmacyApiModels)
//
//		BDDMockito //
//				.given(medicationRepository.get()) //
//				.willReturn(emptyList())
//
//		orderPresenter.onInternal(StartedEvent)
//
//		BDDMockito //
//				.verify(stateEventBus) //
//				.post(ProgressIndicatorState(visible = true))
//
//		val pharmacyNameState = PharmacyNameState(TestData.pharmacyApiModel3.name)
//		BDDMockito //
//				.verify(stateEventBus) //
//				.post(pharmacyNameState)
//
//		BDDMockito //
//				.verify(stateEventBus) //
//				.post(ProgressIndicatorState(visible = false))
//
//		Assert.assertEquals(TestData.pharmacyApiModel3, orderPresenter.nearestPharmacyApiModel)
	}
}