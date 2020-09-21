package com.example.controllerapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.controllerapp.model.History
import kotlinx.android.synthetic.main.list_item.view.*

class MonitorAdapter(
    var list: MutableList<History>
): RecyclerView.Adapter<MonitorAdapter.Holder>() {

    class Holder(view: View): RecyclerView.ViewHolder(view){
        var temperature : TextView = view.tv_temp_list
        var humadity : TextView = view.tv_huma_list
        var date : TextView = view.tv_date
        var time : TextView = view.tv_time

        @SuppressLint("SetTextI18n")
        fun bindController(monitor: History){
            temperature.text = "Suhu : ${monitor.suhu} °C"
            humadity.text = "Kelembaban : ${monitor.kelembaban} %"
            date.text = monitor.date
            time.text = monitor.time

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindController(list[position])
    }
}