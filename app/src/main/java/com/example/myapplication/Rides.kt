package com.example.myapplication

import java.util.*

class Rides {
    var rideId = 0
    var city: String? = null
    var state: String? = null
    var originalStation = 0
    var destinationStation = 0
    var stationPath: IntArray
    var date: String? = null
    var url: String? = null
    var distance: Int = 0

    constructor(
        rideId: Int,
        city: String?,
        state: String?,
        originalStation: Int,
        destinationStation: Int,
        stationPath: IntArray,
        date: String,
        url: String,
        distance: Int
    ) {
        this.rideId = rideId
        this.city = city
        this.state = state
        this.originalStation = originalStation
        this.destinationStation = destinationStation
        this.stationPath = stationPath
        this.date = date
        this.url = url
        this.distance = distance
    }

    override fun toString(): String {
        return "Rides(rideId=$rideId, city=$city, state=$state, originalStation=$originalStation, destinationStation=$destinationStation, stationPath=${stationPath.contentToString()}, date=$date, url=$url, distance=$distance)"
    }


}