package com.codialstudent.codialappdemo.fragment.mentor

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codialstudent.codialappdemo.R
import com.codialstudent.codialappdemo.adapter.RvAdapterMentor
import com.codialstudent.codialappdemo.adapter.RvMentorInterface
import com.codialstudent.codialappdemo.databinding.AlertDialogMentorEditBinding
import com.codialstudent.codialappdemo.databinding.FragmentAllMentorBinding
import com.codialstudent.codialappdemo.db.MyDbHelper
import com.codialstudent.codialappdemo.model.MyCourse
import com.codialstudent.codialappdemo.model.MyGroup
import com.codialstudent.codialappdemo.model.MyMentor
import com.codialstudent.codialappdemo.model.MyStudent

open class AllMentor : Fragment(), RvMentorInterface {
    private lateinit var binding: FragmentAllMentorBinding
    private lateinit var rvAdapter: RvAdapterMentor
    private lateinit var course: MyCourse
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var mentorList: ArrayList<MyMentor>
    private lateinit var dbGroupList: ArrayList<MyGroup>
    private lateinit var dbStudentList: ArrayList<MyStudent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMentorBinding.inflate(layoutInflater, container, false)
        course = arguments?.getSerializable("key") as MyCourse
        myDbHelper = MyDbHelper(requireContext())
        mentorList = ArrayList<MyMentor>()
        val dbMentorList = myDbHelper.getAllMentor()
        dbMentorList.forEach {
            if (it.courseId?.id == course.id) {
                mentorList.add(it)
            }
        }

        dbGroupList = myDbHelper.getAllGroup()
        dbStudentList = myDbHelper.getAllStudent()

        rvAdapter =
            RvAdapterMentor(requireContext(), mentorList, this)
        binding.apply {
            actionBarTitle.text = course.name

            myRv.adapter = rvAdapter

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnAdd.setOnClickListener {
                findNavController().navigate(R.id.addMentor, bundleOf("key" to course))
            }

        }

        return binding.root
    }


    override fun btnEditClick(positionMentor: Int) {
        val mentor = mentorList[positionMentor]
        val surname = mentor.surname
        val name = mentor.name
        val fatherName = mentor.fatherName
        Toast.makeText(context, "${mentor.id}", Toast.LENGTH_SHORT).show()
        val alertDialogLayoutBinding =
            AlertDialogMentorEditBinding.inflate(layoutInflater)
        alertDialogLayoutBinding.apply {
            edtSurname.setText(surname)
            edtName.setText(name)
            edtFatherName.setText(fatherName)
        }
        val dialog: AlertDialog =
            AlertDialog.Builder(requireContext(), R.style.MyMenuDialogTheme)
                .setView(alertDialogLayoutBinding.root)
                .setPositiveButton(
                    "O'zgartirish"
                ) { _, _ ->
                    alertDialogLayoutBinding.apply {
                        mentor.surname = edtSurname.text.toString().trim()
                        mentor.name = edtName.text.toString().trim()
                        mentor.fatherName = edtFatherName.text.toString().trim()
                        myDbHelper.editMentor(mentor)
                        rvAdapter.list[positionMentor] = mentor
                        rvAdapter.notifyItemChanged(positionMentor)
                    }


                }
                .setNegativeButton("Yopish", null)
                .create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFB800"))
    }

    override fun btnDeleteClick(positionMentor: Int, mentor: MyMentor) {

        val dialog: AlertDialog.Builder =
            AlertDialog.Builder(requireContext(), R.style.MyMenuDialogTheme)
                .setMessage("Ushbu mentorni o'chirmoqchimisiz?")
                .setNegativeButton("Yo'q", null)
                .setPositiveButton(
                    "Xa"
                ) { _, _ ->
                    binding.apply {
                        rvAdapter.list.removeAt(positionMentor)
                        myRv.adapter?.notifyItemRemoved(positionMentor)
                        myRv.adapter?.notifyItemRangeChanged(0, rvAdapter.list.size)

                    }
                    myDbHelper.deleteMentor(mentor)

                    dbGroupList.forEach { myGroup ->
                        if (myGroup.mentorId?.id == mentor.id) {
                            dbStudentList.forEach {
                                if (it.groupId?.id == myGroup.id) {
                                    myDbHelper.deleteStudent(it)
                                }
                            }
                            myDbHelper.deleteGroup(myGroup)
                        }
                    }
                }
        dialog.show()

    }


}

