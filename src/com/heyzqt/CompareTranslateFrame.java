package com.heyzqt;

import com.widget.DefaultFont;
import com.widget.FileChooser;
import com.widget.ToolFont;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by heyzqt 9/27/2017
 */
public class CompareTranslateFrame implements ActionListener {

    private JFrame mFrame;

    private JPanel mMainPanel;

    /**
     * File panel
     */
    private JButton mChooseDirectoryBtn;
    private JLabel mFilePathLab;

    /**
     * log panel
     */
    private JScrollPane mLogPanel;
    private static JTextArea mLogArea;
    private JButton mClearBtn;

    private JButton mStartCompareBtn;

    /**
     * choose file
     */
    private FileChooser mFileChooser;

    public static final String STANDARD_FILE_PATH = "E:\\origin_keys";
    public static String COMPARE_FILE_PATH = "";

    public CompareTranslateFrame() {
        initFrame();
    }

    private void initFrame() {
        mFrame = new JFrame(Constant.FRAME_TITLE + "_" + Constant.TOOL_VERSION + "_" + Constant.TOOL_DEVELOPER);
        mMainPanel = new JPanel();
        mMainPanel.setLayout(null);

        // file panel
        mChooseDirectoryBtn = new JButton("选择要对比的res文件夹");
        mChooseDirectoryBtn.setFont(new ToolFont());
        mChooseDirectoryBtn.setBounds(150, 40, 230, 40);
        mChooseDirectoryBtn.addActionListener(this);
        JLabel originFilePath = new JLabel("标准xml文件路径：" + STANDARD_FILE_PATH);
        originFilePath.setFont(new DefaultFont());
        originFilePath.setBounds(400, 30, 500, 20);
        mFilePathLab = new JLabel();
        mFilePathLab.setFont(new DefaultFont());
        mFilePathLab.setText("对比的文件夹路径(建议放在E:\\res)：");
        mFilePathLab.setBounds(400, 20, 1000, 100);

        // log panel
        mLogArea = new JTextArea();
        mLogArea.setFont(new DefaultFont());
        mLogArea.append("this is a log area\n");
        mLogPanel = new JScrollPane(mLogArea);
        mLogPanel.setBounds(150, 100, 1000, 450);
        mClearBtn = new JButton("clear log");
        mClearBtn.setFont(new DefaultFont());
        mClearBtn.setBounds(1030, 560, 120, 40);
        mClearBtn.addActionListener(this);

        mStartCompareBtn = new JButton("start compare");
        mStartCompareBtn.setFont(new DefaultFont());
        mStartCompareBtn.setBounds(860, 560, 150, 40);
        mStartCompareBtn.addActionListener(this);

        mFrame.setSize(1280, 660);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
        mMainPanel.add(mChooseDirectoryBtn);
        mMainPanel.add(originFilePath);
        mMainPanel.add(mFilePathLab);
        mMainPanel.add(mStartCompareBtn);
        mMainPanel.add(mClearBtn);
        mMainPanel.add(mLogPanel);
        mFrame.add(mMainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btn = e.getActionCommand();
        if ("选择要对比的res文件夹".equals(btn)) {
            mFileChooser = new FileChooser();
            if (!mFileChooser.getFilepath().equals("")) {
                mFilePathLab.setText("对比的文件夹路径：" + mFileChooser.getFilepath());
            }
        } else if ("clear log".equals(btn)) {
            mLogArea.setText("");
        } else if ("start compare".equals(btn)) {
            //check if standard file path is empty
            File standardFile = new File(STANDARD_FILE_PATH);
            if (standardFile == null || !standardFile.exists() || standardFile.listFiles().length == 0) {
                showLog("Error!!! Standard file doesn't exist or is empty.");
                return;
            }

            //check if the compare file path is empty
            if (mFileChooser == null) {
                showLog("Error!!! Please choose a compare file path.");
                return;
            }
            COMPARE_FILE_PATH = mFileChooser.getFilepath();
            if (COMPARE_FILE_PATH.equals("")) {
                showLog("Error!!! Please choose a compare file path.");
                return;
            }
            File compareFile = new File(COMPARE_FILE_PATH);
            if (compareFile == null || !compareFile.exists() || !compareFile.isDirectory()) {
                showLog("Error!!! Please choose a compare res file directory.");
                return;
            }

            Main.startCompare(STANDARD_FILE_PATH, COMPARE_FILE_PATH);
        }
    }

    public static void showLog(String msg) {
        mLogArea.append(msg + "\n");
    }

    public static void showLogInfo(String msg) {
        mLogArea.append(msg);
    }
}
