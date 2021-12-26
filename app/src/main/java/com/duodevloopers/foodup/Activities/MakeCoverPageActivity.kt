package com.duodevloopers.foodup.Activities

import android.Manifest
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.duodevloopers.foodup.R
import com.duodevloopers.foodup.databinding.ActivityMakeCoverPageBinding
import com.duodevloopers.foodup.myapp.MyApp
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MakeCoverPageActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityMakeCoverPageBinding


    val myCalendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeCoverPageBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)

        val spinner = findViewById<Spinner>(R.id.department_spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.department, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        val date = OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = monthOfYear
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateLabel()
        }
        binding!!.dateOfSub.setOnClickListener {
            DatePickerDialog(
                this@MakeCoverPageActivity,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
                .show()
        }

        binding.studentName.setText(MyApp.loggedInUser?.name)
        binding.studentId.setText(MyApp.loggedInUser?.id)
        binding.studentSection.setText(MyApp.loggedInUser?.section)

        binding.makePdf.setOnClickListener {

            val courseCode = binding!!.courseCode.text.toString()
            val courseTitle = binding!!.courseTitle.text.toString()
            val submissionDate = binding!!.dateOfSub.text.toString()
            val teacherName = binding!!.teacherName.text.toString()
            val teacherDesignation = binding!!.teacherDesignation.text.toString()
            val studentId = binding!!.studentId.text.toString()
            val semester = binding!!.studentSemester.text.toString()
            val section = binding!!.studentSection.text.toString()
            val studentName = binding!!.studentName.text.toString()

            Dexter.withContext(this@MakeCoverPageActivity)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        try {
                            createPdf(
                                courseCode,
                                courseTitle,
                                submissionDate,
                                teacherName,
                                studentName,
                                teacherDesignation,
                                studentId,
                                semester,
                                section
                            )
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest,
                        token: PermissionToken
                    ) { /* ... */
                    }
                }).check()
        }
    }

    @Throws(FileNotFoundException::class)
    private fun createPdf(
        courseCode: String,
        courseTitle: String,
        submissionDate: String,
        teacherName: String,
        studentName: String,
        teacherDesignation: String,
        studentId: String,
        semester: String,
        section: String
    ) {
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            .toString()
        val file = File(pdfPath, System.currentTimeMillis().toString() + ".pdf")
        val outputStream: OutputStream = FileOutputStream(file)
        val writer = PdfWriter(outputStream)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(0f, 0f, 0f, 0f)
        val drawable = getDrawable(R.drawable.assignment_cover)
        val bitmap = (drawable as BitmapDrawable?)!!.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bitmapData = stream.toByteArray()
        val imageData = ImageDataFactory.create(bitmapData)
        val image = Image(imageData)
        val width = floatArrayOf(160f, 160f)
        val table1 = Table(width)
        table1.setHorizontalAlignment(HorizontalAlignment.CENTER)

        //first row
        table1.addCell(Cell().add(Paragraph("Course Code").setBold()))
        table1.addCell(Cell().add(Paragraph(courseCode).setTextAlignment(TextAlignment.CENTER)))

        //second row
        table1.addCell(Cell().add(Paragraph("Course Title").setBold()))
        table1.addCell(Cell().add(Paragraph(courseTitle).setTextAlignment(TextAlignment.CENTER)))

        //third row
        table1.addCell(Cell().add(Paragraph("Submission Date").setBold()))
        table1.addCell(Cell().add(Paragraph(submissionDate).setTextAlignment(TextAlignment.CENTER)))
        table1.marginTop = 30f
        val widthSecondTable = floatArrayOf(200f, 200f)
        val table2 = Table(widthSecondTable)
        table2.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table2.addCell(Cell().add(Paragraph("Submitted To").setTextAlignment(TextAlignment.CENTER)))
        table2.addCell(Cell().add(Paragraph("Submitted By").setTextAlignment(TextAlignment.CENTER)))
        table2.addCell(
            Cell().add(
                Paragraph(teacherName).setTextAlignment(TextAlignment.CENTER).setBold()
                    .setFontSize(16f)
            )
        )
        table2.addCell(
            Cell().add(
                Paragraph(studentName).setTextAlignment(TextAlignment.CENTER).setBold()
                    .setFontSize(16f)
            )
        )
        table2.addCell(Cell().add(Paragraph(teacherDesignation).setTextAlignment(TextAlignment.CENTER)))
        table2.addCell(Cell().add(Paragraph(studentId).setTextAlignment(TextAlignment.CENTER)))
        table2.addCell(Cell().add(Paragraph("CSE, IIUC").setTextAlignment(TextAlignment.CENTER)))
        table2.addCell(
            Cell().add(
                Paragraph(
                    "Semester: $semester Section: $section"
                ).setTextAlignment(TextAlignment.CENTER)
            )
        )
        table2.marginTop = 40f
        document.add(image)
        document.add(table1)
        document.add(table2)
        document.add(
            Paragraph("\n\n\n\n\n\n\n\n\n\n(Teacher Signature)\n\n\n").setTextAlignment(
                TextAlignment.RIGHT
            ).setMarginRight(16f)
        )
        document.close()
        Toast.makeText(this, "PDF Created", Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        val department = adapterView.getItemAtPosition(i).toString()
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf: DateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding!!.dateOfSub.setText(sdf.format(myCalendar.time))
    }
}