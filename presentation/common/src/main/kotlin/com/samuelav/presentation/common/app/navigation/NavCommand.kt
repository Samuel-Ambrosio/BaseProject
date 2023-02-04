package com.samuelav.presentation.common.app.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavCommand(
    val rootRoute: String,
    private val feature: Feature,
    private val navArgs: List<NavArg> = emptyList(),
    private val optionalNavArgs: List<NavArg> = emptyList(),
){
    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        val optionalArgValues = optionalNavArgs.map { "${it.key}={${it.key}}" }

        val path =
            listOf(rootRoute)
                .plus(feature.route)
                .plus(argValues)
                .filterNot { it.isBlank() }
                .joinToString("/")

        val optionalNavValues = optionalArgValues.filterNot { it.isBlank() }.joinToString("&")
        listOf(path, optionalNavValues).filterNot { it.isBlank() }.joinToString("?")
    }

    val args = navArgs.map { navArgument(it.key) { type = it.navType } }

    val optionalArgs =
        optionalNavArgs.map {
            navArgument(it.key) {
                type = it.navType
                when (it.navType) {
                    NavType.IntType -> {
                        nullable = false
                        defaultValue = 0
                    }
                    else -> {
                        nullable = true
                    }
                }
            }
        }

    fun createRoute(
        args: List<Any> = emptyList(),
        optionalArgs: Map<NavArg, Any> = emptyMap(),
    ) =
        listOf(rootRoute)
            .asSequence()
            .plus(feature.route)
            .plus(args)
            .map { it.toString() }
            .filterNot { it.isBlank() }
            .joinToString("/")
            .let { path ->
                listOf(path)
                    .plus(
                        optionalArgs
                            .map { "${it.key.key}=${it.value}" }
                            .filterNot { it.isBlank() }
                            .joinToString("&"))
                    .filterNot { it.isBlank() }
                    .joinToString("?")
            }

    class HomeSection(
        feature: Feature,
        navArgs: List<NavArg> = emptyList(),
        optionalNavArgs: List<NavArg> = emptyList(),
    ) : NavCommand(
        rootRoute = "home",
        feature = feature,
        navArgs = navArgs,
        optionalNavArgs = optionalNavArgs,
    ) {
        companion object {
            val Main = HomeSection(feature = Feature.Main)
        }
    }

    class SearchSection(
        feature: Feature,
        navArgs: List<NavArg> = emptyList(),
        optionalNavArgs: List<NavArg> = emptyList(),
    ) : NavCommand(
        rootRoute = "search",
        feature = feature,
        navArgs = navArgs,
        optionalNavArgs = optionalNavArgs,
    ) {
        companion object {
            val Main = SearchSection(feature = Feature.Main)
        }
    }

    class MoreSection(
        feature: Feature,
        navArgs: List<NavArg> = emptyList(),
        optionalNavArgs: List<NavArg> = emptyList(),
    ) : NavCommand(
        rootRoute = "more",
        feature = feature,
        navArgs = navArgs,
        optionalNavArgs = optionalNavArgs,
    ) {
        companion object {
            val Main = MoreSection(feature = Feature.Main)
        }
    }
}

data class NavArg(val key: String, val navType: NavType<*>) {
    companion object {}
}