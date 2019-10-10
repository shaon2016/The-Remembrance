package com.shaoniiuc.theremembrance.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @ColumnInfo(name = "msg")
    var taskMsg: String = "",
    var time: Long = 0,
    var date: Long = 0,
    @ColumnInfo(name = "place_name")
    var placeName: String = "",
    @ColumnInfo(name = "place_address")
    var placeAddress: String = "",
    @ColumnInfo(name = "place_mobile")
    var placeMobile: String = "",
    @ColumnInfo(name = "place_rating")
    var placeRating: String = "",
    @ColumnInfo(name = "place_web")
    var placeWeb: String = "",
    var lat : Double = 0.0,
    var lon : Double = 0.0,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
