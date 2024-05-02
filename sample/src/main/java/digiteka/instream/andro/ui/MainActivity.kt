package digiteka.instream.andro.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import digiteka.instream.andro.databinding.MainActivityBinding
import digiteka.instream.andro.databinding.SampleItemBinding
import digiteka.instream.andro.logic.entities.SampleEntity
import digiteka.instream.andro.logic.entities.SampleProvider

class MainActivity : AppCompatActivity() {

	private lateinit var binding: MainActivityBinding
	private val mainAdapter: MainAdapter by lazy { MainAdapter(::onSampleClicked) }
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = MainActivityBinding.inflate(layoutInflater)
		setContentView(binding.root)
		binding.mainActivityRecyclerView.adapter = mainAdapter
		setSamples()
	}

	private fun setSamples() {
		mainAdapter.setSamples(SampleProvider.getSamples())
	}

	private fun onSampleClicked(sample: SampleEntity<*>) {
		val intent = Intent(this, sample.activityClass)
		startActivity(intent)
	}

	private class MainAdapter(
		private val onSampleClicked: (SampleEntity<*>) -> Unit
	) : RecyclerView.Adapter<MainAdapter.SampleItem>() {
		companion object {
			private const val TYPE_SAMPLE = 0
		}

		private val samples = mutableListOf<SampleEntity<*>>()

		@SuppressLint("NotifyDataSetChanged")
		fun setSamples(samples: List<SampleEntity<*>>) {
			this.samples.clear()
			this.samples.addAll(samples)
			notifyDataSetChanged()
		}

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleItem {
			val layoutInflater = LayoutInflater.from(parent.context)
			return when (viewType) {
				TYPE_SAMPLE -> SampleItem(SampleItemBinding.inflate(layoutInflater, parent, false), onSampleClicked)
				else -> throw RuntimeException("Unknown viewType $viewType")
			}
		}

		override fun getItemCount(): Int = samples.size

		override fun onBindViewHolder(holder: SampleItem, position: Int) = samples.getOrNull(position)?.let { holder.bind(it) } ?: Unit

		override fun getItemViewType(position: Int): Int = TYPE_SAMPLE
		private class SampleItem(
			private val binding: SampleItemBinding,
			val onSampleClicked: (SampleEntity<*>) -> Unit
		) : RecyclerView.ViewHolder(binding.root) {

			fun bind(sample: SampleEntity<*>) {
				// label binding
				binding.sampleItemLabel.text = sample.label

				//Button binding
				binding.sampleItemButton.setOnClickListener { onSampleClicked(sample) }
			}
		}
	}
}