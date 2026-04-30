package com.example.weightroom_help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodResultAdapter(private val items: List<UsdaFood>) :
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

        holder.name.text = item.description ?: "Unknown"

        val nutrients = item.foodNutrients

        val calories = nutrients?.firstOrNull {
            it.nutrientNumber == "208" || it.nutrientName?.contains("Energy", ignoreCase = true) == true
        }?.value?.toInt() ?: 0

        val protein = nutrients?.firstOrNull {
            it.nutrientNumber == "203" || it.nutrientName?.contains("Protein", ignoreCase = true) == true
        }?.value?.toInt() ?: 0

        val carbs = nutrients?.firstOrNull {
            it.nutrientNumber == "205" || it.nutrientName?.contains("Carbohydrate", ignoreCase = true) == true
        }?.value?.toInt() ?: 0

        val fat = nutrients?.firstOrNull {
            it.nutrientNumber == "204" || it.nutrientName?.contains("Total lipid", ignoreCase = true) == true
        }?.value?.toInt() ?: 0

        holder.calories.text = "$calories kcal per 100g"
        holder.macros.text = "Protein: ${protein}g   Carbs: ${carbs}g   Fat: ${fat}g"
    }

    override fun getItemCount() = items.size
}