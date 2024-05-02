# Digiteka InStream SDK

[![en](https://img.shields.io/badge/lang-en-red.svg)](ReadMe.md)
[![fr](https://img.shields.io/badge/lang-fr-blue.svg)](ReadMe.fr.md)

Digiteka InStream library provides an interactive view to display floating videos.

# Installation

Simply add the dependency to your application `build.gradle`:

``` kotlin    
dependencies {    
  implementation("com.github.digiteka:SDK-instream-Android:1.0.0-5")
} 
```

# Usage

Then in the `onCreate` of your `Application` class, if you don't have one, create one, and add the following code to initialize the sdk:

``` kotlin  
//Create the DTKISConfig 
val dtkisConfig: DTKISConfig = DTKISConfig.Builder(
			mdtk = "my_MDTK_key",
		)
  .build() 
// Then initialize the InStream sdk 
InStream.initialize(applicationContext = this.applicationContext, config = dtkisConfig)
```

## Configs

### DTKISConfig

- mdtk (String) : api key

### DTKISMainPlayerConfig

- zone (String) : zone of the website in which the video is
- src (String) : ID of the video you want to play
- urlreferrer (String) : URL of the Desktop article (URL referrer)
- gdprConsentString (String) : user consent string
- tagParam (String) : optional advertisement params
- playMode (PlayMode): ON_CLICK : user must tap to play, AUTOPLAY : autoplay when loaded, VISIBLE_AT_FIFTY_PERCENT : scroll 50% to play auto

### DTKISVisiblePlayerConfig

- playerPosition (Position) : position of the visible player (TOP_START, TOP_END, BOTTOM_START, BOTTOM_END)
- widthPercent (Float) : width of the player in percentage of the parentView
- ratio (String) : ratio of the video player width / height
- horizontalMargin (Float) : margin of the visible player
- verticalMargin (Float) : margin of the visible player

## MainPlayerView & VisiblePlayer (XML)

In a XML file, call the `MainPlayerView` like this :

``` kotlin  
    <com.digiteka.instream.ui.player.MainPlayerView
                android:id="@+id/webview"
                android:layout_width="match_parent"
                android:layout_height="0dp"
                app:layout_constraintDimensionRatio="16:9"
                app:layout_constraintTop_toBottomOf="@id/firstText"
                tools:ignore="WebViewLayout" />
```

Then configure `MainPlayerView` and call this in your activity :

``` kotlin  
    val mainPlayerConfig: DTKISMainPlayerConfig = DTKISMainPlayerConfig.Builder(
			zone = "11",
			gdprConsentString = "a_consent_string",
			src = "src",
			urlReferrer = "url_referee",
			tagParam = null,
		).setPlayMode(PlayMode.VISIBLE_AT_FIFTY_PERCENT).build()

    val key = InStream.configureMainPlayer(binding.webview, mainPlayerConfig)
```

Finally, in your activity, you can add a `VisiblePlayer` with  :

``` kotlin  
        InStream.attachVisiblePlayerTo(
            parent = binding.root,
            visiblePlayerConfig = DTKISVisiblePlayerConfig(
            widthPercent = .66f,
            position = Position.BOTTOM_START,
            ratio = "16:9",
            horizontalMargin = 8f,
            verticalMargin = 8f,
            ),
            mainPlayerKey = key
        )
```

## MainPlayer et VisiblePlayer (Jetpack Compose)

In your composable Compose, you need to add a new configuration `MainPlayerView`

``` kotlin  
     val mainPlayerConfig: DTKISMainPlayerConfig = DTKISMainPlayerConfig.Builder(
			zone = "11",
			gdprConsentString = "a_consent_string",
			src = "src",
			urlReferrer = "url_referee",
			tagParam = null,
		).setPlayMode(PlayMode.VISIBLE_AT_FIFTY_PERCENT).build()
```

Then, inside `setContent`, you need to call the composable `MainPlayerComposable`

    MainPlayerComposable(
        mainPlayerView = mainPlayerView
    )

Finally, in your parent component, you can add a `VisiblePlayer`, you need to call `InStream.attachVisiblePlayerTo`  :

``` kotlin  
    setContent {
        MaterialTheme {
            Surface {
                Box(...) {
                    ... {
                        MainPlayerComposable(mainPlayerView = mainPlayerView)
                    }
                }
            }
            InStream.attachVisiblePlayerTo(
                parent = findViewById(android.R.id.content),
                visiblePlayerConfig = DTKISVisiblePlayerConfig(
                    widthPercent = .66f,
                    position = Position.BOTTOM_START,
                    ratio = "16:9",
                    horizontalMargin = 8f,
                    verticalMargin = 8f,
                ),
                mainPlayerKey = key
            )
        }
    }
```

## Logger

Optionally, you can set a custom logger in order to retrieve logs from the sdk. The logger must implement the `DTKISLogger` interface.

``` kotlin  
import android.util.Log 
import com.digiteka.instream.core.log.DTKISLogger    

object MyLogger : DTKISLogger {    
    
  private const val TAG = "MyAppLogger" 
  override fun debug(message: String) { Log.d(TAG, message) }      
  override fun info(message: String) { Log.i(TAG, message) }    
  override fun warning(message: String, throwable: Throwable?) { Log.w(TAG, message, throwable) }    
  override fun error(message: String, throwable: Throwable?) { Log.e(TAG, message, throwable) }

}   
```   

Then pass the logger:

``` kotlin  
InStream.setLogger(logger = MyLogger)   
```   

## Errors and Logs

| Type          | Code d'erreur | Niveau   | Message                                                                                                                                    | Cause                                                                          |
|---------------|---------------|----------|--------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| Configuration | DTKIS_CONF_1  | Critical | Mdtk must not be null, empty or blank. Please provide a valid Api Key.                                                                     | mdtk is null or empty                                                          |
| Configuration | DTKIS_CONF_2  | Error    | No data were provided for your Api key (mdtk), please check your Api Key is valid, and you do provide data for it in the digiteka console. | The mdtk is not valid, or no video has been configured in the digiteka console |
| Configuration | DTKIS_CONF_3  | Error    | InStream sdk has not yet been initialized. Please call `InStream.initialize` first.                                                        | `InStream.initialize` has not been called yet                                  |
| SDK           | DTKIS_SDK_1   | Critical | Unknown player id                                                                                                                          | Player id unknown                                                              |

# Sample app

You can test the Sample app using the mdtk : `01357940`.

Add your digiteka inStream mdtk key to the project's `local.properties` like following:    
``` properties
DIGITEKA_INSTREAM_MDTK=01357940
DIGITEKA_VIDEOFEED_SECRET=your_digiteka_jitpack_auth_token_here
```