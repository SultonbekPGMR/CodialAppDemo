package com.codialstudent.codialappdemo.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.codialstudent.codialappdemo.model.MyCourse
import com.codialstudent.codialappdemo.model.MyGroup
import com.codialstudent.codialappdemo.model.MyMentor
import com.codialstudent.codialappdemo.model.MyStudent
import com.codialstudent.codialappdemo.utils.Constants.COURSE_ABOUT
import com.codialstudent.codialappdemo.utils.Constants.COURSE_ID
import com.codialstudent.codialappdemo.utils.Constants.COURSE_NAME
import com.codialstudent.codialappdemo.utils.Constants.COURSE_TABLE
import com.codialstudent.codialappdemo.utils.Constants.DB_NAME
import com.codialstudent.codialappdemo.utils.Constants.DB_VERSION
import com.codialstudent.codialappdemo.utils.Constants.GROUP_COURSE_ID
import com.codialstudent.codialappdemo.utils.Constants.GROUP_DAY_INDEX
import com.codialstudent.codialappdemo.utils.Constants.GROUP_ID
import com.codialstudent.codialappdemo.utils.Constants.GROUP_MENTOR_ID
import com.codialstudent.codialappdemo.utils.Constants.GROUP_NAME
import com.codialstudent.codialappdemo.utils.Constants.GROUP_OPEN
import com.codialstudent.codialappdemo.utils.Constants.GROUP_TABLE
import com.codialstudent.codialappdemo.utils.Constants.GROUP_TIME_INDEX
import com.codialstudent.codialappdemo.utils.Constants.MENTOR_COURSE_ID
import com.codialstudent.codialappdemo.utils.Constants.MENTOR_FATHER_NAME
import com.codialstudent.codialappdemo.utils.Constants.MENTOR_ID
import com.codialstudent.codialappdemo.utils.Constants.MENTOR_NAME
import com.codialstudent.codialappdemo.utils.Constants.MENTOR_SURNAME
import com.codialstudent.codialappdemo.utils.Constants.MENTOR_TABLE
import com.codialstudent.codialappdemo.utils.Constants.STUDENT_DATE
import com.codialstudent.codialappdemo.utils.Constants.STUDENT_DAYS_INDEX
import com.codialstudent.codialappdemo.utils.Constants.STUDENT_FATHER_NAME
import com.codialstudent.codialappdemo.utils.Constants.STUDENT_GROUP_ID
import com.codialstudent.codialappdemo.utils.Constants.STUDENT_ID
import com.codialstudent.codialappdemo.utils.Constants.STUDENT_NAME
import com.codialstudent.codialappdemo.utils.Constants.STUDENT_SURNAME
import com.codialstudent.codialappdemo.utils.Constants.STUDENT_TABLE
import com.codialstudent.codialappdemo.utils.MyDbHelperInterface

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    MyDbHelperInterface {
    override fun onCreate(p0: SQLiteDatabase?) {
        val courseQuery =
            "create table $COURSE_TABLE ($COURSE_ID integer not null primary key autoincrement unique, $COURSE_NAME text not null, $COURSE_ABOUT text not null)"
        val mentorQuery =
            "create table $MENTOR_TABLE ($MENTOR_ID integer not null primary key autoincrement unique, $MENTOR_NAME text not null, $MENTOR_SURNAME text not null,$MENTOR_FATHER_NAME text not null,$MENTOR_COURSE_ID integer not null, foreign key ($MENTOR_COURSE_ID) references $COURSE_TABLE($COURSE_ID))"
        val groupQuery =
            "create table $GROUP_TABLE ($GROUP_ID integer not null primary key autoincrement unique, $GROUP_NAME text not null,$GROUP_COURSE_ID integer not null,$GROUP_MENTOR_ID integer not null,$GROUP_DAY_INDEX integer not null, $GROUP_TIME_INDEX integer not null,  $GROUP_OPEN integer not null, foreign key ($GROUP_MENTOR_ID) references $MENTOR_TABLE($MENTOR_ID), foreign key ($GROUP_COURSE_ID) references $COURSE_TABLE($COURSE_ID))"
        val studentQuery =
            "create table $STUDENT_TABLE ($STUDENT_ID integer not null primary key autoincrement unique, $STUDENT_SURNAME text not null, $STUDENT_NAME text not null,$STUDENT_FATHER_NAME text not null, $STUDENT_DAYS_INDEX integer not null, $STUDENT_GROUP_ID integer not null,$STUDENT_DATE text not null, foreign key ($STUDENT_GROUP_ID) references $GROUP_TABLE($GROUP_ID))"

        p0?.execSQL(courseQuery)
        p0?.execSQL(mentorQuery)
        p0?.execSQL(groupQuery)
        p0?.execSQL(studentQuery)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }


    override fun addCourse(course: MyCourse) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COURSE_NAME, course.name)
        contentValues.put(COURSE_ABOUT, course.about)
        database.insert(COURSE_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllCourse(): ArrayList<MyCourse> {
        val database = this.readableDatabase
        val courseList = ArrayList<MyCourse>()
        val cursor = database.rawQuery("select * from $COURSE_TABLE", null)
        if (cursor.moveToFirst()) {
            do {
                courseList.add(
                    MyCourse(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                    )
                )
            } while (cursor.moveToNext())
        }
        return courseList
    }

    override fun deleteCourse(course: MyCourse) {
        val database = this.writableDatabase
        database.delete(COURSE_TABLE, "$COURSE_ID=?", arrayOf(course.id.toString()))
        database.close()
    }

    override fun addMentor(mentor: MyMentor) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTOR_NAME, mentor.name)
        contentValues.put(MENTOR_SURNAME, mentor.surname)
        contentValues.put(MENTOR_FATHER_NAME, mentor.fatherName)
        contentValues.put(MENTOR_COURSE_ID, mentor.courseId?.id)
        database.insert(MENTOR_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllMentor(): ArrayList<MyMentor> {
        val database = this.readableDatabase
        val mentorList = ArrayList<MyMentor>()
        val cursor = database.rawQuery("select * from $MENTOR_TABLE", null)
        if (cursor.moveToFirst()) {
            do {
                mentorList.add(
                    MyMentor(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        getCourseById(cursor.getInt(4))
                    )
                )
            } while (cursor.moveToNext())
        }
        return mentorList
    }

    override fun editMentor(mentor: MyMentor) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTOR_NAME, mentor.name)
        contentValues.put(MENTOR_SURNAME, mentor.surname)
        contentValues.put(MENTOR_FATHER_NAME, mentor.fatherName)
        contentValues.put(MENTOR_COURSE_ID, mentor.courseId?.id)
        database.update(MENTOR_TABLE, contentValues, "$MENTOR_ID=?", arrayOf(mentor.id.toString()))

    }

    override fun deleteMentor(mentor: MyMentor) {
        val database = this.writableDatabase
        database.delete(MENTOR_TABLE, "$MENTOR_ID=?", arrayOf(mentor.id.toString()))
        database.close()
    }

    override fun addGroup(group: MyGroup) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUP_NAME, group.name)
        contentValues.put(GROUP_COURSE_ID, group.courseId?.id)
        contentValues.put(GROUP_MENTOR_ID, group.mentorId?.id)
        contentValues.put(GROUP_TIME_INDEX, group.timeIndex)
        contentValues.put(GROUP_DAY_INDEX, group.dayIndex)
        contentValues.put(GROUP_OPEN, group.isOpen)
        database.insert(GROUP_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllGroup(): ArrayList<MyGroup> {
        val database = this.readableDatabase
        val groupList = ArrayList<MyGroup>()
        val cursor = database.rawQuery("select * from $GROUP_TABLE", null)
        if (cursor.moveToFirst()) {
            do {
                groupList.add(
                    MyGroup(
                        cursor.getInt(0),
                        cursor.getString(1),
                        getCourseById(cursor.getInt(2)),
                        getMentorById(cursor.getInt(3)),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getInt(6),
                    )

                )
            } while (cursor.moveToNext())
        }
        return groupList
    }

    override fun editGroup(group: MyGroup) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUP_NAME, group.name)
        contentValues.put(GROUP_DAY_INDEX, group.dayIndex)
        contentValues.put(GROUP_OPEN, group.isOpen)
        contentValues.put(GROUP_TIME_INDEX, group.timeIndex)
        contentValues.put(GROUP_COURSE_ID, group.courseId?.id)
        contentValues.put(GROUP_MENTOR_ID, group.mentorId?.id)
        database.update(GROUP_TABLE, contentValues, "$GROUP_ID=?", arrayOf(group.id.toString()))

    }

    override fun deleteGroup(group: MyGroup) {
        val database = this.writableDatabase
        database.delete(GROUP_TABLE, "$GROUP_ID=?", arrayOf(group.id.toString()))
        database.close()
    }

    override fun addStudent(student: MyStudent) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_NAME, student.name)
        contentValues.put(STUDENT_SURNAME, student.surname)
        contentValues.put(STUDENT_FATHER_NAME, student.fatherName)
        contentValues.put(STUDENT_DAYS_INDEX, student.daysOfWeekIndex)
        contentValues.put(STUDENT_DATE, student.date)
        contentValues.put(STUDENT_GROUP_ID, student.groupId?.id)
        database.insert(STUDENT_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllStudent(): ArrayList<MyStudent> {
        val database = this.readableDatabase
        val studentList = ArrayList<MyStudent>()
        val cursor = database.rawQuery("select * from $STUDENT_TABLE", null)
        if (cursor.moveToFirst()) {
            do {
                studentList.add(
                    MyStudent(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        getGroupById(cursor.getInt(5)),
                        cursor.getString(6),
                        )
                )
            } while (cursor.moveToNext())
        }
        return studentList
    }

    override fun editStudent(student: MyStudent) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_NAME, student.name)
        contentValues.put(STUDENT_SURNAME, student.surname)
        contentValues.put(STUDENT_FATHER_NAME, student.fatherName)
        contentValues.put(STUDENT_DAYS_INDEX, student.daysOfWeekIndex)
        contentValues.put(STUDENT_GROUP_ID, student.groupId?.id)
        contentValues.put(STUDENT_DATE, student.date)
        database.update(STUDENT_TABLE, contentValues, "$STUDENT_ID=?", arrayOf(student.id.toString()))

    }

    override fun deleteStudent(student: MyStudent) {
        val database = this.writableDatabase
        database.delete(STUDENT_TABLE, "$STUDENT_ID=?", arrayOf(student.id.toString()))
        database.close()
    }

    override fun getCourseById(id: Int): MyCourse {
        val database = this.readableDatabase
        val cursor = database.query(
            COURSE_TABLE,
            arrayOf(
                COURSE_ID,
                COURSE_NAME,
                COURSE_ABOUT
            ),
            "$COURSE_ID =?",
            arrayOf(id.toString()),
            null, null, null
        )
        if (cursor != null && cursor.moveToFirst()) {
            return MyCourse(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
            )
        }

        return MyCourse(
            0,
            "cursor.getString(1)",
            "cursor.getString(2)",

            )
    }

    override fun getMentorById(id: Int): MyMentor {
        val database = this.readableDatabase
        val cursor = database.query(
            MENTOR_TABLE,
            arrayOf(
                MENTOR_ID,
                MENTOR_SURNAME,
                MENTOR_NAME,
                MENTOR_FATHER_NAME,
                MENTOR_COURSE_ID
            ),
            "$MENTOR_ID =?",
            arrayOf(id.toString()),
            null, null, null
        )
        if (cursor != null && cursor.moveToFirst()) {
            return MyMentor(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
            )
        }

        return MyMentor(
            0,
            "cursor.getString(1)",
            " cursor.getString(2)",
            " cursor.getString(3)",
        )
    }

    override fun getGroupById(id: Int): MyGroup {
        val database = this.readableDatabase
        val cursor = database.query(
            GROUP_TABLE,
            arrayOf(
                GROUP_ID,
                GROUP_NAME,
                GROUP_COURSE_ID,
                GROUP_MENTOR_ID,
                GROUP_DAY_INDEX,
                GROUP_TIME_INDEX,
                GROUP_OPEN
            ),
            "$GROUP_ID =?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()

        if (cursor != null && cursor.moveToFirst()) {
            return MyGroup(
                cursor.getInt(0),
                cursor.getString(1),
                getCourseById(cursor.getInt(2)),
                getMentorById(cursor.getInt(3)),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6),
            )
        }

        return MyGroup(
            0, "", getCourseById(cursor.getInt(2)),
            getMentorById(cursor.getInt(3)),
            1, 2, 3
        )
    }

}