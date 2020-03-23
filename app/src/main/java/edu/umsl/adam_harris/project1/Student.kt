package edu.umsl.adam_harris.project1

data class Student(val name: String)
val student1 = Student("Frances Daniel")
val student2 = Student("Kristy Rios")
val student3 = Student("Loren Palmer")
val student4 = Student("Gabriel Hansen")
val student5 = Student("Christian Hines")
val student6 = Student("Dean Lowe")
val student7 = Student("Frankie Todd")
val student8 = Student("Pete Weaver")
val student9 = Student("Donna Massey")
val student10 = Student("Ethel Hubbard")

//Note: UNKNOWN = 3 because 3 is the corresponding position the Unknown option is at in the DetailsActivity Spinner
val class1Students = mutableMapOf(student1 to UNKNOWN, student2 to UNKNOWN, student3 to UNKNOWN,
    student4 to UNKNOWN, student5 to UNKNOWN, student6 to UNKNOWN)

val class2Students = mutableMapOf(student5 to UNKNOWN, student6 to UNKNOWN, student7 to UNKNOWN,
    student8 to UNKNOWN, student9 to UNKNOWN, student10 to UNKNOWN)