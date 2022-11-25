package com.example.movierater_advanced

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.movierater_advanced.databinding.ActivityEditMovieBinding
import java.text.ParseException
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EditMovie : AppCompatActivity() {
    private lateinit var binding: ActivityEditMovieBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var  recyclerView: RecyclerView
    private var adapter:MovieAdapter? = null
    private  var movie:Movie_2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply{
            //actionbar
            val actionbar = supportActionBar
            //set actionbar title
            actionbar!!.title = "MovieRater"
            //set back button
            actionbar.setDisplayHomeAsUpEnabled(true)

            sqLiteHelper = SQLiteHelper(this@EditMovie)



            // check visibility
            below13.setOnClickListener{
                setvisibility()
            }
            insertInfo(
                intent.getParcelableExtra("Movie")!!
            )

        }
    }

    private fun updateMovie(movie:Movie_2){
        binding.apply{

            val language_grp:RadioGroup = findViewById(R.id.group_language)
            val language_button = language_grp.checkedRadioButtonId
            val language_final:RadioButton = findViewById(language_button)
            val language = language_final.text.toString()

            var chk_below13 = false
            var chk_violence = false
            var chk_vulgar = false
            if(below13.isChecked == true){
                println("Hellow")
                chk_below13 = true
                println(chk_below13)
                if(violence.isChecked == true){
                    chk_violence = true
                    println("check violence")
                }
                if(languageused.isChecked == true){
                    chk_vulgar = true
                    println("check lanuage")
                }
            }




            val movie = Movie_2(
                id=movie.id,
                name = name.text.toString(),
                description = description.text.toString(),
                language = language,
                date = date.text.toString(),
                below13 = chk_below13,
                vulgar = chk_vulgar,
                violence = chk_violence)




            val status = sqLiteHelper.updateMovie(movie)
            if(status > -1){
                val intent = Intent(this@EditMovie, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext,"Update success",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext,"Update failed",Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun insertInfo(movie:Movie_2){
        binding.apply {
//            val intent = intent

            val langauge_grp = findViewById<RadioGroup>(R.id.group_language)
            val language_button = langauge_grp.checkedRadioButtonId
            println(language_button)
            if(language_button.toString() == movie.language){
                println(language_button)
            }

            name.setText(movie.name)
            description.setText(movie.description)
            date.setText(movie.date)
            println(movie.below13.toString())

            if(movie.below13.toString() == "true"){
                below13.isChecked = true
                if(movie.violence.toString() == "true"){
                    violence.isChecked = true
                    println("check violence")
                }
                if(movie.vulgar.toString() == "true"){
                    languageused.isChecked = true
                    println("check lanuage")
                }
            }else{
                below13.isChecked = false
            }


        }
    }


    // Navigate to Movie Detail
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this@EditMovie,MainActivity::class.java)

        startActivity(intent)
        return true
    }

    // Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.editmovie, menu)
        R.menu.editmovie
        return true
    }

    // Items in Menu select listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.save -> {
            validation()
            true
        }
        R.id.cancel -> {
            cancel()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }



    // Validation
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
                    val format = SimpleDateFormat("dd-MM-yyyy")
                    format.setLenient(false);
                    format.parse(date.text.toString());
                }catch(e:ParseException){
                    date.error = "Date format is wrong (dd-mm-yyyy)"
                    haschk = false
                }
            }
            if(below13.isChecked){
                if(violence.isChecked == false && languageused.isChecked == false) {
                    below13.error = "Please check either Violence / Language Used or Both"
                    haschk = false
                }
            }
            if (groupLanguage.checkedRadioButtonId == -1){
                tamil.setError("Please select a language")
                haschk = false
            }
            if(haschk == true){
                updateMovie(intent.getParcelableExtra("Movie")!!)
            }

        }
        return haschk
    }


    // cancel edit
    private fun cancel(){
        binding.apply{
            val intent = Intent(this@EditMovie, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Checkbox visibility
    private  fun setvisibility() {
        binding.apply {
            val linear: LinearLayout = findViewById(R.id.layout_reasons)
            if(below13.isChecked){
                linear.visibility = View.VISIBLE
            }else{
                linear.visibility = View.INVISIBLE
            }
        }
     }


}

