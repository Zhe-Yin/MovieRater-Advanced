package com.example.movierater_advanced

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.movierater_advanced.Movie
import com.example.movierater_advanced.databinding.ActivityEditMovieBinding
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class EditMovie : AppCompatActivity() {
    private lateinit var binding: ActivityEditMovieBinding
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

            // Update info with Movie Entity Class
            updateinfo()

            // check visibility
            below13.setOnClickListener{
                setvisibility()
            }
        }
    }

    // Navigate to Movie Detail
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this@EditMovie,MovieDetail::class.java)
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

    // Update Movie with info from Movie class
    private fun updateinfo(){
        binding.apply {
            var m = Movie()

            name.setText(m.title)
            description.setText(m.desc)
            date.setText(m.date)
            val language_grp = findViewById(R.id.group_language) as RadioGroup
            val language_button = language_grp.checkedRadioButtonId
            if(m.language == language_button.toString())
            {
                language_grp.checkedRadioButtonId
            }
            if (m.below13 == true){
                below13.isChecked = true
            }
            if (m.language_used == true){
                languageused.isChecked == true
            }
            if(m.violence == false){
                violence.isChecked == false
            }
        }
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
                    val pattern = "yyyy-MM-dd"
                    val simpleDateFormat = SimpleDateFormat(pattern)
                    val date: String = simpleDateFormat.format(date.toString())
                    println(date)
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
                save()
            }

        }
        return haschk
    }

    // Save info & replace old info
    private fun save(){
        binding.apply {
            val intent = Intent(this@EditMovie,MovieDetail::class.java)
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

    // cancel edit
    private fun cancel(){
        binding.apply{
            save()
            val intent = Intent(this@EditMovie, MovieDetail::class.java)
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