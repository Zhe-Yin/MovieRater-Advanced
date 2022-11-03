package com.example.movierater_advanced

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "movie.db"
        private const val TBL_MOVIE = "tbl_movie"
        private const val ID = "id"
        private const val NAME = "name"
        private const val DESCRIPTION = "description"
        private const val LANGUAGE = "language"
        private const val DATE = "date"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTBLMovie = ("CREATE TABLE " + TBL_MOVIE + "("
                + ID +" INTEGER PRIMARY KEY," + NAME + " TEXT," +
                 DESCRIPTION + " TEXT," + LANGUAGE + " TEXT," + DATE
                + " TEXT" + ")"
                )
        db?.execSQL(createTBLMovie)
    }

    override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_MOVIE")
        onCreate(db)
    }

    fun insertMovie(std:Movie_2):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(DESCRIPTION, std.description)
        contentValues.put(LANGUAGE, std.language)
        contentValues.put(DATE, std.date)

        val success = db.insert(TBL_MOVIE, null,contentValues)
        db.close()

        return success

    }

    @SuppressLint("Range")
    fun getAllMovie() : ArrayList<Movie_2>{
        val movielist:ArrayList<Movie_2> = ArrayList()
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

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                description = cursor.getString(cursor.getColumnIndex("description"))
                language = cursor.getString(cursor.getColumnIndex("language"))
                date = cursor.getString(cursor.getColumnIndex("date"))
                val movie = Movie_2(id= id,name= name,description= description,language= language,date= date)
                movielist.add(movie)

            }while(cursor.moveToNext())
        }
        return movielist
    }
}