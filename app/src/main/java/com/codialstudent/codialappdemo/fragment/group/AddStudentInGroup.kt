package com.codialstudent.codialappdemo.fragment.group

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codialstudent.codialappdemo.R
import com.codialstudent.codialappdemo.databinding.FragmentAddStudentInGroupBinding
import com.codialstudent.codialappdemo.db.MyDbHelper
import com.codialstudent.codialappdemo.model.MyGroup
import com.codialstudent.codialappdemo.model.MyStudent
import java.util.*

class AddStudentInGroup : Fragment() {

    private lateinit var binding: FragmentAddStudentInGroupBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var student: MyStudent
    private var dateState = false

    @RequiresApi(Build.VERSION_CODES.N)
    val myCalendar: Calendar = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStudentInGroupBinding.inflate(layoutInflater, container, false)
        myDbHelper = MyDbHelper(requireContext())
        val isEdit = arguments?.getBoolean("isEdit")
        val group = arguments?.getSerializable("group") as MyGroup



        binding.apply {
            if (isEdit == true) {
                student = arguments?.getSerializable("student") as MyStudent
                tvLabel.text= "Talaba O'zgartirish"
                btnSave.text = "O'zgartirish"
                edtName.setText(student.name)
                edtSurname.setText(student.surname)
                edtFatherName.setText(student.fatherName)
                edtDate.setText(student.date)
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnSave.setOnClickListener {
                val name = edtName.text.toString().trim()
                val surname = edtSurname.text.toString().trim()
                val fatherName = edtFatherName.text.toString().trim()
                if (dateState && name.isNotEmpty() && surname.isNotEmpty() && fatherName.isNotEmpty()) {
                    if (isEdit == true) {
                        student.surname = surname
                        student.name = name
                        student.fatherName = fatherName
                        student.date = edtDate.text.toString().trim()
                        myDbHelper.editStudent(student)

                    } else {
                        student = MyStudent(
                            surname,
                            name,
                            fatherName,
                            group.dayIndex,
                            group,
                            edtDate.text.toString().trim()
                        )
                        myDbHelper.addStudent(student)
                    }
                    Toast.makeText(context, "Saqlandi", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }else{
                    Toast.makeText(context, "Malumotlarni kiriting!", Toast.LENGTH_SHORT).show()
                }

            }

        }


        val date = DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->
            myCalendar.set(Calendar.YEAR, p1)
            myCalendar.set(Calendar.MONTH, p2)
            myCalendar.set(Calendar.DAY_OF_MONTH, p3)
            updateLabel()
            dateState = true
        }
        binding.edtDate.setOnClickListener {
            val dialogDate = DatePickerDialog(
                requireContext(),
                R.style.DialogTheme,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
            dialogDate.show()
            dialogDate.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.blue))
            dialogDate.getButton(DatePickerDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.yellow))

        }
        binding.coordinateLayout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                binding.apply {
                    edtName.clearFocus()
                    edtSurname.clearFocus()
                    edtName.clearFocus()
                    edtFatherName.clearFocus()
                    edtDate.clearFocus()
                }
                return true
            }

        })



        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.edtDate.setText(dateFormat.format(myCalendar.time))
    }
}