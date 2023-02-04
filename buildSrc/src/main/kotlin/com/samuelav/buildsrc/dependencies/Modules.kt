package com.samuelav.buildsrc.dependencies

object Modules {
    const val app = ":app"

    object Data {
        const val Source = ":data:source"
        const val Remote = ":data:remote"
        const val Local = ":data:local"
        const val RepositoryImpl = ":data:repositoryImpl"
    }

    object Domain {
        const val Model = ":domain:model"
        const val Repository = ":domain:repository"
        const val UseCase = ":domain:useCase"
    }

    object Presentation {
        const val Analytics = ":presentation:analytics"
        const val Common = ":presentation:common"

        object Features {
            const val Advert = ":presentation:features:advert"
            const val Home = ":presentation:features:home"
        }
    }
}