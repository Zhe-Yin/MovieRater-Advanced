package com.example.movierater_advanced

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.movierater_advanced.databinding.ActivityRatingBinding

class Rating : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private var adapter:RatingAdapter? = null
    private  var review:RatingModel? = null

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

//            btnSubmit.setOnClickListener{
//
//                addreview()
//            }


        }
    }

//    private fun addreview(){
//        val intent1 = intent
//        var intent2 = intent1.getStringExtra("id")
//        println(intent2)
//        val review = RatingModel(
//            stars = 1,
//            review = "Good",
//            movie_id = intent2!!.toInt()
//
//           )
//        val status = sqLiteHelper.insertReview(review)
//        if(status > -1){
//            Toast.makeText(applicationContext,"Movie Added...", Toast.LENGTH_LONG).show()
//        }else{
//            Toast.makeText(applicationContext,"Movie not Added...", Toast.LENGTH_LONG).show()
//        }
//    }

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

