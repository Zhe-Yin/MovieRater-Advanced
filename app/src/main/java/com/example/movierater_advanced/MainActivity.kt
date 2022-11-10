package com.example.movierater_advanced



import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movierater_advanced.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var  recyclerView: RecyclerView
    private var adapter:MovieAdapter? = null
    private  var movie:Movie_2? = null
//    var movieall = arrayOf(getMovieInfo())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.apply {
            //actionbar
            val actionbar = supportActionBar
            //set actionbar title
            actionbar!!.title = "MovieRater"

            // Start Recyclerview
            initRecyclerView()

            sqLiteHelper = SQLiteHelper(this@MainActivity)

            // Retrieve all movies from db
            getMovieInfo()

            adapter?.setOnClickUpdateMenuItem {
                recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                recyclerview.adapter = adapter
                registerForContextMenu(recyclerView)


            }
//            val view2 = findViewById<RecyclerView>(R.id.recyclerview)
//            val adapter: ArrayAdapter<Unit> = ArrayAdapter<Unit>(this@MainActivity, android.R.layout.activity_list_item,movieall)
//            view2.adapter = adapter
//            // Register the ListView  for Context menu
//            // Register the ListView  for Context menu
//            registerForContextMenu()


            // Click Image -> Movie Detail
            adapter?.onClickDetailItem {
                val intent = Intent(this@MainActivity, MovieDetail::class.java)
                intent.putExtra("id",it.id.toString())
                intent.putExtra("name",it.name)
                intent.putExtra("description",it.description)
                intent.putExtra("date",it.date)
                intent.putExtra("language",it.language)
                intent.putExtra("below13",it.below13)
                intent.putExtra("violence",it.violence)
                intent.putExtra("vulgar",it.vulgar)
                startActivity(intent)
            }
        }
    }


    private fun getMovieInfo() {
        val movielist = sqLiteHelper.getAllMovie()
        Log.e("Listing", "${movielist.size}")

        adapter?.addItems(movielist)
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        adapter = MovieAdapter()
        recyclerView.adapter = adapter


    }

    // Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        R.menu.main
        return true
    }

    // Options
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.addmovie2 -> {
            addMovie()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    // Context Menu
    override fun onCreateContextMenu(menu: ContextMenu,v:View, menuInfo:ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(0, v.id, 0, "Edit")
    }


    // Context menu item select listener
    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(item.title == "Edit"){
                val intent = Intent(this@MainActivity, EditMovie::class.java)

                intent.putExtra("id",findViewById<TextView>(R.id.list_movieid).text.toString())
                intent.putExtra("name",findViewById<TextView>(R.id.list_name).text.toString())
                intent.putExtra("description",findViewById<TextView>(R.id.list_description).text.toString())
                intent.putExtra("date",findViewById<TextView>(R.id.list_date).text.toString())
                intent.putExtra("language",findViewById<TextView>(R.id.list_language).text.toString())
                intent.putExtra("below13",findViewById<TextView>(R.id.list_below13).text.toString())
                intent.putExtra("violence",findViewById<TextView>(R.id.list_violence).text.toString())
                intent.putExtra("vulgar",findViewById<TextView>(R.id.list_vulgar).text.toString())
                startActivity(intent)

        }

        return true
    }

    private fun addMovie(){
        binding.apply{
            val intent = Intent(this@MainActivity, AddMovie::class.java)
            startActivity(intent)
        }
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView),
        View.OnClickListener, OnCreateContextMenuListener {
        var name: TextView
        override fun onClick(v: View) {
            val location = name.text.toString()

            val goFlip = Intent(this@ViewHolder as Context?, RecyclerView::class.java)
            val bundle = Bundle()
            bundle.putString("name", location)
            bundle.putInt("pos", adapterPosition)
            goFlip.putExtras(bundle)
            startActivity(goFlip)
        }




        //
        override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
            menu.setHeaderTitle("Select Action")
            val edit = menu.add(Menu.NONE, 1, 1, "Edit")

            edit.setOnMenuItemClickListener(onChange)

        }

        private val onChange =
            MenuItem.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    1 -> {

                        return@OnMenuItemClickListener true
                    }

                }
                false
            }

        init {
            name = itemLayoutView.findViewById<View>(R.id.list_name) as TextView
            itemLayoutView.setOnClickListener(this@ViewHolder)
            itemLayoutView.setOnCreateContextMenuListener(this@ViewHolder)
        }
    }


}


