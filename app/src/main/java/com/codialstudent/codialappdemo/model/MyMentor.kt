package com.codialstudent.codialappdemo.model

class MyMentor {
    var id: Int? = null
    var surname: String? = null
    var name: String? = null
    var fatherName: String? = null
    var courseId: MyCourse? = null

    constructor(
        id: Int?,
        surname: String?,
        name: String?,
        fatherName: String?,
        courseId: MyCourse?
    ) {
        this.id = id
        this.surname = surname
        this.name = name
        this.fatherName = fatherName
        this.courseId = courseId
    }

    constructor(surname: String?, name: String?, fatherName: String?, courseId: MyCourse?) {
        this.surname = surname
        this.name = name
        this.fatherName = fatherName
        this.courseId = courseId
    }

    constructor(id: Int?, surname: String?, name: String?, fatherName: String?) {
        this.id = id
        this.surname = surname
        this.name = name
        this.fatherName = fatherName
    }


}