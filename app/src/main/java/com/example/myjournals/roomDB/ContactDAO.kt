package com.example.myjournals.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myjournals.fragments.home.dataClass.Contact
import com.example.myjournals.fragments.subHeadingFirst.dataClass.SubHeadingFirstDataClass

@Dao
interface ContactDAO {


    /******* Database For Main Home Screen **********/
    @Insert
    suspend fun insertContact(contact: Contact)

    @Query("UPDATE contact SET name = :name WHERE id = :id")
    suspend fun updateContact(name: String, id: Int)

    @Query("DELETE from contact WHERE id = :id")
    suspend fun deleteContact(id: Int)

    @Query("Select * from contact")
    fun getContact(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact WHERE id = :id")
    suspend fun getContactByID(id: Int): Contact?



    /******* Database For Main Home Screen **********/
    @Insert
    suspend fun insertSubHeadingFirst(subHeadingFirstDataClass: SubHeadingFirstDataClass)

    @Query("UPDATE subHeadingFirst SET name = :name WHERE id = :id")
    suspend fun updateSubHeadingFirst(name: String, id: Int)

    @Query("DELETE from subHeadingFirst WHERE id = :id")
    suspend fun deleteSubHeadingFirst(id: Int)

    @Query("Select * from subHeadingFirst")
    fun getSubHeadingFirst(): LiveData<List<SubHeadingFirstDataClass>>


    //NEW
    @Query("SELECT * FROM subHeadingFirst WHERE title = :title")
    fun getSubHeadingFirstByTitle(title: String): LiveData<List<SubHeadingFirstDataClass>>

    @Query("SELECT * FROM subHeadingFirst WHERE id = :id")
    suspend fun getSubHeadingFirstByID(id: Int): SubHeadingFirstDataClass?
}