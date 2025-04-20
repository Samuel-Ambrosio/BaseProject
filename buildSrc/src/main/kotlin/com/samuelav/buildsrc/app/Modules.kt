package com.samuelav.buildsrc.app

object Modules {
    const val app = ":app"

    object Data {
        const val RepositoryImpl = ":data:repositoryImpl"
        const val Sources = ":data:sources"

        object SourcesImpl {
            const val Local = ":data:sourcesImpl:local"
            const val Remote = ":data:sourcesImpl:remote"
        }
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