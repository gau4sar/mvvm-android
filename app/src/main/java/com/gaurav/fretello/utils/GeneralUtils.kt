package com.gaurav.fretello.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult


object GeneralUtils {

    fun getExerciseImageUrl(exerciseId: String): String {
        return "${Constants.BASE_URL}/img/$exerciseId.png"
    }

    fun getBitmap(context: Context, imageUrl :String): Bitmap {
        val loading = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}