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
            val intent = intent

            title.text = intent.getStringExtra("name")
            overview.text = intent.getStringExtra("description")
            language.text = intent.getStringExtra("language")
            date.text = intent.getStringExtra("date")
            below13.text = intent.getStringExtra("below13")
            languageused.text = intent.getStringExtra("vulgar")
            violence.text = intent.getStringExtra("violence")
//            rating.text = intent.getStringExtra("rating")
//            message.text = intent.getStringExtra("message")
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
                val star_layout = findViewById<LinearLayout>(R.id.rating_star)
                star_layout.visibility = View.VISIBLE

                rating.text = intent.getStringExtra("rating")
                message.text = intent.getStringExtra("message")
                stars.rating = intent.getStringExtra("rating")!!.toFloat()
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
        val intent = intent
        if(item.title == "Add Review"){

            binding.apply {
                val intent1 = Intent(this@MovieDetail, Rating::class.java)
//                val movie = adapter!!.getItem(info.position)!!


                intent1.putExtra("id",intent.getStringExtra("id"))
                intent1.putExtra("name",title.text.toString())
                intent1.putExtra("description",overview.text.toString())
                intent1.putExtra("date",date.text.toString())
                intent1.putExtra("language",language.text.toString())
                intent1.putExtra("below13",below13.text.toString())
                intent1.putExtra("violence",violence.text.toString())
                intent1.putExtra("vulgar",languageused.text.toString())
                intent1.putExtra("rating",intent.getStringExtra("rating"))
                intent1.putExtra("message",intent.getStringExtra("message"))
                startActivity(intent1)
            }

        }else if(item.title == "Edit Review"){

            binding.apply {
                val intent1 = Intent(this@MovieDetail, Rating::class.java)
//                val movie = adapter!!.getItem(info.position)!!



                intent1.putExtra("id",intent.getStringExtra("id"))
                intent1.putExtra("name",title.text.toString())
                intent1.putExtra("description",overview.text.toString())
                intent1.putExtra("date",date.text.toString())
                intent1.putExtra("language",language.text.toString())
                intent1.putExtra("below13",below13.text.toString())
                intent1.putExtra("violence",violence.text.toString())
                intent1.putExtra("vulgar",languageused.text.toString())
                intent1.putExtra("rating",intent.getStringExtra("rating"))
                intent1.putExtra("message",intent.getStringExtra("message"))
                startActivity(intent1)
            }
        }

        return true
    }




}






