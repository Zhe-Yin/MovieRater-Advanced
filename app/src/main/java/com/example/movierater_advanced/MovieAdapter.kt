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
    fun addItems(items:ArrayList<Movie_2>) {
        this.movielist = items
        notifyDataSetChanged()
    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
//        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.activity_add_movie, parent, false)
//        // Give the view as it is
//        return MovieViewHolder(v)
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.activity_add_movie,parent,false)
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movielist[position]
        holder.bindView(movie)
    }

    override fun getItemCount(): Int {
        return movielist.size
    }

    class MovieViewHolder(var view: View): ViewHolder(view){
        val language_grp= view.findViewById<RadioGroup>(R.id.group_language)
        val language_button = language_grp.checkedRadioButtonId
        val chk_language = view.findViewById<RadioButton>(language_button)

        private var id = view.findViewById<TextView>(R.id.movieid)
        private var name = view.findViewById<EditText>(R.id.name).toString()
        private var description = view.findViewById<EditText>(R.id.description).toString()
        private var language = view.findViewById<EditText>(chk_language.id).toString()
        private var date = view.findViewById<EditText>(R.id.date).toString()

        fun bindView(std:Movie_2){
            id.text = std.id.toString()
            name = std.name
            description = std.description
            language = std.language
            date = std.date

        }

    }
}