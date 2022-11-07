package com.example.movierater_advanced

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movierater_advanced.databinding.ActivityRatingBinding

class Rating : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding
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

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val intent1 = Intent(this@Rating,MovieDetail::class.java)
        val intent_2 = intent
        intent1.putExtra("id",intent_2.getStringExtra("id"))
        startActivity(intent1)
        return true
    }
}

