package com.candroid.catchmeifyoucan.Utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.candroid.catchmeifyoucan.databinding.FragmentGameBinding

var ivArr: ArrayList<ImageView> = ArrayList()

fun createLayout(layout: String, context: Context, binding: FragmentGameBinding, resId: Int?): ArrayList<ImageView>{

    ivArr.clear()

    when(layout){
        "3 x 3" -> {
            createLayoutAs3x3(context, binding, resId, 9)
        }

        "4 x 4" -> {
            createLayoutAs4x4(context, binding, resId, 16)
        }

        "5 x 5" -> {
            createLayoutAs5x5(context, binding, resId, 25)
        }
    }

    return ivArr
}

private fun createLayoutAs3x3(context: Context, binding: FragmentGameBinding, resId: Int?, ivCount: Int){
    val iv9: ArrayList<ImageView> = ArrayList()
    binding.grid3x3.visibility = View.VISIBLE

    for (index in 1..ivCount){
        val view = context.resources.getIdentifier("iv${ivCount}_$index", "id", context.packageName)
        iv9.add(binding.root.findViewById(view))
    }

    ivArr = iv9

    for (iv in ivArr){
        iv.setImageResource(resId!!)
    }

}

private fun createLayoutAs4x4(context: Context, binding: FragmentGameBinding, resId: Int?, ivCount: Int){
    val iv16: ArrayList<ImageView> = ArrayList()
    binding.grid4x4.visibility = View.VISIBLE

    for (index in 1..ivCount){
        val view = context.resources.getIdentifier("iv${ivCount}_$index", "id", context.packageName)
        iv16.add(binding.root.findViewById(view))
    }

    ivArr = iv16

    for (iv in ivArr){
        iv.setImageResource(resId!!)
    }
}

private fun createLayoutAs5x5(context: Context, binding: FragmentGameBinding, resId: Int?, ivCount: Int){
    val iv25: ArrayList<ImageView> = ArrayList()
    binding.grid5x5.visibility = View.VISIBLE

    for (index in 1..ivCount){
        val view = context.resources.getIdentifier("iv${ivCount}_$index", "id", context.packageName)
        iv25.add(binding.root.findViewById(view))
    }
    ivArr = iv25

    for (iv in ivArr){
        iv.setImageResource(resId!!)
    }
}