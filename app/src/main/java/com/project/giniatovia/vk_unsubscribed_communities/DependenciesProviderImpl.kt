package com.project.giniatovia.vk_unsubscribed_communities

import com.project.giniatovia.core.DependenciesProvider
import com.project.giniatovia.data.repositories.RepositoryImpl
import com.project.giniatovia.domain.repositories.Repository
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

class DependenciesProviderImpl : DependenciesProvider {

    private val dependencyProviderMap: Map<KClass<*>, DependencyProvider<*>> = mapOf(
        Repository::class to PermanentDependencyProvider { RepositoryImpl() },
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(clazz: KClass<T>): T {
        return dependencyProviderMap[clazz]?.get() as? T
            ?: throw IllegalArgumentException("Wrong class $clazz for dependencies map")
    }
}
