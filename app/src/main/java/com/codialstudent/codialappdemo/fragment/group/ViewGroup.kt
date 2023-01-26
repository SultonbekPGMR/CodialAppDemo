package com.codialstudent.codialappdemo.fragment.group

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
import com.codialstudent.codialappdemo.adapter.RvAdapterStudent
import com.codialstudent.codialappdemo.adapter.RvStudentInterface
import com.codialstudent.codialappdemo.databinding.FragmentViewGroupBinding
import com.codialstudent.codialappdemo.db.MyDbHelper
import com.codialstudent.codialappdemo.model.MyGroup
import com.codialstudent.codialappdemo.model.MyStudent
import com.codialstudent.codialappdemo.utils.Constants.ARRAY_TIME


class ViewGroup : Fragment(), RvStudentInterface {

    private lateinit var binding: FragmentViewGroupBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var rvAdapter: RvAdapterStudent
    private lateinit var studentList: ArrayList<MyStudent>
    private lateinit var group: MyGroup
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewGroupBinding.inflate(layoutInflater, container, false)
        group = arguments?.getSerializable("key") as MyGroup
        myDbHelper = MyDbHelper(requireContext())
        val dbStudentList = myDbHelper.getAllStudent()
        studentList = ArrayList()

        dbStudentList.forEach {
            if (it.groupId?.id == group.id) {
                studentList.add(it)
            }
        }

        rvAdapter = RvAdapterStudent(requireContext(), studentList, this)

        binding.apply {
            myRv.adapter = rvAdapter
            tvLabel.text = group.name.toString()
            tvName.text = group.name.toString()
            tvCountStudents.text = "O'quvchilar soni: ${studentList.size}"
            if (group.isOpen == 0) {
                btnStartGroup.visibility = View.GONE
            }
            tvTime.text = "Vaqti: ${ARRAY_TIME[group.timeIndex!!]}"

            btnStartGroup.setOnClickListener {
                group.isOpen = 0
                myDbHelper.editGroup(group)
                btnStartGroup.visibility = View.GONE
                Toast.makeText(context, "Guruhga dars boshlandi", Toast.LENGTH_SHORT).show()
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnAdd.setOnClickListener {
                findNavController().navigate(
                    R.id.addStudentInGroup,
                    bundleOf("isEdit" to false, "group" to group)
                )
            }


        }

        return binding.root
    }

    override fun btnEditClick(positionMentor: Int, student: MyStudent) {
        findNavController().navigate(
            R.id.addStudentInGroup,
            bundleOf("isEdit" to true, "group" to group, "student" to student)
        )
    }

    override fun btnDeleteClick(positionMentor: Int, student: MyStudent) {

        val dialog: AlertDialog.Builder =
            AlertDialog.Builder(requireContext(), R.style.MyMenuDialogTheme)
                .setMessage("Ushbu o'quvchini o'chirmoqchimisiz?")
                .setNegativeButton("Yo'q", null)
                .setPositiveButton(
                    "Xa"
                ) { _, _ ->
                    binding.apply {
                        rvAdapter.list.removeAt(positionMentor)
                        myRv.adapter?.notifyItemRemoved(positionMentor)
                        myRv.adapter?.notifyItemRangeChanged(0, rvAdapter.list.size)
                    }
                    myDbHelper.deleteStudent(student)
                }
        dialog.show()


    }

}