package com.kyrutech.kylefyleimages;

public class ImageFile {

    String directory;
    String fileName;

    public ImageFile(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
