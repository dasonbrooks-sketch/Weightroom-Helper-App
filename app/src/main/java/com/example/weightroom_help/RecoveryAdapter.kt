package com.example.weightroom_help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecoveryAdapter(private val tips: List<RecoveryTip>) :
    RecyclerView.Adapter<RecoveryAdapter.RecoveryViewHolder>() {

    class RecoveryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val type: TextView = itemView.findViewById(R.id.recoveryType)
        val name: TextView = itemView.findViewById(R.id.recoveryName)
        val description: TextView = itemView.findViewById(R.id.recoveryDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecoveryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recovery, parent, false)
        return RecoveryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecoveryViewHolder, position: Int) {
        val tip = tips[position]
        holder.type.text = tip.type
        holder.name.text = tip.name
        holder.description.text = tip.description
    }

    override fun getItemCount() = tips.size
}