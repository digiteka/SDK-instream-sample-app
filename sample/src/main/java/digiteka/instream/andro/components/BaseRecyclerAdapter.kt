package digiteka.instream.andro.components

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, H : RecyclerView.ViewHolder>(data: List<T>? = null) : RecyclerView.Adapter<H>() {

	private val data: MutableList<T> = data?.toMutableList() ?: mutableListOf()

	override fun getItemCount(): Int {
		return data.size
	}

	open fun getItem(position: Int): T? {
		return if (position < 0 || position >= data.size) null else data[position]
	}


	open fun replaceAll(elem: List<T>) {
		data.clear()
		data.addAll(elem)
		notifyDataSetChanged()
	}


	fun clear() {
		data.clear()
		notifyDataSetChanged()
	}

}
