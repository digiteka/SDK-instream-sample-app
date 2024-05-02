# Librairie InStream de Digiteka

[![en](https://img.shields.io/badge/lang-en-red.svg)](ReadMe.md)
[![fr](https://img.shields.io/badge/lang-fr-blue.svg)](ReadMe.fr.md)

La librairie InStream de Digiteka fourni un composant interactif pour afficher des vidéos flottantes.

# Installation

Ajouter la dépendance à votre fichier `build.gradle`:

``` kotlin    
dependencies {    
  implementation("com.github.digiteka:SDK-instream-Android:1.0.0-5")
}   
```   

# Utilisation

Dans la méthode `onCreate` de votre classe `Application`, si vous n'en avez pas, créez-en une, et ajoutez-le code suivant pour initialiser la librairie :

``` kotlin  
//Créer la configuration DTKISConfig 
val dtkisConfig: DTKISConfig = DTKISConfig.Builder(
			mdtk = "Ma_cle_MDTK",
		)
  .build() 
// Puis initialiser la librairie InStream 
InStream.initialize(applicationContext = this.applicationContext, config = dtkisConfig)
```

## Configs

### DTKISConfig

- mdtk (String) : api key

### DTKISMainPlayerConfig

- zone (String) : permet de déterminer sur quelle zone du site la vidéo a été publiée
- src (String) : permet de définir l’ID de la vidéo à jouer
- urlreferrer (String) : permet de définir l’URL correspondant à l’article sur Desktop (URL referrer)
- gdprConsentString (String) : permet de nous communiquer la chaîne de consentement RGPD de l’utilisateur
- tagParam (String) : permet de nous communiquer des paramètres publicitaires facultatifs
- playMode (PlayMode): ON_CLICK : appuyer pour lancer, AUTOPLAY : lancement automatique lorsque la vidéo est chargée, VISIBLE_AT_FIFTY_PERCENT : scroll à 50% pour lancer la vidéo automatiquement

### DTKISVisiblePlayerConfig

- playerPosition (Position) : position du visible player (TOP_START, TOP_END, BOTTOM_START, BOTTOM_END)
- widthPercent (Float) : largeur du visible player en pourcentage de la vue parent
- ratio (String) : ratio du video player largeur / hauteur
- horizontalMargin (Float) : margin du visible player
- verticalMargin (Float) : margin du visible player

## MainPlayerView & VisiblePlayer (XML)

Dans un fichier XML, appeler le `MainPlayerView` comme ceci :

``` kotlin  
    <com.digiteka.instream.ui.player.MainPlayerView
                android:id="@+id/webview"
                android:layout_width="match_parent"
                android:layout_height="0dp"
                app:layout_constraintDimensionRatio="16:9"
                app:layout_constraintTop_toBottomOf="@id/firstText"
                tools:ignore="WebViewLayout" />
```

Puis configurer le `MainPlayerView` en appelant dans votre activité :

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

Dans votre activité, si vous voulez rajouter un `VisiblePlayer`, il suffit de rajouter ceci  :

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

Dans votre composant Compose, il faut d'abord rajouter la configuration du `MainPlayerView`

``` kotlin  
    val mainPlayerConfig: DTKISMainPlayerConfig = DTKISMainPlayerConfig.Builder(
			zone = "11",
			gdprConsentString = "a_consent_string",
			src = "src",
			urlReferrer = "url_referee",
			tagParam = null,
		).setPlayMode(PlayMode.VISIBLE_AT_FIFTY_PERCENT).build()
```

Puis dans le `setContent`, il vous devez appeler le composable `MainPlayerComposable`

    MainPlayerComposable(
        mainPlayerView = mainPlayerView
    )

Enfin, dans votre composant parent, si vous voulez rajouter un `VisiblePlayer`, il suffit d'appeler `InStream.attachVisiblePlayerTo  :

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

Il est possible de définir un logger personalisé pour récupérer les logs de la librairie. Le logger doit implémenter l'interface `DTKISLogger`.

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

Puis passer le logger à la librairie :

``` kotlin  
InStream.setLogger(logger = MyLogger)   
```   

## Erreurs et Logs

| Type          | Code d'erreur | Niveau   | Message                                                                                                                                    | Cause                                                        |
|---------------|---------------|----------|--------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------|
| Configuration | DTKIS_CONF_1  | Critical | Mdtk must not be null, empty or blank. Please provide a valid Api Key.                                                                     | mdtk nul ou vide                                             |
| Configuration | DTKIS_CONF_2  | Error    | No data were provided for your Api key (mdtk), please check your Api Key is valid, and you do provide data for it in the digiteka console. | Le tableau data est vide ou aucune zone ne contient de vidéo |
| Configuration | DTKIS_CONF_3  | Error    | InStream sdk has not yet been initialized. Please call `InStream.initialize` first.                                                        | InStream.initialize n'ont pas encore été appelé              |
| SDK           | DTKIS_SDK_1   | Critical | Unknown player id                                                                                                                          | Id de player inconnu                                         |

# Application de démonstration

Vous pouvez tester l'application de démonstration en utilisant le mdtk : `01357940`.
Pour ce faire, ajoutez votre mdtk à la propriété `local.properties` du projet comme suit :    
``` properties
DIGITEKA_INSTREAM_MDTK=01357940
DIGITEKA_VIDEOFEED_SECRET=your_digiteka_jitpack_auth_token_here
```
