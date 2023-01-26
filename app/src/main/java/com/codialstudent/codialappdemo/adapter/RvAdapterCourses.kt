package com.codialstudent.codialappdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codialstudent.codialappdemo.databinding.RvItemBinding
import com.codialstudent.codialappdemo.model.MyCourse

class RvAdapterCourses(
    val context: Context,
    var list: ArrayList<MyCourse>,
    val rvItemClick: RvItemClick
) :
    RecyclerView.Adapter<RvAdapterCourses.Vh>() {

    inner class Vh(private val itemRvBinding: RvItemBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(courseData: MyCourse, position: Int) {
            itemRvBinding.tvName.text = courseData.name

            itemRvBinding.root.setOnClickListener {
                rvItemClick.itemClicked(courseData,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) =
        holder.onBind(list[position], position)


    override fun getItemCount(): Int = list.size


}

interface RvItemClick {
    fun itemClicked(courseData: MyCourse, position: Int)
}