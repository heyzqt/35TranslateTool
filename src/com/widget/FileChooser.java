package com.widget;

import javax.swing.*;
import java.io.File;

/**
 * Created by heyzqt 9/25/2017
 */
public class FileChooser {

    private String filepath = "";

    private String filename = "";

    private static final String TAG = "FileChooserb ";

    public static void main(String[] args) {
        new FileChooser();
    }

    public FileChooser() {
        JFileChooser chooseDialog = new JFileChooser();
        chooseDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooseDialog.setDialogTitle("选择文件");
        chooseDialog.showOpenDialog(null);
        File file = chooseDialog.getSelectedFile();
        if (file == null) {
            return;
        }
        if (file.exists() && file.isDirectory()) {
            System.out.println(TAG + "isDirectory: " + file.getAbsolutePath());
            filepath = file.getAbsolutePath();
        } else if (file.isFile()) {
            System.out.println(TAG + " isFile: " + file.getAbsolutePath());
            filepath = file.getAbsolutePath();
        }
        System.out.println(chooseDialog.getSelectedFile().getName());
        filename = chooseDialog.getSelectedFile().getName();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
