package com.codialstudent.codialappdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codialstudent.codialappdemo.databinding.RvItemMentorBinding
import com.codialstudent.codialappdemo.model.MyMentor
import com.codialstudent.codialappdemo.model.course.MentorModel

class RvAdapterMentor(
    val context: Context,
    var list: ArrayList<MyMentor>,
    val rvMentorInterface: RvMentorInterface
) :
    RecyclerView.Adapter<RvAdapterMentor.Vh>() {

    inner class Vh(val itemRvBinding: RvItemMentorBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(user: MyMentor, position: Int) {
            itemRvBinding.apply {
                tvNameMentor.text = "${user.name} ${user.surname}"

                btnEdit.setOnClickListener {
                    rvMentorInterface.btnEditClick(position)
                }
                btnDelete.setOnClickListener {
                    rvMentorInterface.btnDeleteClick(position, user)
                }

            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemMentorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) =
        holder.onBind(list[position], position)


    override fun getItemCount(): Int = list.size


}

interface RvMentorInterface {
    fun btnEditClick(positionMentor: Int)
    fun btnDeleteClick(positionMentor: Int, mentor: MyMentor)
}