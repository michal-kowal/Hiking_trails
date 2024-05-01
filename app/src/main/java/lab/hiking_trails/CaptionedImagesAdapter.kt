package lab.hiking_trails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CaptionedImagesAdapter(
    private var captions: Array<String>,
    private var imageIds: IntArray,
    private var trails: Array<Trail>
) :
    RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>() {

    private var listener: Listener? = null

    interface Listener {
        fun onClick(trail: Trail)
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return captions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cv = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_captioned_image, parent, false) as CardView
        return ViewHolder(cv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView
        val imageView = cardView.findViewById<ImageView>(R.id.info_image)
        val drawable = ContextCompat.getDrawable(cardView.context, imageIds[position])
        imageView.setImageDrawable(drawable)
        imageView.contentDescription = captions[position]
        val textView = cardView.findViewById<TextView>(R.id.info_text)
        textView.text = captions[position]

        cardView.setOnClickListener {
            listener?.onClick(trails[position])
        }
    }

    fun setData(filteredTrails: List<Trail>) {
        captions = filteredTrails.map { it.name }.toTypedArray()
        imageIds = filteredTrails.map { it.imageId }.toIntArray()
        trails = filteredTrails.toTypedArray()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView: CardView = itemView as CardView
    }


}