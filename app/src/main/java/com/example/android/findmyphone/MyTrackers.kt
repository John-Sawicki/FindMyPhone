package com.example.android.findmyphone

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.view.*
import android.widget.BaseAdapter
import android.widget.Toast
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
            R.id.finish_activity->{
             finish()
            }R.id.add_contact->{

            }else->{
        }
        }
        return true
    }
    val contact_code = 123
    fun checkPermission(){
        if(Build.VERSION.SDK_INT>=23){  //if less than 23 all permission requested at install
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)!=
                    PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS),contact_code)
                return
            }
        }
        pickContact()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            contact_code->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    pickContact()
                }else{
                    Toast.makeText(this, "cant access contact", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private var pickContactCode = 234
    fun pickContact(){
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)//different app
        startActivityForResult(intent,pickContactCode )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            pickContactCode -> {    //234
                if (releaseInstance()) {
                    val contactData = data!!.data
                    val cr = contentResolver.query(contactData,
                        null, null, null, null)
                    //ContactsContract is specific to Android and not a custom class
                    if(cr.moveToFirst()){
                        val id = cr.getString(cr.getColumnIndex(ContactsContract.Contacts._ID))
                        val hasPhoneNum = cr.getString(cr.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        if ( hasPhoneNum.equals("1")){
                            val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id,
                                null, null)
                            phones.moveToFirst()
                            var phone = phones.getString(cr.getColumnIndex("data1"))
                            val name = phones.getString(cr.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                            listOfContacts.add(UserContact(name, phone))
                        }
                    }
                }
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }


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
            val userContact = listOfContact[postion]
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
