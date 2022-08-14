package com.wit.findmypharmacy.pharmacy.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wit.findmypharmacy.R
import com.wit.findmypharmacy.databinding.FragmentPharmacyListBinding

class PharmacyListFragment : Fragment() {
	private var _binding: FragmentPharmacyListBinding? = null

	private val binding get() = _binding!!

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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.buttonFirst.setOnClickListener {
			findNavController().navigate(R.id.action_PharmacyListFragment_to_SecondFragment)
		}
	}
}