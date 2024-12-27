package com.example.myjournals.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myjournals.fragments.home.dataClass.Contact
import com.example.myjournals.fragments.subHeadingFirst.dataClass.SubHeadingFirstDataClass
import com.example.room_db.Converters
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Contact::class, SubHeadingFirstDataClass::class], version = 3)
@TypeConverters(Converters::class)
abstract class ContactDataBase : RoomDatabase() {
    abstract fun contactDao(): ContactDAO

    companion object {
        @Volatile
        private var Instance: ContactDataBase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): ContactDataBase {
            if (Instance == null) {
                synchronized(this) {
                    Instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDataBase::class.java,
                        "contactDB"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return Instance!!
        }
    }
}