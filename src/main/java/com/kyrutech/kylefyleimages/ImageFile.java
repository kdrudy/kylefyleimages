package com.kyrutech.kylefyleimages;

public class ImageFile {

    String directory;
    String fileName;
    boolean current = false;

    public ImageFile(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
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
