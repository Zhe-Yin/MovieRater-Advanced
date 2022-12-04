package a201457F.assignment_1.movierater_advanced

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.movierater_advanced.R
import com.example.movierater_advanced.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sqLiteHelper: SQLiteHelper
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

            sqLiteHelper = SQLiteHelper(this@MainActivity)

            loadMovies(sqLiteHelper.getAllMovie(),sqLiteHelper.getAllReview())

        }
    }

    private fun loadMovies(movies: ArrayList<Movie_2>, review:ArrayList<RatingModel>){
        adapter = MovieAdapter(this, R.layout.movielist_item, movies)
        val listView: ListView = findViewById(R.id.recyclerview)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, MovieDetail::class.java)
            val movie = movies[position]
            val rating = review[position]
            intent.putExtra("Movie",movie)
            intent.putExtra("Review",rating)

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
    menuInflater.inflate(R.menu.main_edit, menu)
    super.onCreateContextMenu(menu, v, menuInfo)
}

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.title) {
            "Edit" -> {
                val movie = adapter.getItem(info.position)!!
                val intent = Intent(this, EditMovie::class.java)
                intent.putExtra("Movie",movie)

                startActivity(intent)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }



}


