package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

  private val students = mutableListOf(
    StudentModel("Nguyễn Văn Bình", "SV001"),
    StudentModel("Trần Thị Hoài Thu", "SV002"),
    StudentModel("Lê Minh Anh", "SV003"),
    StudentModel("Phạm Quốc Đạt", "SV004"),
    StudentModel("Đỗ Ngọc Hải", "SV005"),
    StudentModel("Vũ Hoàng Nhật Nam", "SV006"),
    StudentModel("Hoàng Anh Tuấn", "SV007"),
    StudentModel("Bùi Thị Kim Dung", "SV008"),
    StudentModel("Đinh Hữu Thành", "SV009"),
    StudentModel("Nguyễn Đức Huy", "SV010"),
    StudentModel("Phạm Hoàng Long", "SV011"),
    StudentModel("Trần Thanh Hà", "SV012"),
    StudentModel("Lê Khánh Linh", "SV013"),
    StudentModel("Vũ Văn Nghĩa", "SV014"),
    StudentModel("Hoàng Thị Mỹ Duyên", "SV015"),
    StudentModel("Đỗ Minh Nhật", "SV016"),
    StudentModel("Nguyễn Hải Yến", "SV017"),
    StudentModel("Trần Thị Ngọc Ánh", "SV018"),
    StudentModel("Phạm Đức Mạnh", "SV019"),
    StudentModel("Lê Thùy Linh", "SV020"),
    StudentModel("Vũ Thị Bích Ngọc", "SV021"),
    StudentModel("Hoàng Gia Huy", "SV022"),
    StudentModel("Bùi Minh Khoa", "SV023"),
    StudentModel("Đinh Quang Hùng", "SV024"),
    StudentModel("Nguyễn Thị Thu Thảo", "SV025"),
    StudentModel("Trần Quốc Khánh", "SV026"),
    StudentModel("Lê Anh Vũ", "SV027"),
    StudentModel("Phạm Thanh Tâm", "SV028"),
    StudentModel("Đỗ Thị Kim Oanh", "SV029"),
    StudentModel("Vũ Minh Triết", "SV030"),


  )
  private lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Initialize the adapter
    studentAdapter = StudentAdapter(students, ::editStudent, ::deleteStudent)

    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    // Handle "Add New" button click
    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddStudentDialog()
    }
  }

  private fun showAddStudentDialog() {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
    val editName = dialogView.findViewById<EditText>(R.id.edit_text_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_text_id)

    val dialog = AlertDialog.Builder(this)
      .setTitle("Add New Student")
      .setView(dialogView)
      .setPositiveButton("Add") { _, _ ->
        val name = editName.text.toString()
        val id = editId.text.toString()
        if (name.isNotEmpty() && id.isNotEmpty()) {
          val newStudent = StudentModel(name, id)
          students.add(newStudent)
          studentAdapter.notifyItemInserted(students.size - 1)
        }
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }


  private fun editStudent(position: Int) {
    val student = students[position]
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
    val editName = dialogView.findViewById<EditText>(R.id.edit_text_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_text_id)


    // Pre-fill the existing student details
    editName.setText(student.studentName)
    editId.setText(student.studentId)

    val dialog = AlertDialog.Builder(this)
      .setTitle("Edit Student")
      .setView(dialogView)
      .setPositiveButton("Save") { _, _ ->
        val newName = editName.text.toString()
        val newId = editId.text.toString()
        if (newName.isNotEmpty() && newId.isNotEmpty()) {
          students[position] = StudentModel(newName, newId)
          studentAdapter.notifyItemChanged(position)
        }
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }


  private fun deleteStudent(position: Int) {
    val deletedStudent = students[position]
    students.removeAt(position)
    studentAdapter.notifyItemRemoved(position)

    // Show Snackbar with Undo option
    Snackbar.make(findViewById(R.id.recycler_view_students), "Student deleted", Snackbar.LENGTH_LONG)
      .setAction("Undo") {
        students.add(position, deletedStudent)
        studentAdapter.notifyItemInserted(position)
      }.show()
  }
}
