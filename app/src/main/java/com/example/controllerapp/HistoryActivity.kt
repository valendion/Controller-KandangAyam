package com.example.controllerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controllerapp.model.History
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    lateinit var list: MutableList<History>
    var valueTemp: Int = 0
    var valueHuma: Int = 0
    var key = ""
    var date = ""
    var time = ""
    var database: DatabaseReference? = null
    var database_read: DatabaseReference? = null
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    lateinit var value: FirebaseDatabaseHelper
    var tgl: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        valueTemp = data_[0].suhu
        valueHuma = data_[0].kelembaban

        handler = Handler(Looper.getMainLooper())

        list = mutableListOf()
        database = FirebaseDatabase.getInstance().getReference("History")
        database_read = FirebaseDatabase.getInstance().getReference("History")

        rv_history.layoutManager = LinearLayoutManager(this)

        database_read!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("_database", " -> ${error.details} ${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    var history: History = dataSnapshot.getValue(History::class.java)!!
                    list.add(history)
                }
                rv_history.setHasFixedSize(true)


                val adapter = MonitorAdapter(list = list)
                adapter.notifyDataSetChanged()

                rv_history.adapter = adapter
            }

        })

        fab_insert.setOnClickListener {
            database!!
                .removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Data berhasil di hapus", Toast.LENGTH_SHORT).show()
                }
        }

    }

    fun summit(history: History, key: String) {
        database!!
            .child(key)
            .setValue(history)

    }

    val repeat = object : Runnable {
        override fun run() {
            tgl = Date()
            date = tgl!!.getDate()
            time = tgl!!.getTime()
            key = database!!.push().key.toString()

            summit(
                History(
                    valueHuma,
                    valueTemp,
                    key,
                    time,
                    date
                ), key
            )

            handler.postDelayed(this, 30000)
        }
    }

    override fun onResume() {
        super.onResume()
        handler.post(repeat)
    }

}
