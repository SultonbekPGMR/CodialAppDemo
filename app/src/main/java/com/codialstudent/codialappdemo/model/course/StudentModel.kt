package com.codialstudent.codialappdemo.model.course

import com.codialstudent.codialappdemo.model.MyGroup

data class StudentModel(
    val surname: String,
    val name: String,
    val fatherName: String,
    val date: String,
    val groupId: MyGroup,
)