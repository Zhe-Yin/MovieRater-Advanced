package a201457F.assignment_1.movierater_advanced

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.movierater_advanced.R
import com.example.movierater_advanced.databinding.ActivityMovieDetailBinding



class MovieDetail : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding


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
    private fun loadMovie(movie: Movie_2, review: RatingModel) {
        binding.apply {
            title.text = movie.name
            overview.text = movie.description
            language.text = movie.language
            date.text = movie.date
            println(movie.below13)
//            below13.text = movie.below13.toString()
            if(movie.below13 == false){
                below13.text = "Yes"
            }else{
                below13.text = "No"
                println(movie.violence)
                if (movie.vulgar == true){
                    languageused.setText("(Vulgar)")
                    languageused.visibility = View.VISIBLE
                }else{
                    languageused.text = ""
                }
                if(movie.violence == true){
                    violence.setText("(Violence)")
                    violence.visibility = View.VISIBLE
                }else{
                    violence.text = ""
                }
            }
            if(review.message != "N/A"){
                val star_layout = findViewById<LinearLayout>(R.id.rating_star)
                star_layout.visibility = View.VISIBLE
                message.text = review.message.toString()
                stars.rating = review.rating
            }

        }
    }


//
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

            if(message.text.isEmpty()){
                menu.add(0, v.id, 0, "Add Review")
            }else{
                menu.add(0, v.id, 0, "Edit Review")
            }

        }


    }
    fun UpdateReview(movie: Movie_2, review: RatingModel){
        val intent = Intent(this@MovieDetail, Rating::class.java)
        intent.putExtra("Review",review)
        intent.putExtra("Movie",movie)
        startActivity(intent)
    }
    fun NavigateBack(movie: Movie_2, review: RatingModel){
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






