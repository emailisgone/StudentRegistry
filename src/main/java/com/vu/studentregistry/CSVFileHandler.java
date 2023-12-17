package com.vu.studentregistry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.*;

public class CSVFileHandler extends FileHandler {
    @Override
    public List<Student> load(File file) throws IOException {
        List<Student> students = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(file.toPath());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            for (CSVRecord csvRecord : csvParser) {
                String name = csvRecord.get("name");
                Major major = Major.valueOf(csvRecord.get("major"));
                int courseNr = Integer.parseInt(csvRecord.get("courseNr"));
                int groupNr = Integer.parseInt(csvRecord.get("groupNr"));
                students.add(new Student(name, major, courseNr, groupNr));
            }
        }
        return students;
    }

    @Override
    public void save(List<Student> students, File file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath());
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("name", "major", "courseNr", "groupNr"))) {
            for (Student student : students) {
                csvPrinter.printRecord(student.getFullName(), student.getMajor(), student.getCourseNr(), student.getGroupNr());
            }
        }
    }
}
