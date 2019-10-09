package com.shaoniiuc.theremembrance.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(

    @ColumnInfo(name = "msg")
    var taskMsg: String = "",
    var time: String = "",
    var date: String = "",
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
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)