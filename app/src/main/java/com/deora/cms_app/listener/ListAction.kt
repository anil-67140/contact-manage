package com.deora.cms_app.listener

import com.deora.cms_app.database.Contact


interface ListAction {
    fun onClick(contact: Contact)
}