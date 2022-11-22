package com.example.movierater_advanced

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.movierater_advanced.MovieDetail as MovieDetail1

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "movie5.db"
        private const val TBL_MOVIE = "tbl_movie_5"
        private const val ID = "id"
        private const val NAME = "name"
        private const val DESCRIPTION = "description"
        private const val LANGUAGE = "language"
        private const val DATE = "date"
        private const val BELOW13 = "below13"
        private const val VIOLENCE = "violence"
        private const val VULGAR = "vulgar"
        private const val RATING = "rating"
        private const val MESSAGE = "message"
//        private const val MOVIE_ID = "movie_id"

    }


    override fun onCreate(db: SQLiteDatabase?) {

        val createTBLMovie = ("CREATE TABLE " + TBL_MOVIE + "("
                + ID +" INTEGER PRIMARY KEY," + NAME + " TEXT," +
                 DESCRIPTION + " TEXT," + LANGUAGE + " TEXT," + DATE
                + " TEXT," + BELOW13
                + " INTEGER," + VIOLENCE
                + " INTEGER," + VULGAR
                + " INTEGER," + RATING
                + " FLOAT," + MESSAGE
                + " TEXT" + ")" )

//        val createTBLReview = ("CREATE TABLE " + TBL_REVIEW + "("
//                + ID +" INTEGER PRIMARY KEY," + STARS +" INTEGER," + REVIEW + " TEXT," + MOVIE_ID + " INTEGER," + ")"
//                )
        db?.execSQL(createTBLMovie)

    }

    override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE " + TBL_MOVIE)

        onCreate(db)
    }

    fun insertMovie(movie:Movie_2):Long{

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, movie.id)
        contentValues.put(NAME, movie.name)
        contentValues.put(DESCRIPTION, movie.description)
        contentValues.put(LANGUAGE, movie.language)
        contentValues.put(DATE, movie.date)
        contentValues.put(BELOW13, movie.below13)
        contentValues.put(VIOLENCE, movie.violence)
        contentValues.put(VULGAR, movie.vulgar)
        contentValues.put(RATING,0)
        contentValues.put(MESSAGE,"N/A")

        println(movie.below13)

        db!!.execSQL("DROP TABLE " + TBL_MOVIE)
        val createTBLMovie = ("CREATE TABLE " + TBL_MOVIE + "("
                + ID +" INTEGER PRIMARY KEY," + NAME + " TEXT," +
                DESCRIPTION + " TEXT," + LANGUAGE + " TEXT," + DATE
                + " INTEGER," + BELOW13
                + " INTEGER," + VIOLENCE
                + " INTEGER," + VULGAR
                + " INTEGER," + RATING
                + " FLOAT," + MESSAGE
                + " TEXT" + ")" )
        db?.execSQL(createTBLMovie)

        val success = db.insert(TBL_MOVIE, null,contentValues)
        db.close()

        return success

    }

//    fun insertReview(review:RatingModel):Long{
//        val db = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(ID, review.id)
//        contentValues.put(STARS,review.stars)
//        contentValues.put(REVIEW,review.review)
//        contentValues.put(MOVIE_ID,review.movie_id)
//
//        val success = db.insert(TBL_REVIEW, null,contentValues)
//        db.close()
//
//        return success
//
//    }

    @SuppressLint("Range")
    fun getAllMovie() : ArrayList<Movie_2>{
        val movielist = ArrayList<Movie_2>()
//        val reviewlist = ArrayList<RatingModel>()
        println(movielist)
        val selectQuery = "SELECT * FROM $TBL_MOVIE"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var name:String
        var description:String
        var language:String
        var date:String
        var below13:Boolean
        var violence:Boolean
        var vulgar:Boolean




        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                description = cursor.getString(cursor.getColumnIndex("description"))
                language = cursor.getString(cursor.getColumnIndex("language"))
                date = cursor.getString(cursor.getColumnIndex("date"))
                below13 = cursor.getInt(cursor.getColumnIndex("below13")) > 0
                violence = cursor.getInt(cursor.getColumnIndex("violence"))  > 0
                vulgar = cursor.getInt(cursor.getColumnIndex("vulgar")) > 0



                val movie = Movie_2(id= id,name= name,description= description,language= language,date= date,below13=below13,violence=violence, vulgar = vulgar)

                movielist.add(movie)


            }while(cursor.moveToNext())
        }
        return movielist
    }
    @SuppressLint("Range")
    fun getAllReview() : ArrayList<RatingModel>{
        val reviewlist = ArrayList<RatingModel>()
//        val reviewlist = ArrayList<RatingModel>()
        println(reviewlist)
        val selectQuery = "SELECT * FROM $TBL_MOVIE"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
//
//        var id:Int
//        var name:String
//        var description:String
//        var language:String
//        var date:String
//        var below13:String
//        var violence:String
//        var vulgar:String
        var movieid:Int
        var rating:Float
        var message:String



        if(cursor.moveToFirst()){
            do{
                movieid = cursor.getInt(cursor.getColumnIndex("id"))
//                name = cursor.getString(cursor.getColumnIndex("name"))
//                description = cursor.getString(cursor.getColumnIndex("description"))
//                language = cursor.getString(cursor.getColumnIndex("language"))
//                date = cursor.getString(cursor.getColumnIndex("date"))
//                below13 = cursor.getString(cursor.getColumnIndex("below13"))
//                violence = cursor.getString(cursor.getColumnIndex("violence"))
//                vulgar = cursor.getString(cursor.getColumnIndex("vulgar"))
                rating = cursor.getFloat(cursor.getColumnIndex("rating"))
                message = cursor.getString(cursor.getColumnIndex("message"))

//                val movie = Movie_2(id= id,name= name,description= description,language= language,date= date,below13=below13,violence=violence, vulgar = vulgar)
                val review = RatingModel(movieid = movieid,rating=rating,message=message)
                reviewlist.add(review)


            }while(cursor.moveToNext())
        }
        return reviewlist
    }

    fun updateMovie(movie:Movie_2):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, movie.id)
        contentValues.put(NAME, movie.name)
        contentValues.put(DESCRIPTION, movie.description)
        contentValues.put(LANGUAGE, movie.language)
        contentValues.put(DATE, movie.date)
        contentValues.put(BELOW13, movie.below13)
        contentValues.put(VIOLENCE, movie.violence)
        contentValues.put(VULGAR, movie.vulgar)
//        contentValues.put(RATING,rating.rating)
//        contentValues.put(MESSAGE,rating.message)
        println(movie.below13)
        val success = db.update(TBL_MOVIE,contentValues,"id="+movie.id,null)
        db.close()

        return success

    }

    fun addReview(rating:RatingModel):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, rating.movieid)
        contentValues.put(RATING,rating.rating)
        contentValues.put(MESSAGE,rating.message)
        val success = db.update(TBL_MOVIE,contentValues,"id="+rating.movieid,null)
        db.close()

        return success

    }

    fun deleteMoviebyId(id:Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,id)
        val success = db.delete(TBL_MOVIE,"id="+id,null)
        db.close()
        return success
    }
}