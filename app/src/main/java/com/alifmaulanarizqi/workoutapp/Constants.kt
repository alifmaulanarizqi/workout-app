package com.alifmaulanarizqi.workoutapp

object Constants {

    private val objectId = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    private val objectName = arrayOf(
        "Exercise 1",
        "Exercise 2",
        "Exercise 3",
        "Exercise 4",
        "Exercise 5",
        "Exercise 6",
        "Exercise 7",
        "Exercise 8",
        "Exercise 9",
        "Exercise 10",
        "Exercise 11",
        "Exercise 12"
    )

    private val objectImage = arrayOf(
        R.drawable.exercise1,
        R.drawable.exercise2,
        R.drawable.exercise3,
        R.drawable.exercise4,
        R.drawable.exercise5,
        R.drawable.exercise6,
        R.drawable.exercise7,
        R.drawable.exercise8,
        R.drawable.exercise9,
        R.drawable.exercise10,
        R.drawable.exercise11,
        R.drawable.exercise12
    )

    private val objectDesc = arrayOf(
        "Raise your hands and knees",
        "Push-up stance and pull your legs on eby one",
        "Lie down and raise your hands and feet on air",
        "Squat and do a kick",
        "Lie down and put your foot on air",
        "Doing exercise 6",
        "Doing exercise 7",
        "Doing exercise 8",
        "Doing exercise 9",
        "Doing exercise 10",
        "Doing exercise 11",
        "Doing exercise 12",
    )

    private val objectIsCompleted = arrayOf(
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false
    )

    private val objectIsSelected = arrayOf(
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false
    )

    fun getExercise(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()
        for(position in objectId.indices) {
            val exercise = ExerciseModel()

            exercise.id = objectId[position]
            exercise.name = objectName[position]
            exercise.image = objectImage[position]
            exercise.desc = objectDesc[position]
            exercise.isCompleted = objectIsCompleted[position]
            exercise.isSelected = objectIsSelected[position]

            exerciseList.add(exercise)
        }

        return exerciseList
    }

}