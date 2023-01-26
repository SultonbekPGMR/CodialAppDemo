package com.codialstudent.codialappdemo.model

class MyGroup:java.io.Serializable {
    var id: Int? = null
    var name: String? = null
    var courseId: MyCourse? = null
    var mentorId: MyMentor? = null
    var timeIndex: Int? = null
    var dayIndex: Int? = null
    var isOpen:Int? = null


    constructor(
        name: String?,
        courseId: MyCourse?,
        mentorId: MyMentor?,
        timeIndex: Int?,
        dayIndex: Int?,
        isOpen: Int?
    ) {
        this.name = name
        this.courseId = courseId
        this.mentorId = mentorId
        this.timeIndex = timeIndex
        this.dayIndex = dayIndex
        this.isOpen = isOpen
    }

    constructor(
        id: Int?,
        name: String?,
        courseId: MyCourse?,
        mentorId: MyMentor?,
        timeIndex: Int?,
        dayIndex: Int?,
        isOpen: Int?
    ) {
        this.id = id
        this.name = name
        this.courseId = courseId
        this.mentorId = mentorId
        this.timeIndex = timeIndex
        this.dayIndex = dayIndex
        this.isOpen = isOpen
    }

}