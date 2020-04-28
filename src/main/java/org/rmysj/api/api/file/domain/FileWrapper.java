package org.rmysj.api.api.file.domain;

import java.io.File;

public class FileWrapper implements Comparable {

    private File file;

    public FileWrapper(File file) {
        this.file = file;
    }

    public int compareTo(Object obj) {
        assert obj instanceof FileWrapper;
        FileWrapper castObj = (FileWrapper) obj;
        if (this.getOffset() -  castObj.getOffset() > 0) {
            return 1;
        } else if (this.getOffset() -  castObj.getOffset() < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public File getFile() {
        return this.file;
    }

    public long getOffset(){
       String offset =  this.file.getName().substring(this.file.getName().lastIndexOf("_") + 1);
       return Long.parseLong(offset);
    }
}
