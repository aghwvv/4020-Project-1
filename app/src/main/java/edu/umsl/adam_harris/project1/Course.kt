package edu.umsl.adam_harris.project1

data class Course(val courseName: String, val extendedName: String,
                  val courseNumber: String, val numOfStudents: Int, val courseDays: String,
                  var presentCount: Int, var lateCount: Int,
                  var students: MutableMap<Student, Int>) {

    /*Function to check all students attendance value and return the number of values that are present*/
    fun getPCount(): Int {
        var count = 0
        for (student in students) {
            //PRESENT = 0
            if (student.value == PRESENT) {
                count++
            }
        }
        return count
    }

    fun getLCount(): Int {
        var count = 0
        for (student in students) {
            //ABSENT = 1 and LATE = 2
            if (student.value == ABSENT || student.value == LATE) {
                count++
            }
        }
        return count
    }
}

val courses = arrayOf(
    Course("Android Apps", "CMP SCI 4222", "4222",
        class1Students.size, "T/Th", 0, 0,
        class1Students),
    Course("Programming Languages", "CMP SCI 4250", "4250",
        class2Students.size, "M/W", 0, 0,
        class2Students)
)