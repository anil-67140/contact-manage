package com.deora.cms_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deora.cms_app.database.Contact
import com.deora.cms_app.databinding.ItemContactBinding
import com.deora.cms_app.listener.ListAction

class ContactAdapter(var contacts:List<Contact>, val action: ListAction): RecyclerView.Adapter<ContactAdapter.ContactVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactVH {
        val itemBinding =ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactVH(itemBinding)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactVH, position: Int) {
        holder.bind(contacts[position])
    }

    fun updateList(newContacts:List<Contact>){
        contacts = newContacts
        notifyDataSetChanged()
    }

    inner class ContactVH(var binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(contact:Contact){
            binding.txName.text = contact.name
            binding.txMobileNo.text = contact.mobileNo
            binding.txEmailAddress.text = contact.email

            binding.linContactItem.setOnClickListener {
                action.onClick(contact)
            }
        }
    }
}