package com.example.myjournals.fragments.subHeadingFirst.ui

import SubHeadingFirstAdapter
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.lifecycle.Observer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjournals.R
import com.example.myjournals.databinding.DialogAddNewPromptBinding
import com.example.myjournals.databinding.DialogUpdatePromptBinding
import com.example.myjournals.databinding.FragmentSubHeadingFirstBinding
import com.example.myjournals.fragments.home.dataClass.Contact
import com.example.myjournals.fragments.subHeadingFirst.dataClass.SubHeadingFirstDataClass
import com.example.myjournals.fragments.subHeadingFirst.interfaces.ISubHeadingListener
import com.example.myjournals.roomDB.ContactDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date


class SubHeadingFirstFragment : Fragment(), ISubHeadingListener {

    private lateinit var binding: FragmentSubHeadingFirstBinding
    private var dialogAddNewPrompt: AlertDialog? = null
    private var dialogEditPrompt: AlertDialog? = null
    private lateinit var dataBase: ContactDataBase
    private lateinit var adapter: SubHeadingFirstAdapter

    private var currentIdForUpdate: Int? = null
    private var stringList: MutableList<Contact> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSubHeadingFirstBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.addNew.setOnClickListener { dialogAddNewPrompt() }
        // Initialize the database
        dataBase = ContactDataBase.getDatabase(requireContext())

        val headingName = arguments?.getString("name")
        binding.tvTopBarHeading.text = headingName
        // Observe database changes
        if (headingName != null) {
            dataBase.contactDao().getSubHeadingFirstByTitle(headingName)
                .observe(viewLifecycleOwner, Observer { contacts ->
                    Log.d("DB_LOG", "Current Contacts in DB: $contacts")

                    // RecyclerView setup
                    val namesList = binding.recyclerView
                    namesList.layoutManager = LinearLayoutManager(requireContext())

                    val myAdapter =
                        SubHeadingFirstAdapter(this@SubHeadingFirstFragment) // Pass the actual contact list
                    binding.recyclerView.adapter = myAdapter
                    myAdapter.setData(contacts)
                })
        }
    }

    override fun onEditButtonClick(position: Int, stringList: SubHeadingFirstDataClass) {
        currentIdForUpdate = stringList.id
        dialogEditPrompt()
    }

    override fun onDeleteButtonClick(position: Int, stringList: SubHeadingFirstDataClass) {
        lifecycleScope.launch(Dispatchers.IO) {
            dataBase.contactDao().deleteSubHeadingFirst(stringList.id)
        }
    }

    override fun onColorPickerButtonClick(position: Int, stringList: SubHeadingFirstDataClass) {
        TODO("Not yet implemented")
    }

    override fun onTextButtonClick(position: Int, stringList: SubHeadingFirstDataClass) {
//        val bundle = Bundle().apply {
//            putString("name", stringList.name)
//        }
//        findNavController().navigate(R.id.subHeadingFirstFragment,bundle)
    }


    private fun dialogAddNewPrompt() {
        val bind = DialogAddNewPromptBinding.inflate(layoutInflater)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext()).setView(bind.root)
        dialogAddNewPrompt = builder.create()
        dialogAddNewPrompt?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogAddNewPrompt?.setCancelable(true)
        dialogAddNewPrompt?.setCanceledOnTouchOutside(true)
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
                    val headingName = arguments?.getString("name")
                    headingName?.let { headingName ->

                        val newContact = SubHeadingFirstDataClass(
                            id = 0,
                            title = headingName,
                            name = name,
                            createDate = Date()
                        )
                        dataBase.contactDao().insertSubHeadingFirst(newContact)

                        Log.d("DB_LOG", "Inserted NEWWWWWWWWWWW: $newContact")
                    }
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
        dialogEditPrompt?.setCanceledOnTouchOutside(true)
        dialogEditPrompt?.show()


        bind.btnInsert.setOnClickListener {
            val contactToUpdate = dataBase.contactDao().getContact()

            val name = bind.eTPromptName.text.toString()
            val phone = bind.etPromptNum.text.toString()
            val EDITtEXT = currentIdForUpdate

            if (name.isEmpty()/* && phone.isEmpty()*/ && EDITtEXT != null) {
                Toast.makeText(requireContext(), "Please enter data for update", Toast.LENGTH_SHORT)
                    .show()
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
                        Toast.makeText(
                            requireContext(),
                            "Contact with ID: $EDITtEXT updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        bind.eTPromptName.text.clear()
                        bind.etPromptNum.text.clear()
                    }
                    dialogEditPrompt?.dismiss()
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Plz insert data for update",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        bind.btnCancel.setOnClickListener {
            dialogEditPrompt?.dismiss()
        }
    }


}