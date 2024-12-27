package com.example.myjournals.fragments.home.interfaces

import com.example.myjournals.fragments.home.dataClass.Contact

interface IPromptListener {

    fun onEditButtonClick(position: Int, stringList: Contact)
    fun onDeleteButtonClick(position: Int, stringList: Contact)
    fun onColorPickerButtonClick(position: Int, stringList: Contact)
    fun onTextButtonClick(position: Int, stringList: Contact)
}