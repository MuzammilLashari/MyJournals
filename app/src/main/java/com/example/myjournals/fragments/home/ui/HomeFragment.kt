package com.example.myjournals.fragments.home.ui

import MyAdapter
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjournals.R
import com.example.myjournals.databinding.DialogAddNewPromptBinding
import com.example.myjournals.databinding.DialogUpdatePromptBinding
import com.example.myjournals.databinding.FragmentHomeBinding
import com.example.myjournals.fragments.home.interfaces.IPromptListener
import com.example.myjournals.fragments.home.dataClass.Contact
import com.example.myjournals.fragments.subHeadingFirst.dataClass.SubHeadingFirstDataClass
import com.example.myjournals.roomDB.ContactDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date


class HomeFragment : Fragment(), IPromptListener /*MyAdapter.DelAndUpdate*/ {

    private lateinit var binding: FragmentHomeBinding
    private var dialogAddNewPrompt: AlertDialog? = null
    private var dialogEditPrompt: AlertDialog? = null
    private lateinit var dataBase: ContactDataBase
    private lateinit var adapter: MyAdapter
    private var currentIdForUpdate: Int ?= null
    private var stringList: MutableList<Contact> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNew.setOnClickListener { dialogAddNewPrompt() }
        // Initialize the database
        dataBase = ContactDataBase.getDatabase(requireContext())

        // Observe database changes
        dataBase.contactDao().getContact().observe(viewLifecycleOwner, Observer { contacts ->
            Log.d("DB_LOG", "Current Contacts in DB: $contacts")

            // RecyclerView setup
            val namesList = binding.recyclerView
            namesList.layoutManager = LinearLayoutManager(requireContext())

            val myAdapter = MyAdapter(this@HomeFragment) // Pass the actual contact list
            binding.recyclerView.adapter = myAdapter
            myAdapter.setData(contacts)
        })
    }

    private fun dialogAddNewPrompt() {
        val bind = DialogAddNewPromptBinding.inflate(layoutInflater)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext()).setView(bind.root)
        dialogAddNewPrompt = builder.create()
        dialogAddNewPrompt?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogAddNewPrompt?.setCancelable(true)
        dialogAddNewPrompt?.setCanceledOnTouchOutside(true  )
        dialogAddNewPrompt?.show()


        bind.btnInsert.setOnClickListener {
            bind.btnInsert.setOnClickListener {
                val name = bind.eTPromptName.text.toString().trim()



                if (name.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter a name", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                lifecycleScope.launch(Dispatchers.IO) {
                    val newContact = Contact(0, name, /*phone,*/ Date())
                    dataBase.contactDao().insertContact(newContact)

                    Log.d("DB_LOG", "Inserted Contact: $newContact")

                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Contact added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        bind.eTPromptName.text.clear()
                        dialogAddNewPrompt?.dismiss()
                    }
                }
            }
        }

        bind.btnCancel.setOnClickListener {
            dialogAddNewPrompt?.dismiss()
        }
    }


    private fun dialogEditPrompt() {
        val bind = DialogUpdatePromptBinding.inflate(layoutInflater)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext()).setView(bind.root)
        dialogEditPrompt = builder.create()
        dialogEditPrompt?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogEditPrompt?.setCancelable(true)
        dialogEditPrompt?.setCanceledOnTouchOutside(true  )
        dialogEditPrompt?.show()


        bind.btnInsert.setOnClickListener {
            val contactToUpdate = dataBase.contactDao().getContact()

            val name = bind.eTPromptName.text.toString()
            val phone = bind.etPromptNum.text.toString()
            val EDITtEXT = currentIdForUpdate

            if (name.isEmpty()/* && phone.isEmpty()*/ && EDITtEXT!=null) {
                Toast.makeText(requireContext(), "Please enter data for update", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val contactToUpdated = EDITtEXT?.let { it1 ->
                    dataBase.contactDao().getContactByID(
                        it1
                    )
                }
                if (name.isNotEmpty() /*&& phone.isNotEmpty()*/ && contactToUpdated != null) {
                    contactToUpdated.name = name
//                    contactToUpdated.phone = phone

                    dataBase.contactDao().updateContact(name, EDITtEXT)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Contact with ID: $EDITtEXT updated successfully", Toast.LENGTH_SHORT).show()
                        bind.eTPromptName.text.clear()
                        bind.etPromptNum.text.clear()
                    }
                    dialogEditPrompt?.dismiss()
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Plz insert data for update", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        bind.btnCancel.setOnClickListener {
            dialogEditPrompt?.dismiss()
        }
    }

    /*private fun dialogColorPicker(){
        val bottomSheet = BottomSheetDialog(requireContext())
        val textColorBottomSheetBinding = DialogColorPickerBinding.inflate(layoutInflater)
        bottomSheet.setContentView(textColorBottomSheetBinding.root)
        bottomSheet.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bottomSheet.show()
        bottomSheet.setCancelable(false)
        bottomSheet.setCanceledOnTouchOutside(true)

        textColorBottomSheetBinding.hslColors.setColor(16777215)
        textColorBottomSheetBinding.hslColors.setColorSelectionListener(object :
            OnColorSelectionListener {
            override fun onColorSelected(color: Int) {
//                binding.imageView5.setBackgroundColor(color)
//                currentSdfColor = color
            }

            override fun onColorSelectionEnd(color: Int) {}

            override fun onColorSelectionStart(color: Int) {}

        })
        textColorBottomSheetBinding.applyColor.setOnOneClickListener(
            requireContext(),
            analytics = "wallpaper_preview_color_sheet_click"
        ) {
            batteryPreference.setValue(key = SDF_COLOR, currentSdfColor)
            bottomSheet.dismiss()
        }
        textColorBottomSheetBinding.cancel.setOnOneClickListener(
            requireContext(),
            analytics = "wallpaper_preview_color_sheet_cancel_click"
        ) {
            binding.imageView5.setBackgroundColor(previousSdfColor)
            batteryPreference.setValue(SDF_COLOR, previousSdfColor)

            bottomSheet.dismiss()
        }
    }*/

    override fun onEditButtonClick(position: Int, stringList: Contact) {
        currentIdForUpdate = stringList.id
        dialogEditPrompt()
    }


    override fun onDeleteButtonClick(position: Int, stringList: Contact) {

        lifecycleScope.launch(Dispatchers.IO) {
            dataBase.contactDao().deleteContact(stringList.id)
        }
    }

    override fun onColorPickerButtonClick(position: Int, stringList: Contact) {

    }

    override fun onTextButtonClick(position: Int, stringList: Contact) {

        val bundle = Bundle().apply {
            putString("name", stringList.name)
        }
        findNavController().navigate(R.id.subHeadingFirstFragment,bundle)
    }


    /*override fun delete(position: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val contactToDelete = dataBase.contactDao().getContactByString(position)
            Log.d("check", "position is : $contactToDelete")
            if (contactToDelete != null) {
                dataBase.contactDao().deleteContact(contactToDelete)
                withContext(Dispatchers.Main) {
                    dialogAddNewPrompt?.dismiss()
                    Toast.makeText(
                        requireContext(),
                        "Contact with ID: $position deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Contact with ID: $position not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun update(position: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val contactToUpdate = dataBase.contactDao().getContactByString(position)
            Log.d("check", "position is : $contactToUpdate")
            if (contactToUpdate != null) {
                dataBase.contactDao().updateContact(contactToUpdate)
                withContext(Dispatchers.Main) {
                    dialogAddNewPrompt?.dismiss()
                    Toast.makeText(
                        requireContext(),
                        "Contact with String: $position updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Contact with String: $position not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }*/


}