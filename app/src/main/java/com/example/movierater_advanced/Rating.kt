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
                btnSubmit.text = "Save"
            }


            btnSubmit.setOnClickListener{

                addreview()
            }

        }
    }

    private fun addreview(){
        val intent = intent

        val movie = Movie_2(
            id = intent.getStringExtra("id")!!.toInt(),
//            name = intent.getStringExtra("name").toString(),
//            description = intent.getStringExtra("description").toString(),
//            date = intent.getStringExtra("date").toString(),
//            language = intent.getStringExtra("language").toString(),
//            below13 = intent.getStringExtra("below13").toString(),
//            violence = intent.getStringExtra("violence").toString(),
//            vulgar = intent.getStringExtra("vulgar").toString()
        )
            val rating = findViewById<RatingBar>(R.id.stars)
            val rate = rating.getRating().toString()
            val message = findViewById<EditText>(R.id.message)

        val status = sqLiteHelper.addReview(movie,rate.toFloat(),message.toString())
        if(status > -1){
            val intent1 = Intent(this@Rating,MovieDetail::class.java)

            val intent2 = intent
            intent1.putExtra("id",intent2.getStringExtra("id").toString())
            intent1.putExtra("name",intent2.getStringExtra("name").toString())
            intent1.putExtra("description",intent2.getStringExtra("description").toString())
            intent1.putExtra("date",intent2.getStringExtra("date").toString())
            intent1.putExtra("language",intent2.getStringExtra("language").toString())
            intent1.putExtra("below13",intent2.getStringExtra("below13").toString())
            intent1.putExtra("violence",intent2.getStringExtra("violence").toString())
            intent1.putExtra("vulgar",intent2.getStringExtra("vulgar").toString())
            intent1.putExtra("rating",rate)
            intent1.putExtra("message",message.toString())
            startActivity(intent1)
            Toast.makeText(applicationContext,"Review success" + message.toString(),Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(applicationContext,"Review failed",Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
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
        startActivity(intent1)
        return true
    }
}

