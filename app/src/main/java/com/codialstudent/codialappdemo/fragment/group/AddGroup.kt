package com.codialstudent.codialappdemo.fragment.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codialstudent.codialappdemo.databinding.FragmentAddGroupBinding
import com.codialstudent.codialappdemo.db.MyDbHelper
import com.codialstudent.codialappdemo.model.MyCourse
import com.codialstudent.codialappdemo.model.MyGroup
import com.codialstudent.codialappdemo.model.MyMentor
import com.codialstudent.codialappdemo.utils.Constants.ARRAY_DAYS
import com.codialstudent.codialappdemo.utils.Constants.ARRAY_TIME


class AddGroup : Fragment() {
    private lateinit var binding: FragmentAddGroupBinding
    private lateinit var myDbHelper: MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddGroupBinding.inflate(layoutInflater, container, false)
        val course = arguments?.getSerializable("key") as MyCourse

        myDbHelper = MyDbHelper(requireContext())
        val mentorList = ArrayList<MyMentor>()
        val mentorNamesList = ArrayList<String>()
        val dbMentorList = myDbHelper.getAllMentor()
        dbMentorList.forEach {
            if (it.courseId?.id == course.id) {
                mentorList.add(it)
                mentorNamesList.add("${it.name} ${it.surname}")

            }
        }

        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            mentorNamesList
        )


        binding.apply {
            spinnerMentor.setAdapter(adapter)
            spinnerTime.setAdapter(
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    ARRAY_TIME
                )
            )
            spinnerDay.setAdapter(
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    ARRAY_DAYS
                )
            )
            var mentorPosition = 0
            var dayPosition = 0
            var timePosition = 0
            var mentorState = false
            var dayState = false
            var timeState = false
            spinnerMentor.setOnItemClickListener { adapterView, view, i, l ->
                mentorPosition = i
                mentorState = true
            }
            spinnerDay.setOnItemClickListener { adapterView, view, i, l ->
                dayPosition = i
                dayState = true
            }
            spinnerTime.setOnItemClickListener { adapterView, view, i, l ->
                timePosition = i
                timeState = true
            }

            btnSave.setOnClickListener {
                val name = edtName.text.toString().trim()
                if (mentorState && dayState && timeState && name.isNotEmpty()){
                    val group = MyGroup(
                        name,
                        course,
                        mentorList[mentorPosition],
                        dayPosition,
                        timePosition,
                        1
                    )

                    myDbHelper.addGroup(group)
                    findNavController().popBackStack()
                }else{
                    Toast.makeText(context, "Malumotlarni kiriting!", Toast.LENGTH_SHORT).show()
                }

            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }





        binding.coordinateLayout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
               binding.apply {
                   edtName.clearFocus()
                   spinnerMentor.clearFocus()
                   spinnerDay.clearFocus()
                   spinnerTime.clearFocus()
               }
                return true
            }

        })

        return binding.root
    }

}