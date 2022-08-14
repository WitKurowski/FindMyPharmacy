package com.wit.findmypharmacy.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.greenrobot.eventbus.EventBus

@Module
@InstallIn(SingletonComponent::class)
class Module {
	@Provides
	fun provideStateEventBus(): EventBus {
		val stateEventBus = EventBus.getDefault()

		return stateEventBus
	}
}