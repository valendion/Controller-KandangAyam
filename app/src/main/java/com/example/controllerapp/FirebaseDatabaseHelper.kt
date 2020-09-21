package com.example.controllerapp

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import com.example.controllerapp.model.Monitor
import com.google.firebase.database.*
import java.util.*

var data_ = ArrayList<Monitor>()

class FirebaseDatabaseHelper {
    lateinit var databaseFirebase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var monitors: ArrayList<Monitor>
    var sh = ""
    var lb = ""


    fun readMonitor(
        context: Context,
        suhu: TextView,
        lembab: TextView,
        fan: Switch,
        lamp: Switch,
        mode: ToggleButton
    ) {
        databaseFirebase = FirebaseDatabase.getInstance()
        databaseReference = this.databaseFirebase.reference.child("dht22")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {


                monitors = ArrayList()
                val data = snapshot.getValue(Monitor::class.java)
                sh = data?.suhu.toString()
                lb = data?.kelembaban.toString()

                data_ = arrayListOf(Monitor(lb.toInt(), sh.toInt()))

                suhu.text = "$sh °C"
                lembab.text = "$lb %"

                fan.setOnCheckedChangeListener { buttonView, isChecked ->
                    when (isChecked) {
                        true -> snapshot.ref.child("fan_status").setValue("ON")
                        false -> snapshot.ref.child("fan_status").setValue("OFF")
                    }
                }

                lamp.setOnCheckedChangeListener { buttonView, isChecked ->
                    when (isChecked) {
                        true -> snapshot.ref.child("lamp_status").setValue("ON")
                        false -> snapshot.ref.child("lamp_status").setValue("OFF")
                    }
                }

                mode.setOnCheckedChangeListener { buttonView, isChecked ->
                    when (isChecked) {
                        true -> {
                            mode.setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.md_white_1000
                                )
                            )
                            fan.isEnabled = true
                            lamp.isEnabled = true
                            snapshot.ref.child("mode").setValue("manual")
                            Toast.makeText(context, "Mode Manual", Toast.LENGTH_SHORT).show()
                        }
                        false -> {
                            mode.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                            fan.isEnabled = false
                            lamp.isEnabled = false
                            fan.isChecked = false
                            lamp.isChecked = false
                            snapshot.ref.child("mode").setValue("otomatis")
                            Toast.makeText(context, "Mode Otomatis", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }

        }

        )
    }


}