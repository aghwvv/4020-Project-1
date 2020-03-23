package edu.umsl.adam_harris.project1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.course_listing_fragment.*

//Definitions for the integer positions of the different attendance values in the Spinner in DetailsActivity
//I defined these purely to make the code more readable instead of just using integer literals when referring to positions on the spinner
const val PRESENT = 0
const val ABSENT = 1
const val LATE = 2
const val UNKNOWN = 3

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_ATTENDANCE = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course_listing_fragment)

        courseListingFragmentView.layoutManager = LinearLayoutManager(this)
        courseListingFragmentView.adapter = CourseAdapter()

        courseListingFragmentView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    inner class CourseAdapter: RecyclerView.Adapter<CourseHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
            val inflater = LayoutInflater.from(this@MainActivity)
            val itemView = inflater.inflate(R.layout.course_item_layout, parent, false)
            return CourseHolder(itemView)
        }

        override fun getItemCount(): Int = courses.size

        override fun onBindViewHolder(holder: CourseHolder, position: Int) {
            val course = courses[position]
            holder.itemView.setOnClickListener {
                val intent = DetailsActivity.newIntent(this@MainActivity, position)
                startActivityForResult(intent, REQUEST_CODE_ATTENDANCE)
            }
            holder.bindCourse(course)
        }
    }

    class CourseHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val courseNameTextView: TextView = itemView.findViewById(R.id.courseNameItemView)
        private val extendedCourseNameTextView: TextView = itemView.findViewById(R.id.extendedCourseNameItemView)
        private val courseNumberTextView : TextView = itemView.findViewById(R.id.courseNumberItemView)
        private val numberOfStudentsTextView : TextView = itemView.findViewById(R.id.numberOfStudentsItemView)
        private val courseDaysTextView : TextView = itemView.findViewById(R.id.courseDaysItemView)
        private val attendanceTextView : TextView = itemView.findViewById(R.id.attendanceItemView)
        private val lateRateTextView : TextView = itemView.findViewById(R.id.lateRateItemView)

        fun bindCourse(course: Course) {
            courseNameTextView.text = course.courseName
            extendedCourseNameTextView.text = course.extendedName
            courseNumberTextView.text = String.format("Course Number:\n%s", course.courseNumber)
            numberOfStudentsTextView.text = String.format("Number of Students:\n%d", course.numOfStudents)
            courseDaysTextView.text = String.format("Days:\n%s", course.courseDays)
            attendanceTextView.text = String.format("Present Students:\n%d/%d", course.presentCount, course.numOfStudents)
            lateRateTextView.text = String.format("Late/Absent Students:\n%d/%d", course.lateCount, course.numOfStudents)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (data == null) { return }

        when (requestCode) {
            REQUEST_CODE_ATTENDANCE -> {
                val coursePosition = data.getIntExtra("coursePosition", 0)
                courses[coursePosition].presentCount = courses[coursePosition].getPCount()
                courses[coursePosition].lateCount = courses[coursePosition].getLCount()
                (courseListingFragmentView.adapter as CourseAdapter).notifyDataSetChanged()
            }
        }
    }
}