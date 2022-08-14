package com.wit.findmypharmacy.pharmacy.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wit.findmypharmacy.databinding.FragmentPharmacyListBinding
import com.wit.findmypharmacy.databinding.PharmacyListItemBinding
import com.wit.findmypharmacy.model.Pharmacy
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@AndroidEntryPoint
class PharmacyListFragment : Fragment() {
	private var _binding: FragmentPharmacyListBinding? = null

	private val binding get() = _binding!!

	private val pharmacyAdapter = PharmacyAdapter()

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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with(binding.pharmacies) {
			adapter = pharmacyAdapter
			layoutManager = LinearLayoutManager(context)
		}
	}

	private fun send(event: Event) {
		pharmacyListPresenter.send(event)
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	fun show(state: State) {
		when (state) {
			is PharmaciesState -> showPharmaciesState(state.pharmacies)
		}
	}

	private fun showPharmaciesState(pharmacies: List<Pharmacy>) {
		pharmacyAdapter.submitList(pharmacies)
	}

	private class PharmacyAdapter :
			ListAdapter<Pharmacy, PharmacyAdapter.ViewHolder>(ItemCallback()) {
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
			val context = parent.context
			val layoutInflater = LayoutInflater.from(context)
			val pharmacyListItemBinding =
				PharmacyListItemBinding.inflate(layoutInflater, parent, false)
			val viewHolder = ViewHolder(pharmacyListItemBinding)

			return viewHolder
		}

		override fun onBindViewHolder(holder: ViewHolder, position: Int) {
			with(holder.pharmacyListItemBinding) {
				val pharmacy = getItem(position)
				name.text = pharmacy.name
			}
		}

		private class ItemCallback : DiffUtil.ItemCallback<Pharmacy>() {
			override fun areContentsTheSame(oldItem: Pharmacy, newItem: Pharmacy): Boolean {
				return oldItem == newItem
			}

			override fun areItemsTheSame(oldItem: Pharmacy, newItem: Pharmacy): Boolean {
				return oldItem.id == newItem.id
			}
		}

		private class ViewHolder(val pharmacyListItemBinding: PharmacyListItemBinding) :
				RecyclerView.ViewHolder(pharmacyListItemBinding.root)
	}
}