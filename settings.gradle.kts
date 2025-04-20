rootProject.name = "BaseProject"
include(
    listOf(
        ":app",
        ":data:repositoryImpl",
        ":data:sources",
        ":data:sourcesImpl:remote",
        ":data:sourcesImpl:local",
        ":domain:model",
        ":domain:repository",
        ":domain:useCase",
        ":presentation:analytics",
        ":presentation:common",
        ":presentation:features:advert",
        ":presentation:features:home"
    )
)
