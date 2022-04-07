package com.project.giniatovia.vk_unsubscribed_communities

import com.project.giniatovia.core.DependenciesProvider
import com.project.giniatovia.data.repositories.GroupsRepositoryImpl
import com.project.giniatovia.domain.repositories.GroupsRepository
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

class DependenciesProviderImpl : DependenciesProvider {

    private val dependencyProviderMap: Map<KClass<*>, DependencyProvider<*>> = mapOf(
        GroupsRepository::class to PermanentDependencyProvider { GroupsRepositoryImpl() },
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(clazz: KClass<T>): T {
        return dependencyProviderMap[clazz]?.get() as? T
            ?: throw IllegalArgumentException("Wrong class $clazz for dependencies map")
    }
}
