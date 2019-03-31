package com.example.android.findmyphone

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_my_trackers.*

import kotlinx.android.synthetic.main.contact_ticket.view.*

class MyTrackers : AppCompatActivity() {
    var listOfContacts = ArrayList<UserContact>()
    var adapter:ContactAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_trackers)
        dummpyData()    //add values to the array
        adapter = ContactAdapter(listOfContacts, this)
        lv_my_tracker.adapter = adapter
    }
    fun dummpyData(){
        listOfContacts.add(UserContact("bob", "713"))
        listOfContacts.add(UserContact("jimbo", "504"))
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.tracker_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.addContent->{
             finish()
            }R.id.finish->{

        }else->{
        }
        }
        return true
    }
    class ContactAdapter:BaseAdapter{
        var listOfContact = ArrayList<UserContact>()
        var context:Context? = null
        constructor(listOfContact:ArrayList<UserContact>, context: Context){
            this.listOfContact = listOfContact
            this.context = context
        }
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun getView(postion: Int, p1: View?, p2: ViewGroup?): View {
            var userContact = listOfContact[postion]
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val contactTickerView = inflater.inflate(R.layout.contact_ticket, null)
            contactTickerView.tv_name.text = userContact.name
            contactTickerView.tv_phone_number.text =userContact.number
            return contactTickerView
        }
        override fun getItem(p0: Int): Any {
          return listOfContact[0]
        }
        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }
        override fun getCount(): Int {
           return listOfContact.size
        }
    }
}
