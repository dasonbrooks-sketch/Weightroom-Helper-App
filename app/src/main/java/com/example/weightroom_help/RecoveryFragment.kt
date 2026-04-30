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

    private val recoveryData = mapOf(
        "Chest" to listOf(
            RecoveryTip(
                type = "Stretching",
                name = "Doorway Chest Stretch",
                description = "Stand in a doorway, place both forearms on the frame, and gently lean forward until you feel a stretch across your chest. Hold for 30 seconds, repeat 3 times."
            ),
            RecoveryTip(
                type = "Foam Rolling",
                name = "Foam Roll Pec Minor",
                description = "Place the foam roller just inside your shoulder near your chest. Apply gentle pressure and slowly roll along the pec muscle for 60 seconds each side."
            ),
            RecoveryTip(
                type = "Light Movement",
                name = "Arm Circles",
                description = "Stand tall and extend arms out to the sides. Make slow large circles forward for 15 reps then reverse. Keeps the shoulder joint loose and promotes blood flow to the chest."
            )
        ),
        "Legs" to listOf(
            RecoveryTip(
                type = "Stretching",
                name = "Standing Quad Stretch",
                description = "Stand on one foot, pull the opposite foot toward your glute and hold for 30 seconds. Switch legs. Keep your knees together and stand tall."
            ),
            RecoveryTip(
                type = "Foam Rolling",
                name = "Foam Roll Quads and IT Band",
                description = "Lie face down and place the foam roller under your quads. Slowly roll from hip to just above the knee for 60 seconds. Then roll the outer thigh for the IT band."
            ),
            RecoveryTip(
                type = "Light Movement",
                name = "Bodyweight Walking Lunges",
                description = "Take slow controlled walking lunges across the room for 2 sets of 10 reps each leg. Keep the weight light and focus on full range of motion to flush out soreness."
            )
        ),
        "Back" to listOf(
            RecoveryTip(
                type = "Stretching",
                name = "Child's Pose",
                description = "Kneel on the floor, sit back toward your heels, and stretch your arms forward on the ground. Hold for 45 seconds and focus on breathing deeply to release tension through the lower and upper back."
            ),
            RecoveryTip(
                type = "Foam Rolling",
                name = "Foam Roll Thoracic Spine",
                description = "Place the foam roller horizontally across your mid back. Support your head with your hands and slowly roll from the upper to lower back for 60 seconds. Avoid rolling the lower lumbar directly."
            ),
            RecoveryTip(
                type = "Light Movement",
                name = "Cat-Cow Stretch",
                description = "On all fours, alternate between arching your back toward the ceiling and dropping your belly toward the floor. Do 15 slow reps to mobilize the entire spine and increase blood flow."
            )
        ),
        "Shoulders" to listOf(
            RecoveryTip(
                type = "Stretching",
                name = "Cross Body Shoulder Stretch",
                description = "Pull one arm across your chest with the opposite hand and hold for 30 seconds. Switch sides. Great for the rear delt and rotator cuff after heavy pressing days."
            ),
            RecoveryTip(
                type = "Foam Rolling",
                name = "Foam Roll Upper Traps",
                description = "Sit on the floor and place the foam roller behind your upper back near the base of the neck. Gently roll across the trap area for 60 seconds to release tightness from overhead work."
            ),
            RecoveryTip(
                type = "Light Movement",
                name = "Arm Swings",
                description = "Do slow controlled arm swings across the body for 20 reps to promote circulation and loosen the joint. If you have a resistance band, hold it in front of you and pull it apart to shoulder height for 2 sets of 15."
            )
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_recovery, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner = view.findViewById<Spinner>(R.id.sorenessSpinner)
        val button = view.findViewById<Button>(R.id.recoveryButton)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recoveryRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val muscles = listOf("Chest", "Legs", "Back", "Shoulders")
        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            muscles
        )

        spinner.post {
            (spinner.selectedView as? android.widget.TextView)?.setTextColor(Color.WHITE)
        }

        spinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                (view as? android.widget.TextView)?.setTextColor(Color.WHITE)
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }

        button.setOnClickListener {
            val selected = spinner.selectedItem.toString()
            val tips = recoveryData[selected] ?: emptyList()
            recyclerView.adapter = RecoveryAdapter(tips)
        }
    }
}