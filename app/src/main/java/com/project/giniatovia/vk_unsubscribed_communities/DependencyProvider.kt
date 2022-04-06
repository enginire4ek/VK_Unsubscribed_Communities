package com.project.giniatovia.vk_unsubscribed_communities

interface DependencyProvider<Dependency> {
    fun get(): Dependency
}

class PermanentDependencyProvider<Dependency>(private val initializer: () -> Dependency) :
    DependencyProvider<Dependency> {

    @Volatile
    private var instance: Dependency? = null

    override fun get(): Dependency {
        return instance ?: synchronized(this) {
            instance ?: initializer.invoke()
                .also { instance = it }
        }
    }
}
