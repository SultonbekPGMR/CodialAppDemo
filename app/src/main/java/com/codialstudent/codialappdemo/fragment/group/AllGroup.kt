package com.codialstudent.codialappdemo.fragment.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codialstudent.codialappdemo.R
import com.codialstudent.codialappdemo.adapter.ViewPagerAdapter
import com.codialstudent.codialappdemo.databinding.FragmentAllGroupBinding
import com.codialstudent.codialappdemo.db.MyDbHelper
import com.codialstudent.codialappdemo.model.MyCourse
import com.codialstudent.codialappdemo.model.MyMentor
import com.codialstudent.codialappdemo.utils.Constants.CURRENT_COURSE
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class AllGroup : Fragment() {
    private lateinit var binding: FragmentAllGroupBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var course: MyCourse
    private lateinit var myDbHelper: MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllGroupBinding.inflate(layoutInflater, container, false)
        course = arguments?.getSerializable("key") as MyCourse
        CURRENT_COURSE = course
myDbHelper = MyDbHelper(requireContext())
        val mentorList = ArrayList<MyMentor>()
        val dbMentorList = myDbHelper.getAllMentor()
        dbMentorList.forEach {
            if (it.courseId?.id == course.id) {
                mentorList.add(it)
            }
        }

        myDbHelper = MyDbHelper(requireContext())
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding.apply {
            tvLabel.text = course.name
            btnAdd.visibility = View.INVISIBLE
            myViewPager.adapter = viewPagerAdapter

            TabLayoutMediator(myTabLayout, myViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Ochilgan\nguruhlar"
                    1 -> tab.text = "Ochilayotgan\nguruhlar"
                }
            }.attach()

            myTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab == myTabLayout.getTabAt(0)) {
                        btnAdd.visibility = View.INVISIBLE
                    } else {
                        btnAdd.visibility = View.VISIBLE
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            btnAdd.setOnClickListener {
                if (mentorList.isEmpty()) {
                    Toast.makeText(context, "Mentor yo'q", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(R.id.addGroup, bundleOf("key" to course))
                }
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }

        return binding.root
    }

}