package com.trans_array;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Ya on 2018/1/6.
 */
public class TransArray
{
    private HashMap<String,String> t_XmlMap;
    private HashMap<String,ArrayList<String>> s_XmlMap;
    private int count=0;
    private File t_xmlDoc =null;
    private File s_xmlDoc =null;
    private String t_xmlPath ="res/middle.xml";
    private String s_xmlPath;
    private String xlsxPath;
    private String SHEET_NAME="";
    private int ENG_COL_NUM=1;
    private int ITEM_COL_NUM=5;
    private static final String ROOT_ELEMENTS="resources";
    private static final String SUB_ELEMENTS="string-array";
    private static final String ITEM_ELEMENTS="item";
    public TransArray(String xlsxPath, String xmlPath,String sheetName,int engColNum,int itemColNum){
        this.s_xmlPath =xmlPath;
        this.xlsxPath=xlsxPath;
        this.SHEET_NAME=sheetName;
        this.ENG_COL_NUM=engColNum;
        this.ITEM_COL_NUM=itemColNum;
        t_XmlMap =new HashMap<String,String>();
        s_XmlMap =new HashMap<String,ArrayList<String>>();
        t_xmlDoc =new File("res/middle.xml");
        s_xmlDoc =new File(s_xmlPath);
        if (loadexcel()==0){
            //generateT_XMLDoc(t_XmlMap,t_xmlPath);
        }else{
            System.out.println("Stop With Error!");
        }
        readXML();
        mergeXmlMap();
        generateXMLDoc(s_XmlMap,s_xmlPath);
    }

    private void mergeXmlMap()
    {
        Set<String> s_keySet=s_XmlMap.keySet();
        for (String key:s_keySet){
            System.out.println("key is "+key);
            ArrayList<String> itemList=s_XmlMap.get(key);
            for (int i=0;i<itemList.size();i++)
            {
                String itemValue=itemList.get(i);
                for (String eng_name : t_XmlMap.keySet())
                {
                    if (t_XmlMap.get(eng_name).equals(itemValue)&& !t_XmlMap.get(eng_name).equals("xx"))
                    {
                        itemList.set(i,t_XmlMap.get(eng_name));
                        count++;
                        System.out.println("translate "+eng_name+" to ["+t_XmlMap.get(eng_name)+"]");
                    }
                }
            }
            System.out.println("Total change number is ["+count+"]");
            s_XmlMap.put(key,itemList);
        }
    }

    private void readXML()
    {
        SAXReader reader = new SAXReader();
        try
        {
            Document  s_document = reader.read(new File(s_xmlPath));
            Element s_rootElement=s_document.getRootElement();
            List<Element> arrays=s_rootElement.elements(SUB_ELEMENTS);
            System.out.println("size of arrays "+arrays.size());
            for (Element element:arrays){
                List<Element> itemEleList=element.elements(ITEM_ELEMENTS);
                for (Element itemEle:itemEleList){
                    generateXmlMap(s_XmlMap,element.attributeValue("name"),itemEle.getText());
                }
            }
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
    }

    private void generateXMLDoc(HashMap<String,ArrayList<String>> map,String filePath)
    {
        Element resources = DocumentHelper.createElement(ROOT_ELEMENTS);
        Document document = DocumentHelper.createDocument(resources);
        Set<String> keySet= map.keySet();
        for(String key:keySet){
            Element array=DocumentHelper.createElement(SUB_ELEMENTS);
            ArrayList<String> itemList;
            array.addAttribute("name",key);
            itemList= map.get(key);
            for (String itemValue:itemList){
                Element itemElement=DocumentHelper.createElement(ITEM_ELEMENTS);
                itemElement.addText(itemValue);
                array.add(itemElement);
            }
            resources.add(array);
        }
        try
        {
            writer(document,filePath);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //load xml
    private int loadexcel()
    {
        Workbook wb=readExcel(this.xlsxPath);
        Row curRow=null;
        Sheet st=wb.getSheet(SHEET_NAME);
        if (st==null){
            System.out.println("No Sheet named '"+SHEET_NAME+"' !! Please check!");
            return -1;
        }
        int rownum = st.getPhysicalNumberOfRows();
        System.out.println("name  is "+st.getSheetName());
        System.out.println("rownum is "+rownum);
        for (int i=1;i<rownum;i++){
            curRow=st.getRow(i);
            String eng_name=curRow.getCell(ENG_COL_NUM).getStringCellValue();
            String trans_value_type=curRow.getCell(ITEM_COL_NUM).getCellFormula();
            String trans_value="";
            if (trans_value_type.equals("NUMERIC")){
                double a=curRow.getCell(ITEM_COL_NUM).getNumericCellValue();
                trans_value=Double.toString(a);
            }else if(trans_value_type.equals("STRING")){
                trans_value=curRow.getCell(ITEM_COL_NUM).getStringCellValue();
            }
            System.out.println(eng_name+"  "+trans_value);
            t_XmlMap.put(eng_name,trans_value);
        }
        System.out.println("xmlmap size is "+ t_XmlMap.size());
        return 0;
    }

    private HashMap<String,ArrayList<String>> generateXmlMap(HashMap<String,ArrayList<String>> map,String array_name, String item_value)
    {
        ArrayList<String> itemList= map.get(array_name);
        if (itemList==null){
            itemList=new ArrayList<String>();
        }
        itemList.add(item_value);
        map.put(array_name,itemList);
        return map;
    }

    //read excel
    public Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public void writer(Document document, String writePath) throws Exception {

//        // 创建XMLWriter对象,指定了写出文件及编码格式

        OutputFormat format = OutputFormat.createPrettyPrint();
        // 设置换行
        format.setNewlines(true);
//        // 设置编码
        format.setEncoding("UTF-8");
//        // 生成缩进
        format.setIndent(true);
//        // 使用4个空格进行缩进, 可以兼容文本编辑器
        format.setIndent("    ");
        XMLWriter xmlWriter = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(writePath))),format);
        xmlWriter.write(document);
        xmlWriter.flush();
        xmlWriter.close();
    }
    public void printTotal(){
        System.out.println(this.SHEET_NAME+" has translate "+count);
    }
}