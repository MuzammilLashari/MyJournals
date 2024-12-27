package com.example.myjournals.fragments.subHeadingFirst.interfaces

import com.example.myjournals.fragments.home.dataClass.Contact
import com.example.myjournals.fragments.subHeadingFirst.dataClass.SubHeadingFirstDataClass

interface ISubHeadingListener {

    fun onEditButtonClick(position: Int, stringList: SubHeadingFirstDataClass)
    fun onDeleteButtonClick(position: Int, stringList: SubHeadingFirstDataClass)
    fun onColorPickerButtonClick(position: Int, stringList: SubHeadingFirstDataClass)
    fun onTextButtonClick(position: Int, stringList: SubHeadingFirstDataClass)

}