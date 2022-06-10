package Util;

import javax.swing.*;
import java.io.File;

public class FileNameGenerator {
    public File writeFileSelectedLocation(String getName , JFileChooser getSelectedFile){
        String regex = ":+|/+|\"+|<+|>+|\\*+";
        String fileName = getName.replaceAll(regex , " ").trim();
        File file = null;
        if (System.getProperty("os.name").startsWith("Mac")){
            file = new File(getSelectedFile.getSelectedFile()+"/"+fileName);
        }else{
            file = new File(getSelectedFile.getSelectedFile()+"\\"+fileName);
        }
        return file;
    }

    public File writeFileInFile(String getName , File getFile){
        String regex = ":+|/+|\"+|<+|>+|\\*+";
        String fileName = getName.replaceAll(regex , " ").trim();
        File file = null;
        if (System.getProperty("os.name").startsWith("Mac")){
            file = new File(getFile.getPath()+"/"+fileName);
        }else{
            file = new File(getFile.getPath()+"\\"+fileName);
        }
        return file;
    }
}
