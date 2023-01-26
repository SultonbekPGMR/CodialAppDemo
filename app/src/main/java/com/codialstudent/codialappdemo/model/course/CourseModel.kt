package com.codialstudent.codialappdemo.model.course

data class CourseModel(
    var id: Int,
    val name: String,
    val about: String,
    var listStudent: ArrayList<StudentModel>,
    var listMentor:ArrayList<MentorModel>
):java.io.Serializable
