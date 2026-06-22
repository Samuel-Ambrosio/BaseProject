import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

apply(plugin = libs.plugins.androidLibrary.get().pluginId)
apply(plugin = libs.plugins.kotlinSerialization.get().pluginId)
apply<KotlinAndroidComposeBasePlugin>()
