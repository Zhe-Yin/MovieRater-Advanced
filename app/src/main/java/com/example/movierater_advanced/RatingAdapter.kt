package com.example.movierater_advanced

//import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class RatingAdapter: RecyclerView.Adapter<RatingAdapter.RatingViewHolder>() {
    private var reviewlist: ArrayList<RatingModel> = ArrayList()
    private var onClickItem: ((RatingModel) -> Unit)? = null
    private var onClickDeleteItem: ((RatingModel) -> Unit)? = null
    private var onClickUpdateMenuItem: ((RatingModel) -> Unit)? = null
    private var onClickDetailItem: ((RatingModel) -> Unit)? = null
    fun addItems(items: ArrayList<RatingModel>) {
        this.reviewlist = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (RatingModel) -> Unit) {
        this.onClickItem = callback

    }

    fun setOnClickDeleteItem(callback: (RatingModel) -> Unit) {
        this.onClickDeleteItem = callback

    }

    fun setOnClickUpdateMenuItem(callback: (RatingModel) -> Unit) {
        this.onClickUpdateMenuItem = callback

    }

    fun onClickDetailItem(callback: (RatingModel) -> Unit) {
        this.onClickDetailItem = callback

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RatingViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.movielist_item, parent, false)
    )

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        val review = reviewlist[position]
        holder.bindView(review)
        holder.itemView.setOnClickListener { onClickItem?.invoke(review) }
        holder.delete.setOnClickListener { onClickDeleteItem?.invoke((review)) }
        holder.movie_name.setOnClickListener { onClickUpdateMenuItem?.invoke(review) }
        holder.movie_image.setOnClickListener { onClickDetailItem?.invoke(review) }
    }

    override fun getItemCount(): Int {
        return reviewlist.size
    }

    class RatingViewHolder(var view: View) : ViewHolder(view) {


        private var id = view.findViewById<TextView>(R.id.list_id)
        private var stars = view.findViewById<TextView>(R.id.list_stars)
        private var review = view.findViewById<TextView>(R.id.list_review)
        private var movie_id = view.findViewById<TextView>(R.id.movie_id)

        var delete = view.findViewById<Button>(R.id.delete)
        var movie_name = view.findViewById<TextView>(R.id.list_name)
        var movie_image = view.findViewById<ImageView>(R.id.image)

        fun bindView(rating: RatingModel) {
            id.text = rating.id.toString()
            stars.text = rating.stars.toString()
            review.text = rating.review.toString()
            movie_id.text = rating.movie_id.toString()


        }

    }
}
