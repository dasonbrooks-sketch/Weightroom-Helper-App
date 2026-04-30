package com.example.weightroom_help

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecoveryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_recovery, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DatabaseHelper(requireContext())

        val spinner = view.findViewById<Spinner>(R.id.sorenessSpinner)
        val button = view.findViewById<Button>(R.id.recoveryButton)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recoveryRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val muscles = listOf("Chest", "Legs", "Back", "Shoulders", "Core")
        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            muscles
        )

        spinner.post {
            (spinner.selectedView as? android.widget.TextView)?.setTextColor(Color.WHITE)
        }

        spinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                (view as? android.widget.TextView)?.setTextColor(Color.WHITE)
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }

        button.setOnClickListener {
            val selected = spinner.selectedItem.toString()
            val tips = db.getRecoveryTips(selected)
            recyclerView.adapter = RecoveryAdapter(tips)
        }
    }
}