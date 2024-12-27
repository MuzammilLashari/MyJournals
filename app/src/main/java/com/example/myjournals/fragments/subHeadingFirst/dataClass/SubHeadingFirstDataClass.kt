package com.example.myjournals.fragments.subHeadingFirst.dataClass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "subHeadingFirst")
data class SubHeadingFirstDataClass(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title:String,
    var name:String,
//    var color : String,
    val createDate: Date
)
