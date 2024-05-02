package digiteka.instream.andro.ui.samples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digiteka.instream.InStream
import com.digiteka.instream.core.config.PlayMode
import com.digiteka.instream.core.config.Position
import com.digiteka.instream.core.config.player.DTKISMainPlayerConfig
import com.digiteka.instream.core.config.visibleplayer.DTKISVisiblePlayerConfig
import digiteka.instream.andro.databinding.ScrollExampleActivityBinding

class ScrollActivity : AppCompatActivity() {

	private lateinit var binding: ScrollExampleActivityBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ScrollExampleActivityBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val mainPlayerConfig: DTKISMainPlayerConfig = DTKISMainPlayerConfig.Builder(
			zone = "11",
			gdprConsentString = "BOj8iv4Oj8iwYAHABAlxCS-AAAAnF7_______9______9uz_Ov_v_f__33e87_9v_l_7_-___u_-3zd4-_1vf99yfm1-7etr3tp_87ues2_Xur__59__3z3_9phPrsk89r633A/idfa/2D2859BC-7FB6-4225-A3E8-EED25341CA93",
			src = "pqvp3r",
			urlReferrer = "test",
			tagParam = null,
		).setPlayMode(PlayMode.VISIBLE_AT_FIFTY_PERCENT).build()

		val key = InStream.configureMainPlayer(binding.webview, mainPlayerConfig)
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
	}
}