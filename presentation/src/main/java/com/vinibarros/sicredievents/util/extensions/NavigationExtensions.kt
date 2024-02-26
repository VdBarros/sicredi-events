package com.vinibarros.sicredievents.util.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.safeNavigation(
    navDirections: NavDirections,
    navOptions: NavOptions? = null,
) {
    val action = currentDestination?.getAction(navDirections.actionId) ?: graph.getAction(
        navDirections.actionId
    )
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(navDirections, navOptions)
    }
}