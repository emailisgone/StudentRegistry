package com.vu.studentregistry;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class FileHandler {
    public abstract List<Student> load(File file) throws IOException;
    public abstract void save(List<Student> students, File file) throws IOException;
}
