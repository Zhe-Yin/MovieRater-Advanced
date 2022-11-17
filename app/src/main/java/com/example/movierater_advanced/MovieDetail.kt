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
import androidx.appcompat.app.AppCompatActivity
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
            registerForContextMenu(reviews);

            // Info retrieve from EditMovie / AddMovie after intent
            val intent = intent

            title.text = intent.getStringExtra("name")
            overview.text = intent.getStringExtra("description")
            language.text = intent.getStringExtra("language")
            date.text = intent.getStringExtra("date")
            below13.text = intent.getStringExtra("below13")
            languageused.text = intent.getStringExtra("vulgar")
            violence.text = intent.getStringExtra("violence")
//            rating.text = intent.getStringExtra("rating")
            message.text = intent.getStringExtra("message")
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
            if(intent.getStringExtra("message") != "N/A"){

                reviews.text = intent.getStringExtra("rating") + "Stars"+ "\n" + intent.getStringExtra("message")
            }

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
        val intent = Intent(this@MovieDetail, MainActivity::class.java)
        startActivity(intent)
        return true
    }

    // Context Menu
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        binding.apply {
            val intent = intent
            if(intent.getStringExtra("message") == "N/A"){
                menu.add(0, v.id, 0, "Add Review")
            }else{
                menu.add(0, v.id, 0, "Edit Review")
            }

        }


    }

    // Context menu item select listener
    override fun onContextItemSelected(item: MenuItem): Boolean {
//        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        if(item.title == "Add Review"){

            binding.apply {
                val intent1 = Intent(this@MovieDetail, Rating::class.java)
//                val movie = adapter!!.getItem(info.position)!!
                val intent = intent
                intent1.putExtra("id",intent.getStringExtra("id"))
                intent1.putExtra("name",title.toString())
                intent1.putExtra("description",overview.toString())
                intent1.putExtra("date",date.toString())
                intent1.putExtra("language",language.toString())
                intent1.putExtra("below13",below13.toString())
                intent1.putExtra("violence",violence.toString())
                intent1.putExtra("vulgar",languageused.toString())
                startActivity(intent1)
            }

        }else if(item.title == "Edit Review"){

            binding.apply {
                val intent1 = Intent(this@MovieDetail, Rating::class.java)
//                val movie = adapter!!.getItem(info.position)!!
                val intent = intent

                intent1.putExtra("id",intent.getStringExtra("id"))
                intent1.putExtra("name",title.toString())
                intent1.putExtra("description",overview.toString())
                intent1.putExtra("date",date.toString())
                intent1.putExtra("language",language.toString())
                intent1.putExtra("below13",below13.toString())
                intent1.putExtra("violence",violence.toString())
                intent1.putExtra("vulgar",languageused.toString())
                intent1.putExtra("rating",intent.getStringExtra("rating"))
                intent1.putExtra("message",intent.getStringExtra("message"))
                startActivity(intent1)
            }
        }

        return true
    }




}






