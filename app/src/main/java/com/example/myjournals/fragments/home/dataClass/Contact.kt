package com.example.myjournals.fragments.home.dataClass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name:String,
//    var color : String,
    val createDate: Date
)
