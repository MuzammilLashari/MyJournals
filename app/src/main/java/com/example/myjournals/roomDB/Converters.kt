package com.example.room_db

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    @TypeConverter
    fun DateToLong(value:Date):Long{
        return value.time
    }

    @TypeConverter
    fun LongToDate(value:Long):Date{
        return Date(value)
    }
}