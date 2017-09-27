package com.heyzqt;

import com.widget.DefaultFont;
import com.widget.ToolFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private static JTextArea mLogArea;
    private JButton mClearBtn;

    private static final String STANDARD_FILE_PATH = "E:\\origin_keys";
    private static String COMPARE_FILE_PATH = "";

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
        mFilePathLab.setText("对比的文件夹路径：");
        mFilePathLab.setBounds(400, 20, 1000, 100);

        // log panel
        mLogArea = new JTextArea();
        mLogArea.setFont(new DefaultFont());
        mLogArea.append("this is a log area");
        mLogArea.setBounds(150, 100, 1000, 450);
        mClearBtn = new JButton("clear log");
        mClearBtn.setFont(new DefaultFont());
        mClearBtn.setBounds(1030, 560, 120, 40);
        mClearBtn.addActionListener(this);


        mFrame.setSize(1280, 660);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
        mMainPanel.add(mChooseDirectoryBtn);
        mMainPanel.add(originFilePath);
        mMainPanel.add(mFilePathLab);
        mMainPanel.add(mClearBtn);
        mMainPanel.add(mLogArea);
        mFrame.add(mMainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btn = e.getActionCommand();
        if ("选择要对比的res文件夹".equals(btn)) {
            System.out.println("click");
        } else if ("clear log".equals(btn)) {
            System.out.println("clear log");
        }
    }

    public static void showLog(String msg) {
        mLogArea.append(msg + "\n");
    }

    public static void showLogInfo(String msg) {
        mLogArea.append(msg);
    }
}
