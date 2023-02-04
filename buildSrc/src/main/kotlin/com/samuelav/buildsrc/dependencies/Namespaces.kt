package com.samuelav.buildsrc.dependencies

object Namespaces {
    const val App = "com.samuelav.baseproject"

    object Data {
        const val Local = "com.samuelav.data.local"
        const val Remote = "com.samuelav.data.remote"
    }

    object Presentation {
        const val Analytics = "com.samuelav.presentation.analytics"
        const val Common = "com.samuelav.presentation.common"

        object Features {
            const val Advert = "com.samuelav.presentation.features.advert"
            const val Home = "com.samuelav.presentation.features.home"
        }
    }
}