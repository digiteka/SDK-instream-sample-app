package digiteka.instream.andro.logic.entities

import android.app.Activity

data class SampleEntity<A : Activity>(
	val label: String,
	val activityClass: Class<A>,
)