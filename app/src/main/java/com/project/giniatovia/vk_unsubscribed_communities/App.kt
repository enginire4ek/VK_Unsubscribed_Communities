package com.project.giniatovia.vk_unsubscribed_communities

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.project.giniatovia.core.DependenciesProvider
import com.project.giniatovia.presentation.dependencies.DependenciesProviderHolder

class App : Application(), DependenciesProviderHolder {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }

    private val provider: DependenciesProvider by lazy {
        DependenciesProviderImpl()
    }

    override fun getDependenciesProvider(): DependenciesProvider {
        return provider
    }
}
