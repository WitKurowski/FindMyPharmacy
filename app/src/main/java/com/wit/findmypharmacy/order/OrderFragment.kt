package com.wit.findmypharmacy.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wit.findmypharmacy.core.Fragment
import com.wit.findmypharmacy.databinding.FragmentOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment<FragmentOrderBinding, OrderPresenter, Event, State>() {
	override fun getBindingRootView(): View = binding.root

	override fun inflateBinding(
			layoutInflater: LayoutInflater, container: ViewGroup?
	): FragmentOrderBinding = FragmentOrderBinding.inflate(layoutInflater, container, false)

	override fun show(state: State) {
	}
}