package com.alifmaulanarizqi.workoutapp

data class ExerciseModel(
    var id: Int = 0,
    var name: String = "",
    var image: Int = 0,
    var desc: String = "",
    var isCompleted: Boolean = false,
    var isSelected: Boolean = false
)
