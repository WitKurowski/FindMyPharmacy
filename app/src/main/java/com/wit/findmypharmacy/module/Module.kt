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
			// TODO: Extract into resource
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