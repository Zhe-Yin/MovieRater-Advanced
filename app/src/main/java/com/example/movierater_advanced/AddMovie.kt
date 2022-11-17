package com.example.movierater_advanced

//import android.widget.LinearLayout


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.movierater_advanced.databinding.ActivityAddMovieBinding
import java.text.ParseException
import java.text.SimpleDateFormat


class AddMovie : AppCompatActivity() {
    private lateinit var binding: ActivityAddMovieBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var  recyclerView: RecyclerView
    private var adapter:MovieAdapter? = null
    private  var movie:Movie_2? = null

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

            // Checkbox validation
            below13.setOnClickListener{
                setvisibility()
            }

            sqLiteHelper = SQLiteHelper(this@AddMovie)



//            adapter?.setOnClickItem{
//                Toast.makeText(applicationContext,it.name,Toast.LENGTH_SHORT).show()
//
//                val list_language =findViewById<TextView>(R.id.list_language)
//                val langauge_grp = findViewById<RadioGroup>(R.id.group_language)
//                val language_button = langauge_grp.checkedRadioButtonId
//                if(language_button.toString() == list_language.text.toString()){
//                    langauge_grp.checkedRadioButtonId
//                }
//
//                name.setText(it.name)
//                description.setText(it.description)
//                date.setText(it.date)
//                movie = it
//            }

        }

    }

//    private fun deleteMovie(id:Int){
//        sqLiteHelper.deleteMoviebyId(id)
//        getMovieInfo()
//    }

    private fun addmovie(){
        binding.apply {
            val language_grp:RadioGroup = findViewById(R.id.group_language)
            val language_button = language_grp.checkedRadioButtonId
            val language_final:RadioButton = findViewById(language_button)
            val language = language_final.text.toString()
            var chk_below13 = "false"
            var chk_violence = "false"
            var chk_vulgar = "false"
            if(below13.isChecked){
                chk_below13 = "true"
                if(violence.isChecked){
                    chk_violence = "true"
                }
                if(languageused.isChecked){
                    chk_vulgar = "true"
                }
            }

            val movie = Movie_2(
                name=name.text.toString(),
            description = description.text.toString(),
            language = language,
            date = date.text.toString(),
            below13 = chk_below13,
            violence = chk_violence,
            vulgar = chk_vulgar)

            val status = sqLiteHelper.insertMovie(movie)
            if(status > -1){
                Toast.makeText(applicationContext,"Movie Added...",Toast.LENGTH_LONG).show()

                clearall()
            }else{
                Toast.makeText(applicationContext,"Movie not Added...",Toast.LENGTH_LONG).show()
            }

        }


    }

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
                    val format = SimpleDateFormat("dd-MM-yyyy")
                    format.setLenient(false);
                    format.parse(date.text.toString());

                }catch(e:ParseException){
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
            setvisibility()

        }
    }

    // Checkbox visibility
    private  fun setvisibility() {
        binding.apply {
            val linear:LinearLayout = findViewById(R.id.layout_reasons)
            if(below13.isChecked){
                linear.visibility = View.VISIBLE
            }else{
                linear.visibility = View.INVISIBLE
            }
        }
    }

}
