package com.example.movierater_advanced

//import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movielist: ArrayList<Movie_2> = ArrayList()
    private var onClickItem: ((Movie_2) -> Unit)? = null
    private var onClickDeleteItem: ((Movie_2) -> Unit)? = null
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
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
//        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.activity_add_movie, parent, false)
//        // Give the view as it is
//        return MovieViewHolder(v)
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movielist[position]
        holder.bindView(movie)
        holder.itemView.setOnClickListener{onClickItem?.invoke(movie)}
        holder.delete.setOnClickListener{onClickDeleteItem?.invoke((movie))}

    }

    override fun getItemCount(): Int {
        return movielist.size
    }

    class MovieViewHolder(var view: View): ViewHolder(view){
//        val language_grp= view.findViewById<RadioGroup>(R.id.group_language)
//        val language_button = language_grp.checkedRadioButtonId
//        val chk_language = view.findViewById<RadioButton>(language_button)
//        val language_final = chk_language.text.toString()


        private var id = view.findViewById<TextView>(R.id.movieid)
        private var name = view.findViewById<TextView>(R.id.list_name)
        private var description = view.findViewById<TextView>(R.id.list_description)
        private var language = view.findViewById<TextView>(R.id.list_language)
        private var date = view.findViewById<TextView>(R.id.list_date)
        var delete = view.findViewById<Button>(R.id.delete)

        fun bindView(std:Movie_2){
            id.text = std.id.toString()
            name.setText(std.name)
            description.setText(std.description)
            language.setText(std.language)
            date.setText(std.date)

        }

    }
}