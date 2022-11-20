package com.example.movierater_advanced

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Rating
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.movierater_advanced.databinding.ActivityRatingBinding

class Rating : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter: ArrayAdapter<Movie_2>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply{
            //actionbar
            val actionbar = supportActionBar
            //set actionbar title
            actionbar!!.title = "MovieRater"
            //set back button
            actionbar.setDisplayHomeAsUpEnabled(true)

            // Initalize SqlHelper
            sqLiteHelper = SQLiteHelper(this@Rating)


            loadReview(
                intent.getParcelableExtra("Review")!!
            )

            btnSubmit.setOnClickListener{
                validation()

            }

        }
    }
    fun validation(){
        binding.apply {
            if(message.text.isEmpty()){
                message.error = "Message cannot be empty!"
                return
            }else{
                addreview(intent.getParcelableExtra("Movie")!!,
                    intent.getParcelableExtra("Review")!!)
            }
        }
    }
    fun loadReview(review:RatingModel){
        binding.apply {
            if(review.rating > 0){
                btnSubmit.text = "Save"
                message.setText(review.message)
                stars.rating = review.rating
            }

        }
    }

    private fun addreview(movie: Movie_2,review:RatingModel){
        val rating = findViewById<RatingBar>(R.id.stars)
        val rate = rating.rating.toString()
        val message = findViewById<EditText>(R.id.message)

        val reviewmodel = RatingModel(
            movieid = movie.id,
            rating = rate.toFloat(),
            message = message.text.toString()
        )


        val status = sqLiteHelper.addReview(reviewmodel)
        if(status > -1){
            val intent = Intent(this@Rating,MovieDetail::class.java)
            intent.putExtra("Movie",movie)
            intent.putExtra("Review",reviewmodel)
            startActivity(intent)


            Toast.makeText(applicationContext,"Review success" + message.toString(),Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(applicationContext,"Review failed",Toast.LENGTH_LONG).show()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
            NavigateBack(intent.getParcelableExtra("Movie")!!,
                intent.getParcelableExtra("Review")!!)
            return true

    }
    fun NavigateBack(movie: Movie_2,review:RatingModel){
        binding.apply {
            val intent = Intent(this@Rating,MovieDetail::class.java)
            intent.putExtra("Movie",movie)
            if(btnSubmit.text.toString() == "Save"){
                intent.putExtra("Review",review)
            }
            startActivity(intent)
        }

    }
}

