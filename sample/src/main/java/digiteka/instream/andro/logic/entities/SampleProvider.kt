package digiteka.instream.andro.logic.entities

import digiteka.instream.andro.ui.samples.AutomaticActivity
import digiteka.instream.andro.ui.samples.ClickActivity
import digiteka.instream.andro.ui.samples.ComposeActivity
import digiteka.instream.andro.ui.samples.ScrollActivity

object SampleProvider {

	fun getSamples(): List<SampleEntity<*>> {
		return listOf(
			SampleEntity(
				label = "Click UI Example",
				activityClass = ClickActivity::class.java,
			),
			SampleEntity(
				label = "Scroll UI Example",
				activityClass = ScrollActivity::class.java,
			),
			SampleEntity(
				label = "Automatic UI Example",
				activityClass = AutomaticActivity::class.java,
			),
			SampleEntity(
				label = "Jetpack Compose UI Example",
				activityClass = ComposeActivity::class.java,
			),
		)
	}
}