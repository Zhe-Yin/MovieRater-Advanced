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

            val intent = intent
            if(intent.getStringExtra("message") != "N/A"){
                println(intent.getStringExtra("name"))
                btnSubmit.text = "Save"
            }


            btnSubmit.setOnClickListener{

                addreview()
            }

        }
    }

    private fun addreview(){
        val rating = findViewById<RatingBar>(R.id.stars)
        val rate = rating.getRating().toString()
        val message = findViewById<EditText>(R.id.message)

        val review = RatingModel(
            movieid = intent.getStringExtra("id")!!.toInt(),
            rating = rate.toFloat(),
            message = message.toString()
        )


        val status = sqLiteHelper.addReview(review)
        if(status > -1){
            val intent1 = Intent(this@Rating,MovieDetail::class.java)

            val intent = intent
            println(intent.getStringExtra("name"))
            intent1.putExtra("id",intent.getStringExtra("id"))
            intent1.putExtra("name","Hello world")
            intent1.putExtra("description","Hello world")
            intent1.putExtra("date",intent.getStringExtra("date"))
            intent1.putExtra("language",intent.getStringExtra("language"))
            intent1.putExtra("below13",intent.getStringExtra("below13"))
            intent1.putExtra("violence",intent.getStringExtra("violence"))
            intent1.putExtra("vulgar",intent.getStringExtra("vulgar"))
            intent1.putExtra("rating",rate)
            intent1.putExtra("message",message.text.toString())
            startActivity(intent1)
            Toast.makeText(applicationContext,"Review success" + message.toString(),Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(applicationContext,"Review failed",Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.apply {
            val intent1 = Intent(this@Rating,MovieDetail::class.java)

            val intent2 = intent
            intent1.putExtra("id",intent2.getStringExtra("id"))
            intent1.putExtra("name",intent2.getStringExtra("name"))
            intent1.putExtra("description",intent2.getStringExtra("description"))
            intent1.putExtra("date",intent2.getStringExtra("date"))
            intent1.putExtra("language",intent2.getStringExtra("language"))
            intent1.putExtra("below13",intent2.getStringExtra("below13"))
            intent1.putExtra("violence",intent2.getStringExtra("violence"))
            intent1.putExtra("vulgar",intent2.getStringExtra("vulgar"))
            if(btnSubmit.text.toString() == "Save"){
                intent1.putExtra("rating",intent2.getStringExtra("rating"))
                intent1.putExtra("message",intent2.getStringExtra("message"))
            }
                startActivity(intent1)
            return true
        }

    }
}

