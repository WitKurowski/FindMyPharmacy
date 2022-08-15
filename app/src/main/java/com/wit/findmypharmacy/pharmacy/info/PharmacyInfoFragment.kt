package com.wit.findmypharmacy.pharmacy.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wit.findmypharmacy.databinding.FragmentPharmacyInfoBinding

class PharmacyInfoFragment : Fragment() {
	private var _binding: FragmentPharmacyInfoBinding? = null

	private val binding get() = _binding!!

	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		_binding = FragmentPharmacyInfoBinding.inflate(inflater, container, false)

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()

		_binding = null
	}
}