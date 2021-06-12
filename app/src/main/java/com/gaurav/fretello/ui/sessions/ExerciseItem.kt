package com.gaurav.fretello.ui.sessions

import coil.load
import com.gaurav.fretello.R
import com.gaurav.fretello.data.model.Exercise
import com.gaurav.fretello.databinding.ItemExerciseLayoutBinding
import com.xwray.groupie.databinding.BindableItem
import timber.log.Timber

open class ExerciseItem(private val exercise: Exercise) :
    BindableItem<ItemExerciseLayoutBinding>() {

    override fun getLayout() = R.layout.item_exercise_layout

    override fun bind(viewBinding: ItemExerciseLayoutBinding, position: Int) {
        viewBinding.tvExerciseName.text = "${exercise.name}"
        viewBinding.tvPracticedAtBPM.text = "@ ${exercise.practicedAtBpm} BPM"

        Timber.d("Exercise image: ${exercise.exerciseImage}")
        viewBinding.ivExercise.load(exercise.exerciseImage)
    }
}