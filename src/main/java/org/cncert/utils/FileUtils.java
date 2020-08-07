package org.cncert.utils;

import java.io.File;
import java.io.Serializable;

public class FileUtils{
    public static boolean deleteFileOrDirectory(File file){
        if(file != null && file.isFile()){
            return  file.delete();
        }
        if(file != null && file.isDirectory()){
            File[] childFiles = file.listFiles();
            if(childFiles == null || childFiles.length == 0){
                return file.delete();
            }
            for(File f: childFiles){
                deleteFileOrDirectory(f);
            }
            return file.delete();
        }
        return false;
    }
}
