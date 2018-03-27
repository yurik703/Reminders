package com.study.reminders

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.EditText
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_reminder.*
import android.widget.ArrayAdapter


class ReminderActivity : AppCompatActivity() {

    var listItems = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null

    lateinit var dbHelper : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        setSupportActionBar(toolbar)

        dbHelper = DatabaseHandler(this)

        listItems = getRemindersFromDb()

        adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                listItems)

        setListAdapter(adapter!!)

        fab.setOnClickListener {
            showAddReminderDialog()
        }
    }

    private fun setListAdapter(adapter: ArrayAdapter<String>) {
        val listView = findViewById<ListView>(R.id.lvReminderList)
        listView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true
    }

    private fun showAddReminderDialog(){

        val reminderEditText = EditText(this)
        val alert = AlertDialog.Builder(this).create()
        alert.setTitle("Add a new reminder")
        alert.setView(reminderEditText)
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Add", {
            dialogInterface, i ->
            val reminder = reminderEditText.text.toString()
            addReminderToListView(reminder)
            setReminderToDb(reminder)
        })
        alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", { dialogInterface: DialogInterface, i: Int ->
        })
        alert.show()
    }

    private fun addReminderToListView(name: String){
        listItems.add(name)
        adapter!!.notifyDataSetChanged()
    }

    private fun getRemindersFromDb() : ArrayList<String> {
        return dbHelper.get()
    }

    private fun setReminderToDb(name: String){
        dbHelper.set(name)
    }
}
