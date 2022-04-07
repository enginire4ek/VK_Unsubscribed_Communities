package com.project.giniatovia.core

import kotlin.reflect.KClass

interface DependenciesProvider {
    fun <T : Any> get(clazz: KClass<T>): T
}
