package com.codialstudent.codialappdemo.utils

import com.codialstudent.codialappdemo.model.MyCourse

object Constants {
    const val DB_NAME = "db_codial"
    const val DB_VERSION = 1

    const val COURSE_TABLE = "coursed"
    const val COURSE_ID = "course_id"
    const val COURSE_NAME = "course_name"
    const val COURSE_ABOUT = "course_about"

    const val GROUP_TABLE = "gr_tb"
    const val GROUP_ID = "group_id"
    const val GROUP_NAME = "group_name"
    const val GROUP_MENTOR_ID = "group_mentor_id"
    const val GROUP_COURSE_ID = "group_course_id"
    const val GROUP_TIME_INDEX = "group_time_index"
    const val GROUP_DAY_INDEX = "group_day_index"
    const val GROUP_OPEN = "group_open"


    const val MENTOR_TABLE = "mentor"
    const val MENTOR_ID = "mentor_id"
    const val MENTOR_SURNAME = "mentor_sur_name"
    const val MENTOR_NAME = "mentor_name"
    const val MENTOR_FATHER_NAME = "mentor_father_name"
    const val MENTOR_COURSE_ID = "mentor_course_id"

    const val STUDENT_TABLE = "student"
    const val STUDENT_ID = "student_id"
    const val STUDENT_SURNAME = "student_sur_name"
    const val STUDENT_NAME = "student_name"
    const val STUDENT_FATHER_NAME = "student_father_name"
    const val STUDENT_GROUP_ID = "student_group_id"
    const val STUDENT_DAYS_INDEX = "student_days_index"
    const val STUDENT_DATE = "student_date"

    val ARRAY_TIME = arrayOf("10:00-12:00", "12:00-14:00", "14:00-16:00", "16:00-18:00", "18:00-20:00")
    val ARRAY_DAYS = arrayOf("Juft kunlari", "Toq kunlari")


    var CURRENT_COURSE = MyCourse("name","description")
}