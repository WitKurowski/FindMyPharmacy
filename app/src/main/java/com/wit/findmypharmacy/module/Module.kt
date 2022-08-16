package com.wit.findmypharmacy.module

import com.wit.findmypharmacy.api.PharmacyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Contains all dependency injection objects that require some special setup or configuration.
 */
@Module
@InstallIn(SingletonComponent::class)
class Module {
	@Provides
	@Singleton
	fun providePharmacyApi(retrofit: Retrofit): PharmacyApi =
		retrofit.create(PharmacyApi::class.java)

	@Provides
	@Singleton
	fun provideRetrofit(): Retrofit {
		val retrofit = Retrofit.Builder().run {
			// Note: It would be cleaner to extract this into a resource to allow for different
			// endpoint base URLs across app flavors.
			baseUrl("https://api-qa-demo.nimbleandsimple.com/")
			addConverterFactory(GsonConverterFactory.create())
			build()
		}

		return retrofit
	}

	@Provides
	fun provideStateEventBus(): EventBus {
		val stateEventBus = EventBus.builder().build()

		return stateEventBus
	}
}