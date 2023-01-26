package com.codialstudent.codialappdemo.model

import com.codialstudent.codialappdemo.model.course.MentorModel

class MyStudent :java.io.Serializable{
    var id: Int? = null
    var surname: String? = null
    var name: String? = null
    var fatherName: String? = null
    var daysOfWeekIndex: Int? = null
    var groupId: MyGroup? = null
    var date:String? = null

    constructor(
        id: Int?,
        surname: String?,
        name: String?,
        fatherName: String?,
        daysOfWeekIndex: Int?,
        groupId: MyGroup?,
        date: String?
    ) {
        this.id = id
        this.surname = surname
        this.name = name
        this.fatherName = fatherName
        this.daysOfWeekIndex = daysOfWeekIndex
        this.groupId = groupId
        this.date = date
    }

    constructor(
        surname: String?,
        name: String?,
        fatherName: String?,
        daysOfWeekIndex: Int?,
        groupId: MyGroup?,
        date: String?
    ) {
        this.surname = surname
        this.name = name
        this.fatherName = fatherName
        this.daysOfWeekIndex = daysOfWeekIndex
        this.groupId = groupId
        this.date = date
    }

}