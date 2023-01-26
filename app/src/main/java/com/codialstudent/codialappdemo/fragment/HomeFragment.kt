package com.codialstudent.codialappdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codialstudent.codialappdemo.R
import com.codialstudent.codialappdemo.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), OnClickListener {


    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.apply {
            btnCourse.setOnClickListener(this@HomeFragment)
            btnGroup.setOnClickListener(this@HomeFragment)
            btnMentor.setOnClickListener(this@HomeFragment)
        }

        return binding.root
    }

    override fun onClick(p0: View?) {
        binding.apply {
            when (p0?.id) {
                btnCourse.id -> {

                    findNavController().navigate(R.id.allCourses)
                }
                btnGroup.id -> {
                    findNavController().navigate(R.id.allCourseGroup)
                }
                btnMentor.id -> {
                    findNavController().navigate(R.id.allCourseMentor)
                }


            }
        }

    }

}