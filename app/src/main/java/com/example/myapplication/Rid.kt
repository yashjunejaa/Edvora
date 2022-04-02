package com.example.myapplication

data class Rid(val id:Int, val origin_station_code: Int,
               val station_path: List<Int>,
               val destination_station_code: Int,
    val date: String,
    val map_url : String,
    val state: String,
    val city: String)
