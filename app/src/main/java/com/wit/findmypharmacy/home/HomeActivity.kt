package com.wit.findmypharmacy.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.wit.findmypharmacy.R
import com.wit.findmypharmacy.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * The singular activity for this application.
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityHomeBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityHomeBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.toolbar)

		val navController = findNavController(R.id.nav_host_fragment_content_pharmacy_list)
		appBarConfiguration = AppBarConfiguration(navController.graph)
		setupActionBarWithNavController(navController, appBarConfiguration)
	}

	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nav_host_fragment_content_pharmacy_list)
		val supportNavigateUp =
			navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

		return supportNavigateUp
	}
}