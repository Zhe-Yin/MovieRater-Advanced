package com.example.movierater_advanced

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movierater_advanced.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter:ArrayAdapter<Movie_2>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.apply {
            //actionbar
            val actionbar = supportActionBar
            //set actionbar title
            actionbar!!.title = "MovieRater"

         // Start Recyclerview
//            initRecyclerView()

            sqLiteHelper = SQLiteHelper(this@MainActivity)

//            getMovieInfo()
            loadMovies(sqLiteHelper.getAllMovie(),sqLiteHelper.getAllReview())




            // Click Image -> Movie Detail
//            adapter?.onClickDetailItem {
//                val intent = Intent(this@MainActivity, MovieDetail::class.java)
//                intent.putExtra("id",it.id.toString())
//                intent.putExtra("name",it.name)
//                intent.putExtra("description",it.description)
//                intent.putExtra("date",it.date)
//                intent.putExtra("language",it.language)
//                intent.putExtra("below13",it.below13)
//                intent.putExtra("violence",it.violence)
//                intent.putExtra("vulgar",it.vulgar)
//                startActivity(intent)
//            }
        }
    }


//    private fun getMovieInfo() {
//        val movielist = sqLiteHelper.getAllMovie()
//        Log.e("Listing", "${movielist.size}")
//
//        adapter?.addItems(movielist)
//    }
    private fun loadMovies(movies: ArrayList<Movie_2>,review:ArrayList<RatingModel>){
        adapter = MovieAdapter(this, R.layout.movielist_item, movies)
        val listView: ListView = findViewById(R.id.recyclerview)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, MovieDetail::class.java)
            val movie = movies[position]
            val rating = review[position]
            intent.putExtra("Movie",movie)
            intent.putExtra("Review",rating)


//            intent.putExtra("name",movie.name)
//            intent.putExtra("description",movie.description)
//            intent.putExtra("date",movie.date)
//            intent.putExtra("language",movie.language)
//            intent.putExtra("below13",movie.below13)
//            intent.putExtra("violence",movie.violence)
//            intent.putExtra("vulgar",movie.vulgar)
//            intent.putExtra("rating",rating.rating.toString())
//            intent.putExtra("message",rating.message)
            startActivity(intent)
        }

        registerForContextMenu(listView)
    }

    // Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        R.menu.main
        return true
    }

    // Options
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.addmovie2 -> {
            addMovie()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    private fun addMovie(){
        binding.apply{
            val intent = Intent(this@MainActivity, AddMovie::class.java)
            startActivity(intent)
        }
    }


override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
) {
    menuInflater.inflate(R.menu.moviedetail, menu)
    super.onCreateContextMenu(menu, v, menuInfo)
}

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.title) {
            "Edit" -> {
                val movie = adapter.getItem(info.position)!!
                val intent = Intent(this, EditMovie::class.java)
                intent.putExtra("Movie",movie)
                println(movie.below13)
//                intent.putExtra("name",movie.name)
//                intent.putExtra("description",movie.description)
//                intent.putExtra("date",movie.date)
//                intent.putExtra("language",movie.language)
//                intent.putExtra("below13",movie.below13)
//                intent.putExtra("violence",movie.violence)
//                intent.putExtra("vulgar",movie.vulgar)
//                intent.putExtra("rating",)
                startActivity(intent)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }



}


