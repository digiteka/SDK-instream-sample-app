package digiteka.instream.andro.ui.samples

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digiteka.instream.InStream
import com.digiteka.instream.core.config.Position
import com.digiteka.instream.core.config.player.DTKISMainPlayerConfig
import com.digiteka.instream.core.config.visibleplayer.DTKISVisiblePlayerConfig
import digiteka.instream.andro.components.BaseRecyclerAdapter
import digiteka.instream.andro.databinding.PlayerItemBinding
import digiteka.instream.andro.databinding.VoidItemBinding

internal class SampleAdapter : BaseRecyclerAdapter<SampleAdapter.ItemWrapper, SampleAdapter.ViewHolder>() {
	companion object {
		private const val TYPE_VOID = 0
		private const val TYPE_PLAYER = 1
	}

	abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		abstract fun bind(wrapper: ItemWrapper?)
	}

	sealed class ItemWrapper(val viewType: Int) {
		data object Void : ItemWrapper(TYPE_VOID)
		data class Player(val config: DTKISMainPlayerConfig, val parent: ViewGroup? = null) : ItemWrapper(TYPE_PLAYER)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return when (viewType) {
			TYPE_VOID -> VoidItem(
				VoidItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			)

			TYPE_PLAYER -> PlayerItem(
				PlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
			)

			else -> throw IllegalStateException("Unexpected view type")
		}
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

	override fun getItemViewType(position: Int) = getItem(position)?.viewType ?: RecyclerView.NO_POSITION
	fun configureRecyclingViewPool(videoRecyclerView: RecyclerView) {
		// Important do not recycle player view
		videoRecyclerView.recycledViewPool.setMaxRecycledViews(TYPE_PLAYER, 0)
	}

	private class VoidItem(
		binding: VoidItemBinding,
	) : ViewHolder(binding.root) {

		override fun bind(wrapper: ItemWrapper?) = Unit
	}

	private class PlayerItem(
		private val binding: PlayerItemBinding
	) : ViewHolder(binding.root) {

		override fun bind(wrapper: ItemWrapper?) {
			(wrapper as? ItemWrapper.Player)?.let { player ->
				val key = InStream.configureMainPlayer(binding.webview, player.config)

				// init visible player
				if (player.parent != null)
					InStream.attachVisiblePlayerTo(
						parent = player.parent,
						visiblePlayerConfig = DTKISVisiblePlayerConfig(
							widthPercent = .33f,
							position = Position.TOP_END,
							ratio = "1.5",
							horizontalMargin = 10f,
							verticalMargin = 16f
						),
						mainPlayerKey = key
					)
			}
		}
	}
}