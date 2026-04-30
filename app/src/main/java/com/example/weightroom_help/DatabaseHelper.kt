package com.example.weightroom_help

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "exercise-db", null, 4) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE exercises (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                muscleGroup TEXT,
                equipment TEXT
            )
        """)
        db.execSQL("""
            CREATE TABLE recovery_tips (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                muscleGroup TEXT,
                type TEXT,
                name TEXT,
                description TEXT
            )
        """)
        seedExercises(db)
        seedRecovery(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS exercises")
        db.execSQL("DROP TABLE IF EXISTS recovery_tips")
        onCreate(db)
    }

    private fun seedExercises(db: SQLiteDatabase) {
        val exercises = listOf(
            // Chest
            Triple("Bench Press", "Chest", "Barbell"),
            Triple("Incline Press", "Chest", "Barbell"),
            Triple("Close Grip Bench Press", "Chest", "Barbell"),
            Triple("Dumbbell Chest Press", "Chest", "Dumbbell"),
            Triple("Chest Fly", "Chest", "Dumbbell"),
            Triple("Incline Dumbbell Press", "Chest", "Dumbbell"),
            Triple("Cable Crossover", "Chest", "Machine"),
            Triple("Machine Chest Press", "Chest", "Machine"),
            Triple("Pec Deck", "Chest", "Machine"),
            Triple("Push Ups", "Chest", "Bodyweight"),
            Triple("Wide Push Ups", "Chest", "Bodyweight"),
            Triple("Diamond Push Ups", "Chest", "Bodyweight"),
            // Legs
            Triple("Squats", "Legs", "Barbell"),
            Triple("Romanian Deadlift", "Legs", "Barbell"),
            Triple("Front Squat", "Legs", "Barbell"),
            Triple("Dumbbell Lunges", "Legs", "Dumbbell"),
            Triple("Dumbbell Squat", "Legs", "Dumbbell"),
            Triple("Bulgarian Split Squat", "Legs", "Dumbbell"),
            Triple("Leg Press", "Legs", "Machine"),
            Triple("Leg Curl", "Legs", "Machine"),
            Triple("Leg Extension", "Legs", "Machine"),
            Triple("Bodyweight Squat", "Legs", "Bodyweight"),
            Triple("Jump Squats", "Legs", "Bodyweight"),
            Triple("Walking Lunges", "Legs", "Bodyweight"),
            // Back
            Triple("Bent Over Row", "Back", "Barbell"),
            Triple("Deadlift", "Back", "Barbell"),
            Triple("Pendlay Row", "Back", "Barbell"),
            Triple("Dumbbell Row", "Back", "Dumbbell"),
            Triple("Single Arm Row", "Back", "Dumbbell"),
            Triple("Dumbbell Deadlift", "Back", "Dumbbell"),
            Triple("Lat Pulldown", "Back", "Machine"),
            Triple("Seated Cable Row", "Back", "Machine"),
            Triple("Machine Row", "Back", "Machine"),
            Triple("Pull Ups", "Back", "Bodyweight"),
            Triple("Chin Ups", "Back", "Bodyweight"),
            Triple("Inverted Rows", "Back", "Bodyweight"),
            // Shoulders
            Triple("Barbell OHP", "Shoulders", "Barbell"),
            Triple("Behind the Neck Press", "Shoulders", "Barbell"),
            Triple("Upright Row", "Shoulders", "Barbell"),
            Triple("Dumbbell Shoulder Press", "Shoulders", "Dumbbell"),
            Triple("Lateral Raises", "Shoulders", "Dumbbell"),
            Triple("Front Raises", "Shoulders", "Dumbbell"),
            Triple("Machine Shoulder Press", "Shoulders", "Machine"),
            Triple("Cable Lateral Raise", "Shoulders", "Machine"),
            Triple("Cable Face Pull", "Shoulders", "Machine"),
            Triple("Pike Push Ups", "Shoulders", "Bodyweight"),
            Triple("Handstand Hold", "Shoulders", "Bodyweight"),
            Triple("YTW Exercise", "Shoulders", "Bodyweight"),
            // Core
            Triple("Barbell Rollout", "Core", "Barbell"),
            Triple("Landmine Twist", "Core", "Barbell"),
            Triple("Barbell Suitcase Carry", "Core", "Barbell"),
            Triple("Dumbbell Side Bend", "Core", "Dumbbell"),
            Triple("Dumbbell Woodchop", "Core", "Dumbbell"),
            Triple("Dumbbell Farmer Carry", "Core", "Dumbbell"),
            Triple("Cable Crunch", "Core", "Machine"),
            Triple("Cable Pallof Press", "Core", "Machine"),
            Triple("Cable Woodchop", "Core", "Machine"),
            Triple("Plank", "Core", "Bodyweight"),
            Triple("Hollow Body Hold", "Core", "Bodyweight"),
            Triple("Dead Bug", "Core", "Bodyweight"),
            Triple("Bicycle Crunches", "Core", "Bodyweight"),
            Triple("Leg Raises", "Core", "Bodyweight"),
            Triple("Ab Wheel Rollout", "Core", "Bodyweight"),
            Triple("Mountain Climbers", "Core", "Bodyweight"),
            Triple("V Ups", "Core", "Bodyweight"),
            Triple("Side Plank", "Core", "Bodyweight")
        )
        exercises.forEach { (name, muscle, equipment) ->
            val values = ContentValues().apply {
                put("name", name)
                put("muscleGroup", muscle)
                put("equipment", equipment)
            }
            db.insert("exercises", null, values)
        }
    }

    private fun seedRecovery(db: SQLiteDatabase) {
        val tips = listOf(
            // Chest
            listOf("Chest", "Stretching", "Doorway Chest Stretch",
                "Stand in a doorway, place both forearms on the frame, and gently lean forward until you feel a stretch across your chest. Hold for 30 seconds, repeat 3 times."),
            listOf("Chest", "Stretching", "Floor Chest Opener",
                "Lie face up on the floor with arms out to your sides at 90 degrees. Let gravity pull your chest open for 60 seconds. Great passive stretch after a heavy pressing session."),
            listOf("Chest", "Stretching", "Overhead Lat and Chest Stretch",
                "Clasp your hands behind your head and gently pull your elbows back while looking up. Hold for 20 seconds and repeat 3 times to open the chest and front shoulders."),
            listOf("Chest", "Foam Rolling", "Foam Roll Pec Minor",
                "Place the foam roller just inside your shoulder near your chest. Apply gentle pressure and slowly roll along the pec muscle for 60 seconds each side."),
            listOf("Chest", "Foam Rolling", "Lacrosse Ball Pec Release",
                "Place a lacrosse ball against your pec near the armpit and lean into a wall. Hold pressure on tight spots for 20 to 30 seconds each before moving to the next."),
            listOf("Chest", "Light Movement", "Arm Circles",
                "Stand tall and extend arms out to the sides. Make slow large circles forward for 15 reps then reverse. Keeps the shoulder joint loose and promotes blood flow to the chest."),
            listOf("Chest", "Light Movement", "Band Pull Aparts",
                "Hold a resistance band in front of you at chest height with straight arms. Pull the band apart until your arms are fully extended to your sides. Do 3 sets of 15 reps to strengthen the rear delts and counterbalance chest tightness."),
            listOf("Chest", "Light Movement", "Standing Chest Squeeze",
                "Hold your arms at 90 degrees in front of you and squeeze your hands together as if hugging a tree. Pulse the squeeze for 2 sets of 20 to increase circulation through the pec muscle."),
            // Legs
            listOf("Legs", "Stretching", "Standing Quad Stretch",
                "Stand on one foot, pull the opposite foot toward your glute and hold for 30 seconds. Switch legs. Keep your knees together and stand tall."),
            listOf("Legs", "Stretching", "Seated Hamstring Stretch",
                "Sit on the floor with legs straight. Reach toward your toes and hold for 30 to 45 seconds. Keep your back flat rather than rounding to get a true hamstring stretch."),
            listOf("Legs", "Stretching", "Pigeon Pose Hip Stretch",
                "From a push up position, bring one knee forward and place it behind your wrist while extending the other leg back. Sink your hips toward the floor and hold for 45 seconds each side to release glutes and hip flexors."),
            listOf("Legs", "Stretching", "Couch Stretch",
                "Kneel facing away from a wall and place one foot up on the wall behind you. Push your hips forward until you feel a deep stretch through the front of the thigh and hip flexor. Hold for 45 seconds each side."),
            listOf("Legs", "Foam Rolling", "Foam Roll Quads and IT Band",
                "Lie face down and place the foam roller under your quads. Slowly roll from hip to just above the knee for 60 seconds. Then roll the outer thigh for the IT band."),
            listOf("Legs", "Foam Rolling", "Foam Roll Hamstrings",
                "Sit on the floor with the foam roller under your hamstrings. Use your hands to lift your hips and roll slowly from just below the glute to behind the knee for 60 seconds each leg."),
            listOf("Legs", "Foam Rolling", "Foam Roll Calves",
                "Sit with the foam roller under your calves. Cross one ankle over the other to increase pressure and roll slowly from ankle to just below the knee for 60 seconds per leg."),
            listOf("Legs", "Light Movement", "Bodyweight Walking Lunges",
                "Take slow controlled walking lunges across the room for 2 sets of 10 reps each leg. Keep the weight light and focus on full range of motion to flush out soreness."),
            listOf("Legs", "Light Movement", "Slow Air Squat",
                "Perform bodyweight squats with a 3 second descent and a 1 second pause at the bottom. Do 2 sets of 10 to restore range of motion and pump blood into sore quads and glutes."),
            listOf("Legs", "Light Movement", "Box Step Ups",
                "Use a low step or box and perform slow controlled step ups for 2 sets of 12 each leg. Focuses on quad activation without heavy loading, great for active recovery days."),
            // Back
            listOf("Back", "Stretching", "Child's Pose",
                "Kneel on the floor, sit back toward your heels, and stretch your arms forward on the ground. Hold for 45 seconds and focus on breathing deeply to release tension through the lower and upper back."),
            listOf("Back", "Stretching", "Seated Spinal Twist",
                "Sit on the floor with legs straight, bend one knee and cross it over. Twist toward the bent knee and hold for 30 seconds each side. Releases tightness through the mid and lower back."),
            listOf("Back", "Stretching", "Doorway Lat Stretch",
                "Grip a doorframe at shoulder height, step back, and let your body hang slightly while keeping your arm straight. Hold for 30 seconds each side to decompress the lat and teres major."),
            listOf("Back", "Stretching", "Thread the Needle",
                "On all fours, slide one arm underneath your body along the floor while rotating your torso. Hold for 20 seconds each side to mobilize the thoracic spine and stretch the upper back."),
            listOf("Back", "Foam Rolling", "Foam Roll Thoracic Spine",
                "Place the foam roller horizontally across your mid back. Support your head with your hands and slowly roll from the upper to lower back for 60 seconds. Avoid rolling the lower lumbar directly."),
            listOf("Back", "Foam Rolling", "Foam Roll Lats",
                "Lie on your side with the foam roller under your armpit along the side of your back. Roll slowly from armpit to just above the hip for 60 seconds each side."),
            listOf("Back", "Light Movement", "Cat-Cow Stretch",
                "On all fours, alternate between arching your back toward the ceiling and dropping your belly toward the floor. Do 15 slow reps to mobilize the entire spine and increase blood flow."),
            listOf("Back", "Light Movement", "Dead Hang",
                "Hang from a pull up bar with a relaxed grip for 20 to 30 seconds. Lets gravity decompress the spine and stretch the lats after heavy pulling work. Do 3 hangs with 30 seconds rest between."),
            listOf("Back", "Light Movement", "Bird Dog",
                "On all fours, extend the opposite arm and leg simultaneously and hold for 2 seconds before switching. Do 2 sets of 10 each side to activate the stabilizer muscles of the back without load."),
            // Shoulders
            listOf("Shoulders", "Stretching", "Cross Body Shoulder Stretch",
                "Pull one arm across your chest with the opposite hand and hold for 30 seconds. Switch sides. Great for the rear delt and rotator cuff after heavy pressing days."),
            listOf("Shoulders", "Stretching", "Sleeper Stretch",
                "Lie on your side on the floor with your bottom arm at 90 degrees. Use your top hand to gently push your bottom wrist toward the floor until you feel a stretch in the back of the shoulder. Hold for 30 seconds each side."),
            listOf("Shoulders", "Stretching", "Overhead Tricep and Shoulder Stretch",
                "Raise one arm overhead, bend at the elbow, and use the opposite hand to gently pull the elbow behind your head. Hold for 30 seconds each side to stretch the shoulder and tricep."),
            listOf("Shoulders", "Stretching", "Wall Shoulder Flexion Stretch",
                "Face a wall and walk your fingers up until your arm is fully extended overhead. Hold for 20 seconds and repeat 3 times to restore overhead range of motion after pressing."),
            listOf("Shoulders", "Foam Rolling", "Foam Roll Upper Traps",
                "Sit on the floor and place the foam roller behind your upper back near the base of the neck. Gently roll across the trap area for 60 seconds to release tightness from overhead work."),
            listOf("Shoulders", "Foam Rolling", "Lacrosse Ball Rear Delt Release",
                "Place a lacrosse ball between your rear shoulder and a wall. Lean in and find tight spots in the rear delt and rotator cuff. Hold pressure on each spot for 20 to 30 seconds before moving."),
            listOf("Shoulders", "Light Movement", "Arm Swings",
                "Do slow controlled arm swings across the body for 20 reps to promote circulation and loosen the joint. If you have a resistance band, hold it in front of you and pull it apart to shoulder height for 2 sets of 15."),
            listOf("Shoulders", "Light Movement", "Wall Angels",
                "Stand with your back flat against a wall and arms at 90 degrees. Slowly slide your arms up the wall into a Y shape and back down. Do 2 sets of 10 keeping constant contact with the wall to restore shoulder mobility."),
            listOf("Shoulders", "Light Movement", "External Rotation with Band",
                "Attach a resistance band at elbow height. Hold it with your elbow bent at 90 degrees against your side and rotate your forearm outward. Do 3 sets of 15 each side to strengthen the rotator cuff and reduce injury risk."),
            // Core
            listOf("Core", "Stretching", "Cobra Stretch",
                "Lie face down and press your hands into the floor near your chest. Straighten your arms to lift your chest while keeping your hips on the ground. Hold for 30 seconds and repeat 3 times to decompress the lumbar spine after core work."),
            listOf("Core", "Stretching", "Lying Knee to Chest",
                "Lie on your back and pull both knees to your chest. Hold for 30 to 45 seconds while breathing deeply. Releases the lower back and hip flexors which get tense during heavy ab training."),
            listOf("Core", "Stretching", "Seated Side Bend Stretch",
                "Sit cross legged and reach one arm overhead while leaning to the opposite side. Hold for 20 seconds each side to release the obliques and intercostal muscles along the ribs."),
            listOf("Core", "Stretching", "Standing Hip Flexor Stretch",
                "Step one foot forward into a lunge and lower your back knee to the ground. Push your hips forward until you feel a stretch down the front of the back hip. Hold for 30 seconds each side. Essential after heavy planks and leg raises."),
            listOf("Core", "Foam Rolling", "Foam Roll Lower Back",
                "Sit on the floor in front of the foam roller and lean back onto it so it sits just above the glutes. Roll gently across the lower back for 45 seconds. Avoid putting direct pressure on the spine — focus on the muscles on either side."),
            listOf("Core", "Foam Rolling", "Foam Roll Thoracic Spine for Core Relief",
                "Place the foam roller across your mid back and support your head with your hands. Roll from just above the lower back to the shoulder blades for 60 seconds. Releases the erector muscles that assist during core work."),
            listOf("Core", "Foam Rolling", "Lacrosse Ball Hip Flexor Release",
                "Lie face down and place a lacrosse ball just below your hip bone in the front of the hip. Apply gentle pressure and hold on tight spots for 20 to 30 seconds. Relieves deep hip flexor tension built up from leg raises and planks."),
            listOf("Core", "Light Movement", "Dead Bug",
                "Lie on your back with arms pointing to the ceiling and knees bent at 90 degrees. Slowly lower the opposite arm and leg toward the floor while keeping your lower back pressed flat. Return and switch sides. Do 2 sets of 10 each side."),
            listOf("Core", "Light Movement", "Slow Leg Raises",
                "Lie on your back and raise your legs to 90 degrees then lower them slowly over 4 seconds without letting them touch the floor. Do 2 sets of 10 at a controlled pace to promote blood flow without taxing the abs."),
            listOf("Core", "Light Movement", "Bear Crawl",
                "Start on all fours with knees hovering one inch off the ground. Crawl forward for 10 steps then backward for 10 steps keeping your back flat and hips level. Do 3 rounds to activate the deep core stabilizers in a low impact way.")
        )
        tips.forEach { (muscle, type, name, description) ->
            val values = ContentValues().apply {
                put("muscleGroup", muscle)
                put("type", type)
                put("name", name)
                put("description", description)
            }
            db.insert("recovery_tips", null, values)
        }
    }

    fun getFiltered(muscle: String, equipment: String): List<String> {
        val results = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT name FROM exercises WHERE muscleGroup = ? AND equipment = ?",
            arrayOf(muscle, equipment)
        )
        while (cursor.moveToNext()) results.add(cursor.getString(0))
        cursor.close()
        return results
    }

    fun getFiltered(muscle: String, equipmentList: List<String>): List<String> {
        if (equipmentList.isEmpty()) return emptyList()
        val placeholders = equipmentList.joinToString(",") { "?" }
        val args = arrayOf(muscle) + equipmentList.toTypedArray()
        val results = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT name FROM exercises WHERE muscleGroup = ? AND equipment IN ($placeholders)",
            args
        )
        while (cursor.moveToNext()) results.add(cursor.getString(0))
        cursor.close()
        return results
    }

    fun getRecoveryTips(muscle: String): List<RecoveryTip> {
        val results = mutableListOf<RecoveryTip>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT type, name, description FROM recovery_tips WHERE muscleGroup = ? ORDER BY type",
            arrayOf(muscle)
        )
        while (cursor.moveToNext()) {
            results.add(
                RecoveryTip(
                    type = cursor.getString(0),
                    name = cursor.getString(1),
                    description = cursor.getString(2)
                )
            )
        }
        cursor.close()
        return results
    }
}