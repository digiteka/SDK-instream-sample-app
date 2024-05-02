package digiteka.instream.andro.ui.samples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digiteka.instream.core.config.PlayMode
import com.digiteka.instream.core.config.player.DTKISMainPlayerConfig
import digiteka.instream.andro.databinding.VideoActivityBinding

class ClickActivity : AppCompatActivity() {

	private lateinit var binding: VideoActivityBinding

	private val sampleAdapter: SampleAdapter by lazy { SampleAdapter() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = VideoActivityBinding.inflate(layoutInflater)
		setContentView(binding.root)
		binding.videoRecyclerView.adapter = sampleAdapter

		val mainPlayerConfig: DTKISMainPlayerConfig = DTKISMainPlayerConfig.Builder(
			zone = "11",
			gdprConsentString = "BOj8iv4Oj8iwYAHABAlxCS-AAAAnF7_______9______9uz_Ov_v_f__33e87_9v_l_7_-___u_-3zd4-_1vf99yfm1-7etr3tp_87ues2_Xur__59__3z3_9phPrsk89r633A/idfa/2D2859BC-7FB6-4225-A3E8-EED25341CA93",
			src = "pqvp3r",
			urlReferrer = "test",
			tagParam = null,
		).setPlayMode(PlayMode.ON_CLICK).build()

		sampleAdapter.configureRecyclingViewPool(binding.videoRecyclerView)
		val itemWrappers = listOf(
			SampleAdapter.ItemWrapper.Void,
			SampleAdapter.ItemWrapper.Player(mainPlayerConfig),
			SampleAdapter.ItemWrapper.Void,
		)
		sampleAdapter.replaceAll(itemWrappers)
	}
}