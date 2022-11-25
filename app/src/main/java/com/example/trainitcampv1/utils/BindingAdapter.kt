package com.example.trainitcampv1.utils

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.trainitcampv1.R
import com.example.trainitcampv1.data.entity.Priority
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

object BindingAdapter {
    @androidx.databinding.BindingAdapter("android:navigateToAddFragment")
    @JvmStatic
    fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
        view.setOnClickListener {
            if (navigate) {
                view.findNavController().navigate(R.id.action_homeFragment_to_addFragment)
            }
        }
    }

    @androidx.databinding.BindingAdapter("android:emptyDatabase")
    @JvmStatic
    fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
        when (emptyDatabase.value) {
            true -> view.visibility = View.VISIBLE
            else -> view.visibility = View.INVISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @androidx.databinding.BindingAdapter("android:parsePriorityColor")
    @JvmStatic
    fun parsePriorityColor(cardView: MaterialCardView, priority: Priority) {
        when (priority) {
            Priority.HIGH -> {
                cardView.setCardBackgroundColor(cardView.context.getColor(R.color.pink))
            }
            Priority.MEDIUM -> {
                cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))
            }
            Priority.LOW -> {
                cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))
            }
        }
    }
}