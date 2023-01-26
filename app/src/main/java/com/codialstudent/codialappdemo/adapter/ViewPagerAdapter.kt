package com.codialstudent.codialappdemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codialstudent.codialappdemo.fragment.course.AllCourses
import com.codialstudent.codialappdemo.fragment.group.GroupsOpened
import com.codialstudent.codialappdemo.fragment.group.GroupsOpening
import com.codialstudent.codialappdemo.fragment.mentor.AllMentor
import java.nio.file.attribute.GroupPrincipal

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = if (position == 0) {
           GroupsOpened()
        } else {
            GroupsOpening()
        }
        return fragment
    }
}