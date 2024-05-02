import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.Locale
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
	alias(libs.plugins.com.android.application)
	alias(libs.plugins.org.jetbrains.kotlin.android)
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 0
val versionBuild = 0

fun generateVersionCode() = versionMajor * 1_000_000 + versionMinor * 10_000 + versionPatch * 100 + versionBuild

fun generateVersionName(): String {
	val isRelease = gradle.startParameter.taskRequests.toString().lowercase(Locale.getDefault()).contains("release")
	val isProd = gradle.startParameter.taskRequests.toString().lowercase(Locale.getDefault()).contains("prod")

	// display build number except for prod release
	return if (isRelease || isProd) {
		"$versionMajor.$versionMinor.$versionPatch"
	} else {
		"$versionMajor.$versionMinor.$versionPatch-$versionBuild"
	}
}

val localProperties = Properties().apply {
	val localProperties = rootProject.file("local.properties")
	if (localProperties.exists() && localProperties.isFile) {
		InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
			load(reader)
		}
	} else {
		throw FileNotFoundException("local.properties file not found at ${localProperties.absolutePath}")
	}
}

android {
	namespace = "digiteka.instream.andro"
	compileSdk = 34

	defaultConfig {
		applicationId = "digiteka.instream.andro"
		minSdk = 21
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
		buildConfigField("String", "DIGITEKA_INSTREAM_MDTK", "\"${localProperties.getProperty("DIGITEKA_INSTREAM_MDTK")}\"")
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		compose = true
		viewBinding = true
		buildConfig = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.2"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {

	implementation(libs.digiteka.instream)

	implementation(libs.core.ktx)
	implementation(libs.lifecycle.runtime.ktx)
	implementation(libs.activity.compose)
	implementation(platform(libs.compose.bom))
	implementation(libs.ui)
	implementation(libs.ui.graphics)
	implementation(libs.ui.tooling.preview)
	implementation(libs.material3)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.androidx.recyclerview)
	implementation(libs.material)
	androidTestImplementation(platform(libs.compose.bom))
}