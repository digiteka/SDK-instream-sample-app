package digiteka.instream.andro

import android.app.Application
import com.digiteka.instream.InStream
import com.digiteka.instream.core.config.DTKISConfig
import digiteka.instream.andro.logic.log.MyLogger

class SampleApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		//Create the DTKISConfig
		val dtkisConfig: DTKISConfig = DTKISConfig.Builder(
			mdtk = BuildConfig.DIGITEKA_INSTREAM_MDTK
		)
			.build()

		//Then initialize the InStream sdk
		InStream.initialize(applicationContext = this.applicationContext, config = dtkisConfig)

		// (Optional) set a custom logger
		InStream.setLogger(MyLogger)
	}
}
