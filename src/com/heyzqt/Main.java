package com.heyzqt;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;

public class Main {

    /**
     * The number of statistics modified
     */
    private static int CHANGED_NUM = 0;
    private static int UNCHANGED_NUM = 0;
    private static int ADD_NUM = 0;

    private static String FILE_STANDARD_PATH = "./src/origin_keys";

    private final static String WRITE_FILE_PATH = "E:\\res";

    private static String FILE_STANDARD_NAME = "";

    private static String WRITE_File_NAME = "";

    private final static String FILL_STRING = "xx";

    /**
     * 当前需要检查的文件名,只要文件名，不要后缀
     * 注意！！！ 文件名一定要规范！！！
     */
//    private final static String FILENAME = "mmp_strings.xml";
    private final static String FILENAME = "nav_strings";

    /**
     * 统计一共修改了多少个文件
     */
    private static int num = 0;

    private static String[] countryStr = {"values-ar", "values-bg-rBG", "values-cs", "values-da", "values-de",
            "values-el-rGR",
            "values-es", "values-fa-rIR", "values-fi", "values-fr", "values-hr", "values-hu", "values-in-rID",
            "values-it", "values-iw-rIL", "values-mn-rMN", "values-ms-rMY", "values-my-rMM", "values-nl",
            "values-no", "values-pl", "values-pt", "values-ro", "values-ru", "values-sk", "values-sl",
            "values-sq-rAL", "values-sr", "values-sv", "values-sw-rTZ", "values-ta-rIN", "values-th", "values-tr",
            "values-uk-rUA", "values-vi-rVN"};

    public static void main(String[] args) {

        new CompareTranslateFrame();

        compareXMLA2XMLB("src/vi_rVN.xml", "src/strings.xml", "src/strings.xml");

//        String name = findXMLFile("menu_strings.xml", "thr_menu_strings.xml");
//        System.out.println("name = " + name);
        //startCompare("E:\\origin_keys", WRITE_FILE_PATH);

        //compareXMLA2XMLBArray("src/test1.xml", "src/test2.xml", "src/test2.xml");

        //找到标准的35国xml文件
//        File file = new File(FILE_STANDARD_PATH);
//        File[] standardFiles = file.listFiles();
//        for (int i = 0; i < standardFiles.length; i++) {
//            if (standardFiles[i].isFile()) {
//                FILE_STANDARD_NAME = standardFiles[i].getName();
//                System.out.println("i = " + i + " standard file name = " + FILE_STANDARD_NAME);
//            }
//        }
//        //找到要修改的35国xml文件
//        File writeFile = new File(WRITE_FILE_PATH);      //该文件夹下不止35国，需要进行筛选
//        File[] writeFiles = writeFile.listFiles();      // res下所有文件
//        int flag = 0;       //标志位，节约扫描时间
//        for (int i = 0; i < countryStr.length; i++) {
//            String standardXML = standardFiles[i].getName();
//
//            boolean isFindDirectory = false;
//            for (int j = 0; j < writeFiles.length; j++) {
//                if (isFindDirectory) {
//                    break;
//                }
//
//                //find directory
//                if (countryStr[i].equals(writeFiles[j].getName())) {
//                    //flag = j;
//
//                    isFindDirectory = true;
//                    File findFile = new File(WRITE_FILE_PATH + "\\" + countryStr[i]);
//                    File[] findFiles = findFile.listFiles();
//                    //System.out.println("country name = " + countryStr[i]);
//
//                    //find xml file
//                    for (int k = 0; k < findFiles.length; k++) {
//                        String temp = findFiles[k].getName();
//
//                        WRITE_File_NAME = findXMLFile(FILENAME, temp);
//
//                        if (!WRITE_File_NAME.equals("")) {
//                            System.out.println("i = " + i + ",WRITE_File_NAME = " + WRITE_File_NAME);
//                            // main step
//                            compareXMLA2XMLB(FILE_STANDARD_PATH + "/" + standardXML,
//                                    WRITE_FILE_PATH + "\\" + countryStr[i] + "\\" + WRITE_File_NAME,
//                                    WRITE_FILE_PATH + "\\" + countryStr[i] + "\\" + WRITE_File_NAME);
////                            compareXMLA2XMLBArray(FILE_STANDARD_PATH + "/" + standardXML,
////                                    WRITE_FILE_PATH + "\\" + countryStr[i] + "\\" + WRITE_File_NAME,
////                                    WRITE_FILE_PATH + "\\" + countryStr[i] + "\\" + WRITE_File_NAME, 5);
//                            break;
//                        }
//                    }
//                }
//            }
//        }
    }

    public static void startCompare(String standardfilepath, String comparefilepath) {
        long startTime = System.currentTimeMillis();

        //找到标准的xml文件
        File file = new File(standardfilepath);
        File[] standardFiles = file.listFiles();
        int standardLength = standardFiles.length;
        //print standard files name
        for (int i = 0; i < standardLength; i++) {
            if (standardFiles[i].isFile()) {
                String temp = standardFiles[i].getName();
                //CompareTranslateFrame.showLog("i = " + i + " standard file name = " + temp);
                //System.out.println("i = " + i + " standard file name = " + temp);
            }
        }

        //find file type and countries name
        String filetype;
        String[] countryDirectories = new String[standardLength];
        String firstFileName = standardFiles[0].getName();
        //find file type
        firstFileName = firstFileName.replace(".xml", "");
        int startIndex = firstFileName.indexOf("strings");
        int endIndex = startIndex + "strings".length();
        filetype = firstFileName.substring(0, endIndex);
        System.out.println("filetype = " + filetype);
        CompareTranslateFrame.showLog("filetype = " + filetype);

        //find countries name
        for (int i = 0; i < standardLength; i++) {
            String temp = standardFiles[i].getName();
            temp = temp.replace(".xml", "");
            temp = temp.replace(filetype + "_", "values-");
            temp = temp.replace("_", "-");
            countryDirectories[i] = temp;
            System.out.println("num = " + (i + 1) + " country = " + countryDirectories[i]);
            CompareTranslateFrame.showLog("num = " + (i + 1) + " country = " + countryDirectories[i]);
        }
        System.out.println();
        CompareTranslateFrame.showLog("");

        if (countryDirectories.length == 0) {
            System.out.println("error!!!Compare country directories is null.");
            CompareTranslateFrame.showLog("error!!!Compare country directories is null.");
            return;
        }

        //找到要修改的xml文件
        File writeFile = new File(comparefilepath);      //该文件夹下不止35国，需要进行筛选
        File[] writeFiles = writeFile.listFiles();      // res下所有文件
        for (int i = 0; i < countryDirectories.length; i++) {
            String standardXML = standardFiles[i].getName();

            for (int j = 0; j < writeFiles.length; j++) {
                //find directory
                if (countryDirectories[i].equals(writeFiles[j].getName())) {
                    File findFile = new File(comparefilepath + "\\" + countryDirectories[i]);
                    File[] findFiles = findFile.listFiles();
                    //System.out.println("country name = " + countryDirectories[i]);

                    if (findFiles == null || findFiles.length == 0) {
                        System.out.println("Error!!!Compare file is empty.");
                        CompareTranslateFrame.showLog("Error!!!Compare file is empty.");
                        return;
                    }

                    //find xml file
                    for (int k = 0; k < findFiles.length; k++) {
                        String temp = findFiles[k].getName();
                        if (temp.indexOf("arrays") != -1) {
                            continue;
                        }

                        String compareFileName = findXMLFile(filetype, temp);
                        if (!compareFileName.equals("")) {
                            //System.out.println("i = " + (i + 1) + ",compare file name = " + compareFileName);
                            // main step
                            // compare XMLA with XMLB
                            compareXMLA2XMLB(standardfilepath + "/" + standardXML,
                                    comparefilepath + "\\" + countryDirectories[i] + "\\" + compareFileName,
                                    comparefilepath + "\\" + countryDirectories[i] + "\\" + compareFileName);
                            break;
                        }
                    }
                    //find directory and break
                    break;
                }
            }
        }

        //print the program run time
        System.out.println("程序运行时间：" + (System.currentTimeMillis() - startTime) + "ms");
        CompareTranslateFrame.showLog("程序运行时间：" + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static String findXMLFile(String findFile, String temp) {
        String result = "";
        if (findFile.equals("menu_strings.xml")) {
            if (temp.indexOf("menu_strings.xml") != -1 && temp.indexOf("thr_menu_strings.xml") == -1) {
                //menu_strings.xml
                result = temp;
            } else if (temp.indexOf("menu_strings.xml") != -1 && temp.indexOf("thr_menu_strings.xml") != -1) {
                //thr_menu_strings.xml
                result = temp;
            }
        } else {
            if (temp.indexOf(findFile) != -1) {
                result = temp;
            }
        }
        return result;
    }


    private static void compareXMLA2XMLB(String standardFilePath, String readFilePath, String writeFilePath) {
        num++;
        System.out.println("num " + num);
        System.out.println("readfile path = " + standardFilePath);
        System.out.println("writefile path = " + writeFilePath);
        CompareTranslateFrame.showLog("num " + num);
        CompareTranslateFrame.showLog("readfile path = " + standardFilePath);
        CompareTranslateFrame.showLog("writefile path = " + writeFilePath);
        // 创建saxReader对象
        SAXReader reader = new SAXReader();
        // 通过read方法读取一个文件 转换成Document对象
        Document document1 = null;
        Document document2 = null;
        try {
            document1 = reader.read(new File(standardFilePath));
            document2 = reader.read(new File(readFilePath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        CHANGED_NUM = 0;
        ADD_NUM = 0;
        UNCHANGED_NUM = 0;

        //compare 2 "xml" files
        Element root1 = document1.getRootElement();
        Element root2 = document2.getRootElement();
        Iterator iterator1 = root1.elementIterator();
        Iterator iterator2;
        boolean isExisted;
        boolean isChanged;
        while (iterator1.hasNext()) {
            Element tempEle1 = (Element) iterator1.next();
            Attribute attribute1 = tempEle1.attribute("name");
            String key1 = attribute1.getValue();
            isExisted = false;
            isChanged = false;

            // if meet "xx" continue
            if (key1.equals(FILL_STRING))
                continue;

            if (key1.equals(""))
                continue;

            //System.out.println("11111111111");
            iterator2 = root2.elementIterator();

            while (iterator2.hasNext()) {
                Element tempEle2 = (Element) iterator2.next();
                Attribute attribute2 = tempEle2.attribute("name");
                String key2 = attribute2.getValue();
                //System.out.println("key1 = " + key1 + ",key2 = " + key2);

                // find the same key
                if (key1.equals(key2)) {
                    isExisted = true;
                    String value1 = tempEle1.getText();
                    //System.out.println("value1 = " + value1 + ",value2 = " + tempEle2.getText());
                    // compare the value
                    if(value1.equals("xx")){
                        continue;
                    }
                    if (!value1.equals(tempEle2.getText())) {
                        tempEle2.setText(value1.toString());
                        CHANGED_NUM++;
                        //System.out.println("changed " + CHANGED_NUM + " tempEle2 = " + tempEle2.getText());
                        System.out.println("changed " + CHANGED_NUM + " key = " + key1);
                        CompareTranslateFrame.showLog("changed " + CHANGED_NUM + " key = " + key1);
                        isChanged = true;
                        break;
                    }
                }
            }

            // if the key doesn't existed, we should add it
            if (!isExisted) {
                Element newElement = root2.addElement("string");
                newElement.addAttribute("name", key1);
                newElement.setText(tempEle1.getText());
                ADD_NUM++;
                isChanged = true;
                System.out.println("Add " + ADD_NUM + " key = " + key1);
                CompareTranslateFrame.showLog("Add " + ADD_NUM + " key = " + key1);
            }

            if (!isChanged) {
                UNCHANGED_NUM++;
                System.out.println("unchanged key = " + key1);
                CompareTranslateFrame.showLog("unchanged key = " + key1);
            }
        }

        // write to a new file
        try {
            writer(document2, writeFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("changed count = " + CHANGED_NUM + ", add count = " + ADD_NUM + ", unchanged count = " +
                UNCHANGED_NUM + ",result = " +
                (CHANGED_NUM + UNCHANGED_NUM + ADD_NUM));
        System.out.println("write end\n");
        CompareTranslateFrame.showLog("changed count = " + CHANGED_NUM + ", add count = " + ADD_NUM + ", unchanged " +
                "count = " +
                UNCHANGED_NUM + ",result = " +
                (CHANGED_NUM + UNCHANGED_NUM + ADD_NUM));
        CompareTranslateFrame.showLog("write end\n");
    }


    private static void compareXMLA2XMLBArray(String standardFilePath, String readFilePath,
                                              String writeFilePath, int rowIndex) {
        rowIndex -= 1;
        num++;
        System.out.println("num " + num);
        System.out.println("readfile path = " + standardFilePath);
        System.out.println("writefile path = " + writeFilePath);
        // 创建saxReader对象
        SAXReader reader = new SAXReader();
        // 通过read方法读取一个文件 转换成Document对象
        Document document1 = null;
        Document document2 = null;
        try {
            document1 = reader.read(new File(standardFilePath));
            document2 = reader.read(new File(readFilePath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        CHANGED_NUM = 0;
        ADD_NUM = 0;
        UNCHANGED_NUM = 0;

        //compare 2 "xml" files
        Element root1 = document1.getRootElement();
        Element root2 = document2.getRootElement();
        Iterator iterator1 = root1.elementIterator();
        Iterator iterator2;
        boolean isExisted;
        boolean isChanged;
        while (iterator1.hasNext()) {
            Element stringarray1 = (Element) iterator1.next();
            Attribute attribute1 = stringarray1.attribute("name");
            String key1 = attribute1.getValue();
            isExisted = false;
            isChanged = false;

            // if meet "xx" continue
            if (key1.equals(FILL_STRING))
                continue;

            if (key1.equals(""))
                continue;

            //System.out.println("11111111111");
            iterator2 = root2.elementIterator();

            Element stringarray2 = null;
            while (iterator2.hasNext()) {
                stringarray2 = (Element) iterator2.next();
                Attribute attribute2 = stringarray2.attribute("name");
                String key2 = attribute2.getValue();
                //System.out.println("key1 = " + key1 + ",key2 = " + key2);

                // find the same key
                if (key1.equals(key2)) {
                    isExisted = true;

                    //find same item
                    String standardValue = stringarray1.element("item").getText();
                    Iterator iterator3 = stringarray2.elementIterator("item");
                    int z = 0;
                    while (iterator3.hasNext()) {
                        Element item = (Element) iterator3.next();
                        if (z == rowIndex) {
                            //System.out.println("value1 = " + value1 + ",value2 = " + tempEle2.getText());
                            // compare the value
                            if (!standardValue.equals(item.getText())) {
                                System.out.print("z = " + z + ", standardvalue = " + standardValue + ",  value1 = " +
                                        item.getText());
                                item.setText(standardValue);
                                System.out.println(", value2 = " + item.getText());
                                CHANGED_NUM++;
                                System.out.println("changed " + CHANGED_NUM + " key = " + key1);
                                isChanged = true;
                                break;
                            }
                            break;
                        }
                        z++;
                    }
                }
            }

            // if the key doesn't existed, we should add it
            if (!isExisted) {
                Element newElement = stringarray2.addElement("item");
                //newElement.addAttribute("name", key1);
                newElement.setText(stringarray1.element("item").getText());
                ADD_NUM++;
                isChanged = true;
                System.out.println("Add " + ADD_NUM + " key = " + key1);
            }

            if (!isChanged) {
                UNCHANGED_NUM++;
                System.out.println("unchanged key = " + key1);
            }
        }

        // 写入到一个新的文件中
        try {
            writer(document2, writeFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("changed count = " + CHANGED_NUM + ", add count = " + ADD_NUM + ", unchanged count = " +
                UNCHANGED_NUM + ",result = " +
                (CHANGED_NUM + UNCHANGED_NUM + ADD_NUM));
        System.out.println("wirte end\n");
    }

    /**
     * 把document对象写入新的文件
     *
     * @param document
     * @throws Exception
     */
    public static void writer(Document document, String writePath) throws Exception {

//        // 创建XMLWriter对象,指定了写出文件及编码格式

//        OutputFormat format = OutputFormat.createPrettyPrint();
//        OutputFormat format = new OutputFormat();
        // 设置换行
//        format.setNewlines(true);
//        // 设置编码
//        format.setEncoding("UTF-8");
//        // 生成缩进
//        format.setIndent(true);
//        // 使用4个空格进行缩进, 可以兼容文本编辑器
//        format.setIndent("    ");


//        XMLWriter xmlWriter = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(writePath))),
//                format);

        XMLWriter xmlWriter = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(writePath))));

        xmlWriter.write(document);
        xmlWriter.flush();
        xmlWriter.close();
    }
}
