package com.codialstudent.codialappdemo.fragment.mentor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codialstudent.codialappdemo.databinding.FragmentAddMentorBinding
import com.codialstudent.codialappdemo.db.MyDbHelper
import com.codialstudent.codialappdemo.model.MyCourse
import com.codialstudent.codialappdemo.model.MyMentor


class AddMentor : Fragment() {

    private lateinit var binding: FragmentAddMentorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddMentorBinding.inflate(layoutInflater, container, false)
        val course = arguments?.getSerializable("key") as MyCourse
        val myDbHelper = MyDbHelper(requireContext())

        var surname = ""
        var name = ""
        var fatherName = ""

        binding.apply {

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSave.setOnClickListener {
                surname = edtSurname.text.toString().trim()
                name = edtName.text.toString().trim()
                fatherName = edtFatherName.text.toString().trim()
                myDbHelper.addMentor(
                    MyMentor(
                        surname, name, fatherName, course
                    )
                )

                findNavController().popBackStack()
            }
        }

        return binding.root
    }


}