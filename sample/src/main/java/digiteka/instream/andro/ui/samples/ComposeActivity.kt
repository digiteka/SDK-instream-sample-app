package digiteka.instream.andro.ui.samples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digiteka.instream.InStream
import com.digiteka.instream.core.config.PlayMode
import com.digiteka.instream.core.config.Position
import com.digiteka.instream.core.config.player.DTKISMainPlayerConfig
import com.digiteka.instream.core.config.visibleplayer.DTKISVisiblePlayerConfig
import com.digiteka.instream.ui.composable.MainPlayerComposable
import com.digiteka.instream.ui.player.MainPlayerView
import digiteka.instream.andro.R

class ComposeActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val text = getText(R.string.lorem_text)
		val mainPlayerView = MainPlayerView(this)

		val mainPlayerConfig: DTKISMainPlayerConfig = DTKISMainPlayerConfig.Builder(
			zone = "11",
			gdprConsentString = "BOj8iv4Oj8iwYAHABAlxCS-AAAAnF7_______9______9uz_Ov_v_f__33e87_9v_l_7_-___u_-3zd4-_1vf99yfm1-7etr3tp_87ues2_Xur__59__3z3_9phPrsk89r633A/idfa/2D2859BC-7FB6-4225-A3E8-EED25341CA93",
			src = "pqvp3r",
			urlReferrer = "test",
			tagParam = null,
		).setPlayMode(PlayMode.VISIBLE_AT_FIFTY_PERCENT).build()
		val key = InStream.configureMainPlayer(mainPlayerView, mainPlayerConfig)

		setContent {
			MaterialTheme {
				Surface {
					Box(
						contentAlignment = Alignment.Center,
						modifier = Modifier.fillMaxSize(),
					) {
						Column(
							Modifier
								.padding(24.dp)
								.verticalScroll(rememberScrollState()),
							horizontalAlignment = Alignment.CenterHorizontally,
						) {
							Text(text = text.toString())
							MainPlayerComposable(
								mainPlayerView = mainPlayerView
							)
							Text(text = text.toString())
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
	}
}
