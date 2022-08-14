package com.wit.findmypharmacy.pharmacy.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wit.findmypharmacy.databinding.FragmentPharmacyListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PharmacyListFragment : Fragment() {
	private var _binding: FragmentPharmacyListBinding? = null

	private val binding get() = _binding!!

	@Inject
	lateinit var pharmacyListPresenter: PharmacyListPresenter

	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		_binding = FragmentPharmacyListBinding.inflate(inflater, container, false)

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()

		_binding = null
	}

	override fun onStart() {
		super.onStart()

		pharmacyListPresenter.register(this)
		send(StartedEvent)
	}

	override fun onStop() {
		super.onStop()

		pharmacyListPresenter.unregister(this)
	}

	private fun send(event: Event) {
		pharmacyListPresenter.send(event)
	}
}