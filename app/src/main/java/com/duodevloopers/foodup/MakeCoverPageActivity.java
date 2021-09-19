package com.duodevloopers.foodup;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.duodevloopers.foodup.databinding.ActivityMakeCoverPageBinding;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MakeCoverPageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityMakeCoverPageBinding binding;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeCoverPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Spinner spinner = findViewById(R.id.department_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        binding.dateOfSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MakeCoverPageActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });


        binding.makePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String courseCode = binding.courseCode.getText().toString();
                String courseTitle = binding.courseTitle.getText().toString();
                String submissionDate = binding.dateOfSub.getText().toString();
                String teacherName = binding.teacherName.getText().toString();
                String teacherDesignation = binding.teacherDesignation.getText().toString();
                String studentId = binding.studentId.getText().toString();
                String semester = binding.studentSemester.getText().toString();
                String section = binding.studentSection.getText().toString();
                String studentName = binding.studentName.getText().toString();


                Dexter.withContext(MakeCoverPageActivity.this)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                try {
                                    createPdf(courseCode, courseTitle, submissionDate, teacherName, studentName, teacherDesignation, studentId, semester, section);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                        }).check();

            }
        });
    }

    private void createPdf(String courseCode, String courseTitle, String submissionDate, String teacherName, String studentName, String teacherDesignation, String studentId, String semester, String section) throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(pdfPath, System.currentTimeMillis()+".pdf");
        OutputStream outputStream = new FileOutputStream(file);


        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A4);
        document.setMargins(0, 0, 0, 0);

        Drawable drawable = getDrawable(R.drawable.assignment_cover);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);

        float[] width = {160, 160};
        Table table1 = new Table(width);
        table1.setHorizontalAlignment(HorizontalAlignment.CENTER);

        //first row
        table1.addCell(new Cell().add(new Paragraph("Course Code").setBold()));
        table1.addCell(new Cell().add(new Paragraph(courseCode).setTextAlignment(TextAlignment.CENTER)));

        //second row
        table1.addCell(new Cell().add(new Paragraph("Course Title").setBold()));
        table1.addCell(new Cell().add(new Paragraph(courseTitle).setTextAlignment(TextAlignment.CENTER)));

        //third row
        table1.addCell(new Cell().add(new Paragraph("Submission Date").setBold()));
        table1.addCell(new Cell().add(new Paragraph(submissionDate).setTextAlignment(TextAlignment.CENTER)));

        table1.setMarginTop(30);

        float[] widthSecondTable = {200,200};
        Table table2 = new Table(widthSecondTable);
        table2.setHorizontalAlignment(HorizontalAlignment.CENTER);


        table2.addCell(new Cell().add(new Paragraph("Submitted To").setTextAlignment(TextAlignment.CENTER)));
        table2.addCell(new Cell().add(new Paragraph("Submitted By").setTextAlignment(TextAlignment.CENTER)));

        table2.addCell(new Cell().add(new Paragraph(teacherName).setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(16)));
        table2.addCell(new Cell().add(new Paragraph(studentName).setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(16)));

        table2.addCell(new Cell().add(new Paragraph(teacherDesignation).setTextAlignment(TextAlignment.CENTER)));
        table2.addCell(new Cell().add(new Paragraph(studentId).setTextAlignment(TextAlignment.CENTER)));

        table2.addCell(new Cell().add(new Paragraph("CSE, IIUC").setTextAlignment(TextAlignment.CENTER)));
        table2.addCell(new Cell().add(new Paragraph("Semester: "+semester+" "+"Section: "+section).setTextAlignment(TextAlignment.CENTER)));


        table2.setMarginTop(40);

        document.add(image);
        document.add(table1);
        document.add(table2);
        document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n(Teacher Signature)\n\n\n").setTextAlignment(TextAlignment.RIGHT).setMarginRight(16));
        document.close();
        Toast.makeText(this, "PDF Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String department = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        DateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        binding.dateOfSub.setText(sdf.format(myCalendar.getTime()));
    }
}