package com.example.weightroom_help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodResultAdapter(private val items: List<FoodProduct>) :
    RecyclerView.Adapter<FoodResultAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.foodName)
        val calories: TextView = itemView.findViewById(R.id.foodCalories)
        val macros: TextView = itemView.findViewById(R.id.foodMacros)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_result, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.product_name ?: "Unknown"
        holder.calories.text = "${item.nutriments?.energy_kcal_100g?.toInt() ?: 0} kcal per 100g"
        holder.macros.text = "Protein: ${item.nutriments?.proteins_100g?.toInt() ?: 0}g  " +
                "Carbs: ${item.nutriments?.carbohydrates_100g?.toInt() ?: 0}g  " +
                "Fat: ${item.nutriments?.fat_100g?.toInt() ?: 0}g"
    }

    override fun getItemCount() = items.size
}