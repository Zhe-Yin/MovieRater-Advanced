package com.example.movierater_advanced

import kotlin.random.Random

class Movie_2 (
    var id:Int = getAutoId(),
    var name:String = "",
    var description:String = "",
    var language:String = "",
    var date:String = ""

){
    companion object{
        fun getAutoId():Int{
            var random_id:Random = Random.Default
            return random_id.nextInt(100)
        }
    }

}