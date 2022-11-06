package com.example.movierater_advanced

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movierater_advanced.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var  recyclerView: RecyclerView
    private var adapter:MovieAdapter? = null
    private  var movie:Movie_2? = null


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

            initRecyclerView()

            sqLiteHelper = SQLiteHelper(this@MainActivity)

            getMovieInfo()
//            val movieinfo = findViewById<LinearLayout>(R.id.movieinfo)
//            movieinfo.visibility = View.GONE
            adapter?.setOnClickUpdateMenuItem {
                registerForContextMenu(findViewById(R.id.list_name))

            }

            adapter?.onClickDetailItem {
                val intent = Intent(this@MainActivity, MovieDetail::class.java)

                intent.putExtra("id",it.id)
                intent.putExtra("name",it.name)
                intent.putExtra("description",it.description)
                intent.putExtra("date",it.date)
                intent.putExtra("language",it.language)
                intent.putExtra("below13",it.below13)
                intent.putExtra("violence",it.violence)
                intent.putExtra("vulgar",it.vulgar)

                startActivity(intent)
            }



//             Long press for Review


//            adapter?.setOnClickDeleteItem {
//                deleteMovie(it.id)
//            }
        }

    }



    private fun getMovieInfo(){
        val movielist = sqLiteHelper.getAllMovie()
        Log.e("Listing","${movielist.size}")

        adapter?.addItems(movielist)

    }
    private fun initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        adapter = MovieAdapter()
        recyclerView.adapter = adapter


//        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

    }

//    private fun deleteMovie(id:Int){
//        sqLiteHelper.deleteMoviebyId(id)
//        getMovieInfo()
//    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        R.menu.main
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.addmovie2 -> {
            addMovie()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    // Context Menu
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(0, v.id, 0, "Edit")

    }

    // Context menu item select listener
    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(item.title == "Edit"){


                val intent = Intent(this@MainActivity, EditMovie::class.java)

                intent.putExtra("id",findViewById<TextView>(R.id.list_movieid).text.toString())
                intent.putExtra("name",findViewById<TextView>(R.id.list_name).text.toString())
                intent.putExtra("description",findViewById<TextView>(R.id.list_description).text.toString())
                intent.putExtra("date",findViewById<TextView>(R.id.list_date).text.toString())
                intent.putExtra("language",findViewById<TextView>(R.id.list_language).text.toString())
                intent.putExtra("below13",findViewById<TextView>(R.id.list_below13).text.toString())
                intent.putExtra("violence",findViewById<TextView>(R.id.list_violence).text.toString())
                intent.putExtra("vulgar",findViewById<TextView>(R.id.list_vulgar).text.toString())
                startActivity(intent)



        }

        return true
    }

    private fun addMovie(){
        binding.apply{
            val intent = Intent(this@MainActivity, AddMovie::class.java)
            startActivity(intent)
        }
    }
}