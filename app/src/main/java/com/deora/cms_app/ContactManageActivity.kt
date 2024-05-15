package com.deora.cms_app

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.deora.cms_app.database.AppDataBase
import com.deora.cms_app.database.Contact
import com.deora.cms_app.databinding.ActivityContactManageBinding
import com.deora.cms_app.utils.DataHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactManageActivity : AppCompatActivity() {
    private lateinit var binding:ActivityContactManageBinding
    private var coroutineScope=CoroutineScope(Dispatchers.Main)
    private var uContact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityContactManageBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)
        uContact = DataHolder.contact

        if(uContact!=null){
            binding.edName.setText(uContact?.name)
            binding.edMobileNo.setText(uContact?.mobileNo)
            binding.edEmail.setText(uContact?.email)
        }

        binding.btnContact.setOnClickListener {
            handleContact()
        }
    }

    fun handleContact(){
        val name = binding.edName.getUIText()
        val mobileNo = binding.edMobileNo.getUIText()
        val email = binding.edEmail.getUIText()

        if(name.isNotEmpty() && mobileNo.isNotEmpty() && email.isNotEmpty()){
            val contact = Contact(name=name, mobileNo = mobileNo, email = email)

            if(uContact!=null){
                contact.id = uContact?.id!!
            }

            coroutineScope.launch {
                val id = AppDataBase
                    .getDatabase(this@ContactManageActivity)
                    .contactDao()
                    .insert(contact)

                if(uContact!=null){
                    showToastMessage("contact information has updated with ID = $id")
                }
                else{
                    showToastMessage("contact information has added with ID = $id")
                }
                finish()
            }
        }
        else{
            showToastMessage("please fill contact information")
        }
    }

    fun EditText.getUIText() = this.text.toString().trim()

    fun showToastMessage(message:String){
        Toast.makeText(this@ContactManageActivity,message,Toast.LENGTH_SHORT).show()
    }
}