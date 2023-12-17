package com.vu.studentregistry;

import com.vu.studentregistry.FileHandler;
import com.vu.studentregistry.Major;
import com.vu.studentregistry.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelFileHandler extends FileHandler {
    @Override
    public List<Student> load(File file) throws IOException {
        List<Student> students = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file))) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                rowIterator.next();  // Skip the header row
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String name = row.getCell(0).getStringCellValue();
                Major major = Major.valueOf(row.getCell(1).getStringCellValue());
                int courseNr = (int) row.getCell(2).getNumericCellValue();
                int groupNr = (int) row.getCell(3).getNumericCellValue();
                students.add(new Student(name, major, courseNr, groupNr));
            }
        }
        return students;
    }

    @Override
    public void save(List<Student> students, File file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream out = new FileOutputStream(file)) {
            Sheet sheet = workbook.createSheet("Students");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("name");
            headerRow.createCell(1).setCellValue("major");
            headerRow.createCell(2).setCellValue("courseNr");
            headerRow.createCell(3).setCellValue("groupNr");
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(student.getFullName());
                row.createCell(1).setCellValue(student.getMajor().toString());
                row.createCell(2).setCellValue(student.getCourseNr());
                row.createCell(3).setCellValue(student.getGroupNr());
            }
            workbook.write(out);
        }
    }
}
