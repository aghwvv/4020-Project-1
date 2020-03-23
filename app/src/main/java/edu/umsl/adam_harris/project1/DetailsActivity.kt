package edu.umsl.adam_harris.project1

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.student_listing_fragment.*

class DetailsActivity : AppCompatActivity() {

    private var coursePosition = 0

    companion object {
        private const val EXTRA_COURSE_POSITION = "edu.umsl.adam_harris.project1.course_position"

        @JvmStatic
        fun newIntent(context: Context, course: Int): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_COURSE_POSITION, course)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_listing_fragment)

        coursePosition = intent.getIntExtra(EXTRA_COURSE_POSITION, 0)

        studentListingFragmentView.layoutManager = LinearLayoutManager(this)
        studentListingFragmentView.adapter = StudentAdapter()

        studentListingFragmentView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        attendanceResetButton.setOnClickListener{
            for (element in courses[coursePosition].students) {
                element.setValue(3)
            }
            (studentListingFragmentView.adapter as StudentAdapter).notifyDataSetChanged()
        }
    }

    inner class StudentAdapter: RecyclerView.Adapter<StudentHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
            val inflater = LayoutInflater.from(this@DetailsActivity)
            val itemView = inflater.inflate(R.layout.student_item_layout, parent, false)
            return StudentHolder(itemView)
        }

        override fun getItemCount(): Int = courses[coursePosition].students.size

        override fun onBindViewHolder(holder: StudentHolder, studentPosition: Int) {
            val student = courses[coursePosition].students.asIterable().elementAt(studentPosition) as MutableMap.MutableEntry<Student, Int>
            holder.bindStudent(student)
        }
    }

    inner class StudentHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val studentNameTextView: TextView = itemView.findViewById(R.id.studentNameItemView)
        private val attendanceSpinnerView: Spinner = itemView.findViewById(R.id.attendanceSpinner)

        fun bindStudent(student: MutableMap.MutableEntry<Student, Int>) {
            studentNameTextView.text = student.key.name
            attendanceSpinnerView.adapter =
                ArrayAdapter.createFromResource(
                this@DetailsActivity, R.array.attendance_options_array,
                android.R.layout.simple_spinner_item
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    attendanceSpinnerView.adapter = adapter
                }

            attendanceSpinnerView.setSelection(student.value)

            attendanceSpinnerView.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    student.setValue(position)
                    val intent = Intent()
                    intent.putExtra("coursePosition", coursePosition)
                    setResult(Activity.RESULT_OK, intent)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    //Empty function
                }
            }
        }
    }
}