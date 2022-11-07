package com.example.movierater_advanced

import kotlin.random.Random

class RatingModel(

    var id:Int = getAutoId(),
    var stars:Int,
    var review:String = "",
    var movie_id:Int
) {
    companion object{
        fun getAutoId():Int{
            var random_id: Random = Random.Default
            return random_id.nextInt(300,500)
        }
    }
}