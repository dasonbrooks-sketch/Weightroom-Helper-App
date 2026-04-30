package com.example.weightroom_help

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
class CaloriesFragment : Fragment() {

    private var isMale = true
    private var tableVisible = false

    data class ActivityLevel(
        val name: String,
        val multiplier: Double,
        val description: String
    )

    private val activityLevels = listOf(
        ActivityLevel(
            "Sedentary", 1.2,
            "Desk job, little to no exercise. Office worker who drives everywhere and doesn't work out."
        ),
        ActivityLevel(
            "Lightly Active", 1.37,
            "Light walks or casual gym visits 1–3 days a week. Think someone who goes for a 20–30 min walk a few times a week."
        ),
        ActivityLevel(
            "Moderately Active", 1.55,
            "Working out with intention 3–5 days a week. Amateur recreational athlete who lifts or runs consistently."
        ),
        ActivityLevel(
            "Very Active", 1.725,
            "Hard training most days. Preparing for a 5K, playing recreational league sports, or intense gym sessions 6–7 days a week."
        ),
        ActivityLevel(
            "Extra Active", 1.9,
            "Physical job plus training, or elite amateur athlete. Think construction worker who also trains, or someone doing two-a-days."
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_calories, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val foodSearchInput = view.findViewById<EditText>(R.id.foodSearchInput)
        val searchFoodButton = view.findViewById<Button>(R.id.searchFoodButton)
        val foodResultsView = view.findViewById<RecyclerView>(R.id.foodRecyclerView)
        val foodProgress = view.findViewById<ProgressBar>(R.id.foodProgressBar)

        foodResultsView.layoutManager = LinearLayoutManager(requireContext())

        searchFoodButton.setOnClickListener {
            val query = foodSearchInput.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(requireContext(), "Enter a food to search", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            foodProgress.visibility = View.VISIBLE
            foodResultsView.visibility = View.GONE

            lifecycleScope.launch {
                try {
                    val response = FoodApiClient.api.searchFood(query)
                    val products = response.products?.filter {
                        it.product_name != null && it.nutriments?.energy_kcal_100g != null
                    } ?: emptyList()

                    foodProgress.visibility = View.GONE
                    foodResultsView.visibility = View.VISIBLE
                    foodResultsView.adapter = FoodResultAdapter(products)

                    if (products.isEmpty()) {
                        Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    foodProgress.visibility = View.GONE
                    Toast.makeText(requireContext(), "Search failed. Check connection.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)

        val maleButton = view.findViewById<Button>(R.id.maleButton)
        val femaleButton = view.findViewById<Button>(R.id.femaleButton)
        val activitySpinner = view.findViewById<Spinner>(R.id.activitySpinner)
        val weightInput = view.findViewById<EditText>(R.id.weightInput)
        val feetInput = view.findViewById<EditText>(R.id.feetInput)
        val inchesInput = view.findViewById<EditText>(R.id.inchesInput)
        val ageInput = view.findViewById<EditText>(R.id.ageInput)
        val calculateButton = view.findViewById<Button>(R.id.calculateButton)
        val resultContent = view.findViewById<LinearLayout>(R.id.resultContent)
        val resultLabel = view.findViewById<TextView>(R.id.resultLabel)
        val resultValue = view.findViewById<TextView>(R.id.resultValue)
        val resultBmr = view.findViewById<TextView>(R.id.resultBmr)
        val toggleTableButton = view.findViewById<Button>(R.id.toggleTableButton)
        val activityTable = view.findViewById<LinearLayout>(R.id.activityTable)

        val activityNames = activityLevels.map { it.name }
        activitySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            activityNames
        )

        activitySpinner.post {
            (activitySpinner.selectedView as? android.widget.TextView)?.setTextColor(Color.WHITE)
        }
        activitySpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, v: android.view.View?, position: Int, id: Long) {
                (v as? android.widget.TextView)?.setTextColor(Color.WHITE)
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }

        maleButton.setOnClickListener {
            isMale = true
            maleButton.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#2196F3"))
            femaleButton.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#444444"))
        }

        femaleButton.setOnClickListener {
            isMale = false
            femaleButton.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#2196F3"))
            maleButton.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#444444"))
        }

        calculateButton.setOnClickListener {
            val weight = weightInput.text.toString().toDoubleOrNull()
            val feet = feetInput.text.toString().toDoubleOrNull() ?: 0.0
            val inches = inchesInput.text.toString().toDoubleOrNull() ?: 0.0
            val age = ageInput.text.toString().toDoubleOrNull()
            val totalInches = (feet * 12) + inches
            val multiplier = activityLevels[activitySpinner.selectedItemPosition].multiplier

            if (weight == null || totalInches == 0.0 || age == null) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bmr = if (isMale) {
                66 + (6.2 * weight) + (12.7 * totalInches) - (6.76 * age)
            } else {
                655.1 + (4.35 * weight) + (4.7 * totalInches) - (4.7 * age)
            }

            val tdee = bmr * multiplier
            val genderLabel = if (isMale) "Male" else "Female"

            resultLabel.text = "Daily calorie needs ($genderLabel)"
            resultValue.text = "${tdee.toInt().toLocaleString()} kcal/day"
            resultBmr.text = "BMR: ${bmr.toInt()} kcal × $multiplier activity multiplier"
            resultContent.visibility = View.VISIBLE
        }

        toggleTableButton.setOnClickListener {
            tableVisible = !tableVisible
            if (tableVisible) {
                toggleTableButton.text = "Hide Activity Level Guide"
                activityTable.visibility = View.VISIBLE
                if (activityTable.childCount == 0) {
                    buildActivityTable(activityTable)
                }
            } else {
                toggleTableButton.text = "Show Activity Level Guide"
                activityTable.visibility = View.GONE
            }
        }
    }

    private fun buildActivityTable(container: LinearLayout) {
        activityLevels.forEach { level ->
            val card = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(32, 24, 32, 24)
                setBackgroundColor(Color.parseColor("#1E1E1E"))
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 0, 0, 16)
                layoutParams = params
            }

            val nameRow = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
            }

            val nameText = TextView(requireContext()).apply {
                text = level.name
                textSize = 15f
                setTextColor(Color.WHITE)
                setTypeface(null, android.graphics.Typeface.BOLD)
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val multiplierText = TextView(requireContext()).apply {
                text = "×${level.multiplier}"
                textSize = 14f
                setTextColor(Color.parseColor("#2196F3"))
                setTypeface(null, android.graphics.Typeface.BOLD)
            }

            nameRow.addView(nameText)
            nameRow.addView(multiplierText)

            val descText = TextView(requireContext()).apply {
                text = level.description
                textSize = 13f
                setTextColor(Color.parseColor("#AAAAAA"))
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.topMargin = 8
                layoutParams = params
            }

            card.addView(nameRow)
            card.addView(descText)
            container.addView(card)
        }
    }

    private fun Int.toLocaleString(): String = String.format("%,d", this)
}