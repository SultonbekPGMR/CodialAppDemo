package com.codialstudent.codialappdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codialstudent.codialappdemo.databinding.RvItemGroupBinding
import com.codialstudent.codialappdemo.model.MyGroup
import com.codialstudent.codialappdemo.model.MyStudent

class RvAdapterGroup(
    val groupList: ArrayList<MyGroup>,
    val dbStudentList: List<MyStudent>,
    val context: Context,
    val rvGroupClick: RvGroupClick
) :
    RecyclerView.Adapter<RvAdapterGroup.Vh>() {

    inner class Vh(val itemRvBinding: RvItemGroupBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        private var counter = 0
        fun onBind(user: MyGroup, position: Int) {
            itemRvBinding.apply {
                tvNameMentor.text = user.name
                counter = 0
                dbStudentList.forEach {
                    if (it.groupId?.id == user.id) {
                        counter++
                    }
                }

                tvCountStudents.text = "O'quvchilar soni: $counter ta"
                btnEdit.setOnClickListener {
                    rvGroupClick.btnEditClick(user, position)
                }
                btnDelete.setOnClickListener {
                    rvGroupClick.btnDeleteClick(user, position)
                }
                btnView.setOnClickListener {
                    rvGroupClick.btnViewClick(user, position)
                }
                root.setOnClickListener {
                    rvGroupClick.btnViewClick(user, position)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) =
        holder.onBind(groupList[position], position)


    override fun getItemCount(): Int = groupList.size


}

interface RvGroupClick {
    fun btnEditClick(group: MyGroup, position: Int)
    fun btnViewClick(group: MyGroup, position: Int)
    fun btnDeleteClick(group: MyGroup, position: Int)
}