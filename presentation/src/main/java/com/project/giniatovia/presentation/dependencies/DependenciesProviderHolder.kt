package com.project.giniatovia.presentation.dependencies

import android.content.Context
import com.project.giniatovia.core.DependenciesProvider

interface DependenciesProviderHolder {
    fun getDependenciesProvider(): DependenciesProvider
}

fun Context.getDependenciesProvider(): DependenciesProvider {
    return (this.applicationContext as DependenciesProviderHolder).getDependenciesProvider()
}
