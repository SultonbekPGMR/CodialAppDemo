package com.codialstudent.codialappdemo.fragment.mentor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.codialstudent.codialappdemo.R
import com.codialstudent.codialappdemo.adapter.RvAdapterCourses
import com.codialstudent.codialappdemo.adapter.RvItemClick
import com.codialstudent.codialappdemo.databinding.FragmentAllCourseMentorBinding
import com.codialstudent.codialappdemo.db.MyDbHelper
import com.codialstudent.codialappdemo.model.MyCourse


class AllCourseMentor : Fragment(), RvItemClick {
    private lateinit var binding: FragmentAllCourseMentorBinding
    private lateinit var rvAdapterCourses: RvAdapterCourses


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCourseMentorBinding.inflate(layoutInflater, container, false)
        val myDbHelper = MyDbHelper(requireContext())
        rvAdapterCourses = RvAdapterCourses(requireContext(), myDbHelper.getAllCourse(), this)
        binding.apply {
            myRv.adapter = rvAdapterCourses

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }


        return binding.root
    }

    override fun itemClicked(courseData: MyCourse, position: Int) {
        findNavController().navigate(R.id.allMentor, bundleOf("key" to courseData))
    }

}