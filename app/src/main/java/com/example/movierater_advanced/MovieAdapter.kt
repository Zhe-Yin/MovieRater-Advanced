package com.example.movierater_advanced

//import android.R
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder



class MovieAdapter(): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movielist: ArrayList<Movie_2> = ArrayList()
    private var onClickItem: ((Movie_2) -> Unit)? = null
    private var onClickDeleteItem: ((Movie_2) -> Unit)? = null
    private var onClickUpdateMenuItem: ((Movie_2) -> Unit)? = null
    private var onClickDetailItem: ((Movie_2) -> Unit)? = null
    fun addItems(items:ArrayList<Movie_2>) {
        this.movielist = items
        notifyDataSetChanged()
    }
     fun setOnClickItem(callback: (Movie_2) -> Unit){
        this.onClickItem = callback

    }
    fun setOnClickDeleteItem(callback: (Movie_2) -> Unit){
        this.onClickDeleteItem = callback

    }

    fun setOnClickUpdateMenuItem(callback: (Movie_2) -> Unit){
        this.onClickUpdateMenuItem = callback

    }

    fun onClickDetailItem(callback: (Movie_2) -> Unit){
        this.onClickDetailItem = callback

    }

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder (

        LayoutInflater.from(parent.context).inflate(R.layout.movielist_item,parent,false)

    )


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movielist[position]
        holder.bindView(movie)
        holder.itemView.setOnClickListener{onClickItem?.invoke(movie)}
        holder.delete.setOnClickListener{onClickDeleteItem?.invoke((movie))}
//        holder.movie_name.setOnLongClickListener{onClickUpdateMenuItem?.invoke(movie)}
        holder.movie_image.setOnClickListener { onClickDetailItem?.invoke(movie) }
holder.movie_name.setOnLongClickListener {
    onClickUpdateMenuItem?.invoke(movie)
    true
}


    }



    override fun getItemCount(): Int {
        return movielist.size
    }




    class MovieViewHolder(var view: View): ViewHolder(view){

        private var id = view.findViewById<TextView>(R.id.list_movieid)
        private var name = view.findViewById<TextView>(R.id.list_name)
        private var description = view.findViewById<TextView>(R.id.list_description)
        private var language = view.findViewById<TextView>(R.id.list_language)
        private var date = view.findViewById<TextView>(R.id.list_date)
        private var below13 = view.findViewById<TextView>(R.id.list_below13)
        private var vulgar = view.findViewById<TextView>(R.id.list_vulgar)
        private var violence = view.findViewById<TextView>(R.id.list_violence)
        var delete = view.findViewById<Button>(R.id.delete)
        var movie_name = view.findViewById<TextView>(R.id.list_name)
        var movie_image = view.findViewById<ImageView>(R.id.image)
//        var item = view.findViewById<LinearLayout>(R.id.movieitem)

        fun bindView(movie:Movie_2){
            id.text = movie.id.toString()
            name.setText(movie.name)
            description.setText(movie.description)
            language.setText(movie.language)
            date.setText(movie.date)
            below13.setText(movie.below13)
            violence.setText(movie.violence)
            vulgar.setText(movie.vulgar)

        }

    }

}