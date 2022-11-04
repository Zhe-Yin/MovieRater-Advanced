package com.example.movierater_advanced

import kotlin.random.Random
import kotlin.random.nextInt

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
            return random_id.nextInt(300,500)
        }
    }

}