package com.example.movierater_advanced

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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movierater_advanced.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter:ArrayAdapter<Movie_2>

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

//            // Start Recyclerview
//            initRecyclerView()

            sqLiteHelper = SQLiteHelper(this@MainActivity)

//            getMovieInfo()
            loadMovies(sqLiteHelper.getAllMovie())
//            adapter?.setOnClickUpdateMenuItem {
//
//            }



//            val view2 = findViewById<RecyclerView>(R.id.recyclerview)
//            val adapter: ArrayAdapter<Unit> = ArrayAdapter<Unit>(this@MainActivity, android.R.layout.activity_list_item,movieall)
//            view2.adapter = adapter
//            // Register the ListView  for Context menu
//            // Register the ListView  for Context menu


            // Click Image -> Movie Detail
//            adapter?.onClickDetailItem {
//                val intent = Intent(this@MainActivity, MovieDetail::class.java)
//                intent.putExtra("id",it.id.toString())
//                intent.putExtra("name",it.name)
//                intent.putExtra("description",it.description)
//                intent.putExtra("date",it.date)
//                intent.putExtra("language",it.language)
//                intent.putExtra("below13",it.below13)
//                intent.putExtra("violence",it.violence)
//                intent.putExtra("vulgar",it.vulgar)
//                startActivity(intent)
//            }
        }
    }


//    private fun getMovieInfo() {
//        val movielist = sqLiteHelper.getAllMovie()
//        Log.e("Listing", "${movielist.size}")
//
//        adapter?.addItems(movielist)
//    }
    private fun loadMovies(movies: ArrayList<Movie_2>) {
        adapter = MovieAdapter(this, R.layout.movielist_item, movies)
        val listView: ListView = findViewById(R.id.recyclerview)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, MovieDetail::class.java)
            val movie = movies[position]
            intent.putExtra("id", movie.id)
            intent.putExtra("name",movie.name)
            intent.putExtra("description",movie.description)
            intent.putExtra("date",movie.date)
            intent.putExtra("language",movie.language)
            intent.putExtra("below13",movie.below13)
            intent.putExtra("violence",movie.violence)
            intent.putExtra("vulgar",movie.vulgar)
            startActivity(intent)
        }

        registerForContextMenu(listView)
    }
//    private fun initRecyclerView() {
//
//        binding.recyclerview.adapter = adapter
//
//        recyclerView = findViewById(R.id.recyclerview)
//        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
//        adapter = MovieAdapter()
//        recyclerView.adapter = adapter
//
//        registerForContextMenu(recyclerView)
//
//    }

//    private fun performOptionsMenuClick(position: Int) {
//        // create object of PopupMenu and pass context and view where we want
//        // to show the popup menu
//        val popupMenu = PopupMenu(this , binding.recyclerview[position].findViewById(R.id.list_name))
//        // add the menu
//        popupMenu.inflate(R.menu.addmovie)
//        // implement on menu item click Listener
//        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
//            override fun onMenuItemClick(item: MenuItem?): Boolean {
//                when(item?.itemId){
//
//                    // in the same way you can implement others
//                    R.id.addmovie -> {
//                        // define
//                        Toast.makeText(this@MainActivity , "Item 2 clicked" , Toast.LENGTH_SHORT).show()
//                        return true
//                    }
//
//                }
//                return false
//            }
//        })
//        popupMenu.show()
//    }

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
//    // Context Menu
//    override fun onCreateContextMenu(menu: ContextMenu,v:View, menuInfo:ContextMenuInfo?) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//        menu.add(0, v.id, 0, "Edit")
//    }


//    // Context menu item select listener
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        if(item.title == "Edit"){
//                val intent = Intent(this@MainActivity, EditMovie::class.java)
//
//
//            intent.putExtra("id",findViewById<TextView>(R.id.list_movieid).text.toString())
//            intent.putExtra("name",findViewById<TextView>(R.id.list_name).text.toString())
//            intent.putExtra("description",findViewById<TextView>(R.id.list_description).text.toString())
//            intent.putExtra("date",findViewById<TextView>(R.id.list_date).text.toString())
//            intent.putExtra("language",findViewById<TextView>(R.id.list_language).text.toString())
//            intent.putExtra("below13",findViewById<TextView>(R.id.list_below13).text.toString())
//            intent.putExtra("violence",findViewById<TextView>(R.id.list_violence).text.toString())
//            intent.putExtra("vulgar",findViewById<TextView>(R.id.list_vulgar).text.toString())
//
//                startActivity(intent)
//
//        }
//
//        return true
//    }


    private fun addMovie(){
        binding.apply{
            val intent = Intent(this@MainActivity, AddMovie::class.java)
            startActivity(intent)
        }
    }

//    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView),
//        View.OnClickListener, OnCreateContextMenuListener {
//        var name: TextView
//        override fun onClick(v: View) {
//            val location = name.text.toString()
//
//            val goFlip = Intent(this@ViewHolder as Context?, RecyclerView::class.java)
//            val bundle = Bundle()
//            bundle.putString("name", location)
//            bundle.putInt("pos", adapterPosition)
//            goFlip.putExtras(bundle)
//            startActivity(goFlip)
//        }
//
//
//
//
//        //
//        override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
//            menu.setHeaderTitle("Select Action")
//            val edit = menu.add(Menu.NONE, 1, 1, "Edit")
//
//            edit.setOnMenuItemClickListener(onChange)
//
//        }
//
//        private val onChange =
//            MenuItem.OnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    1 -> {
//                        println("HELLO")
//                        return@OnMenuItemClickListener true
//                    }
//
//                }
//                false
//            }
//
//        init {
//            name = itemLayoutView.findViewById<View>(R.id.list_name) as TextView
//            itemLayoutView.setOnClickListener(this@ViewHolder)
//            itemLayoutView.setOnCreateContextMenuListener(this@ViewHolder)
//        }
//    }
override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
) {
    menuInflater.inflate(R.menu.moviedetail, menu)
    super.onCreateContextMenu(menu, v, menuInfo)
}

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.title) {
            "Edit" -> {
                val movie = adapter.getItem(info.position)!!
                val intent = Intent(this, EditMovie::class.java)
                intent.putExtra("id", movie.id.toString())
                intent.putExtra("name",movie.name)
                intent.putExtra("description",movie.description)
                intent.putExtra("date",movie.date)
                intent.putExtra("language",movie.language)
                intent.putExtra("below13",movie.below13)
                intent.putExtra("violence",movie.violence)
                intent.putExtra("vulgar",movie.vulgar)
                startActivity(intent)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }



}


