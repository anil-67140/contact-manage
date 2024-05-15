package com.deora.cms_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.deora.cms_app.adapter.ContactAdapter
import com.deora.cms_app.database.AppDataBase
import com.deora.cms_app.database.Contact
import com.deora.cms_app.databinding.ActivityContactListBinding
import com.deora.cms_app.listener.ListAction
import com.deora.cms_app.utils.DataHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContactListActivity : AppCompatActivity() , ListAction {
    private lateinit var binding:ActivityContactListBinding
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initializeRV()

        binding.btnManageContact.setOnClickListener {
            addContact()
        }
    }

    fun initializeRV(){
        adapter = ContactAdapter(arrayListOf(), this)
        binding.rvContactList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    fun refreshList(){
        GlobalScope.launch(Dispatchers.IO) {
            val contacts = AppDataBase.getDatabase(this@ContactListActivity).contactDao().getAllContacts()
            launch(Dispatchers.Main) {
                adapter.updateList(contacts)
            }
        }
    }

    override fun onClick(contact: Contact) {
        AlertDialog
            .Builder(this)
            .setTitle("Action required")
            .setMessage("Please choose relevant action for contact named ${contact.name}")
            .setPositiveButton("Update") { dialog,i ->
                updateContact(contact)
            }
            .setNegativeButton("Delete") { dialog,i ->
                deleteContact(contact)
            }
            .setNeutralButton("Cancel") { dialog,i ->
                dialog::dismiss
            }
            .create()
            .show()
    }

    fun addContact(){
        DataHolder.contact = null
        navigate()
    }

    fun updateContact(contact: Contact){
        DataHolder.contact = contact
        navigate()
    }

    fun navigate(){
        Intent(this,ContactManageActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun deleteContact(contact: Contact){
        GlobalScope.launch(Dispatchers.IO) {
            AppDataBase.getDatabase(this@ContactListActivity).contactDao().delete(contact)
            refreshList()
        }
    }

}