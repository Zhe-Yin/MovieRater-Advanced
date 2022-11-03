package com.example.movierater_advanced

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
//import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.movierater_advanced.databinding.ActivityAddMovieBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AddMovie : AppCompatActivity() {
    private lateinit var binding: ActivityAddMovieBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var  recyclerView: RecyclerView
    private var adapter:MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply{

            //actionbar
            val actionbar = supportActionBar
            //set actionbar title
            actionbar!!.title = "MovieRater"
            //set back button
            actionbar.setDisplayHomeAsUpEnabled(true)

//            below13.setOnClickListener{
//                setvisibility()
//            }

//            initalView()
            initRecyclerView()
            sqLiteHelper = SQLiteHelper(this@AddMovie)



        }

    }
    private fun getMovieInfo(){
        val movielist = sqLiteHelper.getAllMovie()
        Log.e("Listing","${movielist.size}")

        adapter?.addItems(movielist)

    }
    private fun initRecyclerView(){
        recyclerView = findViewById(R.id.recylerview)
        recyclerView.layoutManager = LinearLayoutManager(this@AddMovie)
        adapter = MovieAdapter()
        recyclerView.adapter = adapter
    }
    private fun addmovie(){
        binding.apply {
            val language_grp:RadioGroup = findViewById(R.id.group_language)
            val language_button = language_grp.checkedRadioButtonId
            val language = findViewById(language_button) as RadioButton
            val movie = Movie_2(
                name=name.text.toString(),
            description = description.text.toString(),
            language = language.toString(),
            date = date.text.toString())
            val status = sqLiteHelper.insertMovie(movie)
            if(status > -1){
                Toast.makeText(applicationContext,"Movie Added...",Toast.LENGTH_LONG).show()
                getMovieInfo()
            }else{
                Toast.makeText(applicationContext,"Movie not Added...",Toast.LENGTH_LONG).show()
            }

        }


    }
//    private fun initalView(){
//        binding.apply {
//
//        }
//    }

    // Navigate back to Main
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this@AddMovie,MainActivity::class.java)
        startActivity(intent)
        return true
    }
    // Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.addmovie, menu)
        return true
    }
    // Items in Menu select listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.clear -> {
            clearall()
            true
        }
        R.id.addmovie ->{
            validation()
            true
        }
        else -> super.onOptionsItemSelected(item)


    }

    // Validate all fields
    private fun validation():Boolean{
        var haschk = true
        binding.apply {

            if(name.text.isEmpty()){
                name.error = "Name is empty"
                haschk = false
            }
            if(description.text.isEmpty()){
                description.error = "Description is empty"
                haschk = false
            }
            if(date.text.isEmpty()){
                date.error = "Date is empty"
                haschk = false

            }else{
                try{
//
                    var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
                    var formattedDate = date.toString().format(formatter)
                }catch(e:Exception){
                    date.error = "Date format is wrong (dd-mm-yyyy)"
                    haschk = false
                }
            }
            if(below13.isChecked == true){
                if(violence.isChecked == false && languageused.isChecked == false){
                    below13.error = "Please check either Violence / Language Used or Both"
                    haschk = false
                }
            }
            if(haschk == true){
                addmovie()
            }

        }
        return haschk
    }

    // Clear all inputs
    private fun clearall(){
        binding.apply {
            name.text.clear()
            description.text.clear()
            date.text.clear()
            below13.isChecked = false
            languageused.isChecked = false
            violence.isChecked = false
//            setvisibility()

        }
    }

    // Adds movie after validation ( pass data with intent )
    private fun addmovie_2(){
        binding.apply {
            val intent = Intent(this@AddMovie,MovieDetail::class.java)
            val language_grp:RadioGroup = findViewById(R.id.group_language)
            val language_button = language_grp.checkedRadioButtonId
            val language = findViewById(language_button) as RadioButton

            intent.putExtra("title",name.text.toString())
            intent.putExtra("overview",description.text.toString())
            intent.putExtra("language",language.text.toString())
            intent.putExtra("date",date.text.toString())
            intent.putExtra("below13",below13.isChecked.toString())
            intent.putExtra("violence",violence.isChecked.toString())
            intent.putExtra("languageused",languageused.isChecked.toString())

            startActivity(intent)

        }
    }
    // Checkbox visibility
//    private  fun setvisibility() {
//        binding.apply {
//            val linear:LinearLayout = findViewById(R.id.layout_reasons)
//            if(below13.isChecked){
//                linear.visibility = View.VISIBLE
//            }else{
//                linear.visibility = View.INVISIBLE
//            }
//        }
//    }

}
