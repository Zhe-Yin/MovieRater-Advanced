package a201457F.assignment_1.movierater_advanced

//import android.R

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.movierater_advanced.R

class MovieAdapter(
    context: Context,
    private val resource: Int = R.layout.movielist_item,
    objects: MutableList<Movie_2>
) :
    ArrayAdapter<Movie_2>(context, resource, objects) {
    private val mContext: Context

    init {
        mContext = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val vi: LayoutInflater = LayoutInflater.from(mContext)
            view = vi.inflate(resource, null)
        }

        val movie: Movie_2? = getItem(position)

        if (movie != null) {
            val tt1: TextView = view!!.findViewById(R.id.list_name)
            tt1.text = movie.name
        }
        return view!!
    }
}
