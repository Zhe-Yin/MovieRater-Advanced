package com.example.movierater_advanced

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.movierater_advanced.databinding.ActivityMovieDetailBinding



class MovieDetail : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var  recyclerView: RecyclerView
    private var adapter:MovieAdapter? = null
    private  var movie:Movie_2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply{
            //actionbar
            val actionbar = supportActionBar
            //set actionbar title
            actionbar!!.title = "MovieRater"
            //set back button
            actionbar.setDisplayHomeAsUpEnabled(true)

            // Long press for Review
            registerForContextMenu(message);

            // Info retrieve from EditMovie / AddMovie after intent
            loadMovie(
                intent.getParcelableExtra("Movie")!!,
                intent.getParcelableExtra("Review")!!
            )

        }


    }
    private fun loadMovie(movie: Movie_2,review:RatingModel) {
        binding.apply {
            title.text = movie.name
            overview.text = movie.description
            language.text = movie.language
            date.text = movie.date
            println(movie.below13)
//            below13.text = movie.below13.toString()
            if(movie.below13 == false){
                below13.text = "No"
            }else{
                if (movie.vulgar == true){
                    languageused.setText("(Violence)")
                    language.visibility = View.VISIBLE
                }else{
                    languageused.text = ""
                }
                if(movie.violence == true){
                    violence.setText("(Vulgar)")
                    violence.visibility = View.VISIBLE
                }else{
                    violence.text = ""
                }
            }
            if(review.message != "N/A"){
                val star_layout = findViewById<LinearLayout>(R.id.rating_star)
                star_layout.visibility = View.VISIBLE

                rating.text = review.rating.toString()
                message.text = review.message.toString()
                stars.rating = review.rating
            }

//            if (movie.review != null) {
//                textViewMovieRating.text = "${movie.review} | ${movie.rating}/5"
//            } else {
//                textViewMovieRating.text = "No reviews yet.\n" +
//                        "Long press here to add your review"
//                registerForContextMenu(textViewMovieRating)
//            }
        }
    }


    // Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.moviedetail, menu)
        R.menu.moviedetail
        return true
    }
    // Navigate to Main Page
    override fun onSupportNavigateUp(): Boolean {
        NavigateBack(intent.getParcelableExtra("Movie")!!,
            intent.getParcelableExtra("Review")!!)
        return true
    }

    // Context Menu
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        binding.apply {

            if(rating.text.isEmpty()){
                menu.add(0, v.id, 0, "Add Review")
            }else{
                menu.add(0, v.id, 0, "Edit Review")
            }

        }


    }
    fun UpdateReview(movie:Movie_2,review:RatingModel){
        val intent = Intent(this@MovieDetail, Rating::class.java)
        intent.putExtra("Review",review)
        intent.putExtra("Movie",movie)
        startActivity(intent)
    }
    fun NavigateBack(movie:Movie_2,review:RatingModel){
        val intent = Intent(this@MovieDetail, MainActivity::class.java)

        intent.putExtra("Review",review)
        intent.putExtra("Movie",movie)
        startActivity(intent)
    }
    // Context menu item select listener
    override fun onContextItemSelected(item: MenuItem): Boolean {
//        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val intent = intent
        if(item.title == "Add Review"){

            binding.apply {
                UpdateReview(intent.getParcelableExtra("Movie")!!,
                    intent.getParcelableExtra("Review")!!)
            }

        }else if(item.title == "Edit Review"){

            binding.apply {

                UpdateReview( intent.getParcelableExtra("Movie")!!,
                    intent.getParcelableExtra("Review")!!)
            }
        }

        return true
    }




}






