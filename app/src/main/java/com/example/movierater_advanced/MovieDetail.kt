package com.example.movierater_advanced

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
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
            registerForContextMenu(reviews);

            // Info retrieve from EditMovie / AddMovie after intent
            val intent = intent
            title.text = intent.getStringExtra("title")
            overview.text = intent.getStringExtra("overview")
            language.text = intent.getStringExtra("language")
            date.text = intent.getStringExtra("date")
            below13.text = intent.getStringExtra("below13")
            languageused.text = intent.getStringExtra("languageused")
            violence.text = intent.getStringExtra("violence")
            if(below13.text == "true"){
                below13.setText("No")
                if (languageused.text == "true"){
                    languageused.setText("(Violence)")
                    language.visibility = View.VISIBLE
                }else{
                    languageused.text = ""
                }
                if(violence.text == "true"){
                    violence.setText("(Vulgar)")
                    violence.visibility = View.VISIBLE
                }else{
                    violence.text = ""

                }
            }else{
                below13.setText("Yes")
            }

        }


    }

    // Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.moviedetail, menu)
        R.menu.moviedetail
        return true
    }

    // Items in Menu select listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.edit -> {
            val intent = Intent(this@MovieDetail, EditMovie::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    // Navigate to Main Page
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this@MovieDetail, MainActivity::class.java)
        startActivity(intent)
        return true
    }

    // Context Menu
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(0, v.id, 0, "Add Review")

    }

    // Context menu item select listener
    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(item.title == "Add Review"){
            val intent = Intent(this@MovieDetail, Rating::class.java)
            startActivity(intent)
        }

        return true
    }




}






