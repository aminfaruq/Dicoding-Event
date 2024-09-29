package com.aminfaruq.dicodingevent.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

fun RecyclerView.hideBottomNavigationOnScroll(activity: AppCompatActivity, bottomNavId: Int) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val bottomNavigationView = activity.findViewById<BottomNavigationView>(bottomNavId)
            if (dy > 0 && recyclerView.canScrollVertically(1)) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }
    })
}
