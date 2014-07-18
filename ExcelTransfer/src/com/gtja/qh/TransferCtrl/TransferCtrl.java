/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtja.qh.TransferCtrl;

import com.gtja.qh.View.Frame;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.CellType;
import jxl.NumberCell;
import org.apache.poi.ss.usermodel.*;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 王南图
 */
public class TransferCtrl {
    private Frame frame;

    public TransferCtrl() {
        frame = new Frame();
        addComponentListeners();
    }
    
    public void addComponentListeners(){
        addSelectFileButtonListener();
        addGenerateButtonListener();
    }

    private void addSelectFileButtonListener() {
       frame.getSelectFileButton().addActionListener(new java.awt.event.ActionListener() {
                   public void actionPerformed(java.awt.event.ActionEvent evt) {
                   selectFileActionPerformed(evt);
             }
          });
    }
    private void selectFileActionPerformed(java.awt.event.ActionEvent evt) {                                         
       JFileChooser chooser = new JFileChooser();
       FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Excel文件", "xls", "xlsx","csv");
       chooser.setFileFilter(filter); //  过滤器可以不要
       int returnVal = chooser.showOpenDialog(frame);
       if(returnVal == JFileChooser.APPROVE_OPTION) {
           String inputFilePath = chooser.getSelectedFile().getAbsolutePath();
           frame.getFilePath().setText(chooser.getSelectedFile().getAbsolutePath());
           File inputFile = new File(inputFilePath);
           String inputFileName = inputFile.getName();
           String extension = inputFileName.lastIndexOf(".")==-1?"":inputFileName.substring(inputFileName.lastIndexOf(".")+1);
           if(!("xls".equals(extension) || "xlsx".equals(extension))){
               JOptionPane.showMessageDialog(null, "不支持的文件格式", "注意", JOptionPane.ERROR_MESSAGE);
                frame.getFilePath().setText("");
           }
       }
       

      }  

    private void addGenerateButtonListener() {
        frame.getGenerateButton().addActionListener(new java.awt.event.ActionListener() {
                   public void actionPerformed(java.awt.event.ActionEvent evt) {
                       try {
                           generateButtonActionPerformed(evt);
                       } catch (DBFException ex) {
                           ex.printStackTrace();
                       }
             }
          });
    }
    
     private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) throws DBFException { 
        String inputFilePath = frame.getFilePath().getText();
      if(inputFilePath.length() != 0 ){ 
        String date = frame.getDate().getText();
        String fileName;
        if(!(frame.getTxtCB().isSelected()||frame.getDbfCB().isSelected())){
             JOptionPane.showMessageDialog(null, "请至少选择一种文件类型", "注意", JOptionPane.ERROR_MESSAGE);
        }else{
            if(isValidDate(date)){
                 fileName = "0004_00000001_" + date + "_DailyFundChg";
                JFileChooser chooser = new JFileChooser();
                 chooser.setDialogTitle("请选择导出目录");
                 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                 int returnVal = chooser.showOpenDialog(frame);
                 if(returnVal == JFileChooser.APPROVE_OPTION) {
                    String outFileDir = chooser.getSelectedFile().getAbsolutePath();
                    Boolean t = false;
                    Boolean d = false;
                    if(frame.getTxtCB().isSelected()){
                        t = transferToTxt(inputFilePath,fileName,outFileDir);
                        if(!t){
                            if(frame.getDbfCB().isSelected()){
                               d = transferToDbf(inputFilePath,fileName,outFileDir);   
                                if(!d){
                                JOptionPane.showMessageDialog(null, "文件生成失败", "注意", JOptionPane.ERROR_MESSAGE);
                                }else{
                                JOptionPane.showMessageDialog(null, "Txt文件生成失败，Dbf文件生成成功", "注意", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }else{
                        if(frame.getDbfCB().isSelected()){
                         d = transferToDbf(inputFilePath,fileName,outFileDir); 
                            
                            if(!d){
                                JOptionPane.showMessageDialog(null, "Txt文件生成成功，Dbf文件生成失败", "注意", JOptionPane.ERROR_MESSAGE);
                            }else{
                                JOptionPane.showMessageDialog(null, "文件已生成", "注意", JOptionPane.PLAIN_MESSAGE);
                            }
                    }else if(!frame.getDbfCB().isSelected()){
                            JOptionPane.showMessageDialog(null, "Txt文件已生成", "注意", JOptionPane.PLAIN_MESSAGE);
                    }
                   }
                 }else{
                         d = transferToDbf(inputFilePath,fileName,outFileDir);
                 
                         if(!d){
                                JOptionPane.showMessageDialog(null, "Dbf文件生成失败", "注意", JOptionPane.ERROR_MESSAGE);
                            }else{
                                JOptionPane.showMessageDialog(null, "Dbf文件已生成", "注意", JOptionPane.PLAIN_MESSAGE);
                            }
                    }
            }   
        }else{
           JOptionPane.showMessageDialog(null, "请输入有效日期", "注意", JOptionPane.ERROR_MESSAGE);
            }
     
        }
       }else{
          JOptionPane.showMessageDialog(null, "请选择文件", "注意", JOptionPane.ERROR_MESSAGE);
      }
    }
     
     private boolean isValidDate(String s){
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
       dateFormat.setLenient(false);
        try{
            dateFormat.parse(s);
            return true;
        }catch(Exception e){
            return false;
        }
     }
     
     private boolean transferToTxt(String inputFilePath,String fileName,String outFileDir){
         File inputFile = new File(inputFilePath);
         String inputFileName = inputFile.getName();
         String extension = inputFileName.lastIndexOf(".")==-1?"":inputFileName.substring(inputFileName.lastIndexOf(".")+1); 
          StringBuffer input = null;
         if("xls".equals(extension)){
              //JXL读取excel 2003文件，暂不支持xlsx格式
         try {
            //读取excel文件
            InputStream is = new FileInputStream(inputFilePath);
            jxl.Workbook rwb = jxl.Workbook.getWorkbook(is);
            jxl.Sheet rs = rwb.getSheet(0);
            int rsRows= rs.getRows();
            input = new StringBuffer();
            for( int i=1; i < rsRows; i++){
                 String line  = "A999@" + rs.getCell(4, i).getContents() + "@" + rs.getCell(6,i).getContents() + "\r\n";
                 input.append(line);
            }
         }catch (Exception e) {
            e.printStackTrace(); 
            return false;
         }
        }else if("xlsx".equals(extension)){
             //POI 读取excel 2007文件,不支持excel 2003
         try {
            InputStream fs = new FileInputStream(inputFilePath);
            XSSFWorkbook wb;
             wb = new XSSFWorkbook(fs);
             XSSFSheet sheet = wb.getSheetAt(0);
             int rows = sheet.getPhysicalNumberOfRows();
             input = new StringBuffer();
             for(int i = 1;i<rows;i++){
                 Row row = sheet.getRow(i);
                 if(row == null){
                     continue;
                 }
                 if(row.getCell(4) == null){
                     row.createCell(4);
                     row.getCell(4).setCellValue("");
                 }
                 if(row.getCell(6) == null){
                     row.createCell(6);
                     row.getCell(6).setCellValue("");
                 }

                 String tradeCode = row.getCell(4).getStringCellValue();
                 Double amount = null;
                 if(row.getCell(6).getCellType() == CELL_TYPE_NUMERIC){
                     amount = row.getCell(6).getNumericCellValue();
                 }else{
                     if(row.getCell(6).getStringCellValue().length() == 0){
                         amount = null;
                     }else{
                     amount =new DecimalFormat("0.00").parse(row.getCell(6).getStringCellValue()).doubleValue();  //将String转换为Double 
                     }
                 }
                 String line;
                 if(amount == null){
                     line = "A999@" + tradeCode + "@" + "\r\n";
                 }else{
                     line = "A999@" + tradeCode + "@" + amount + "\r\n";
                 }
                 input.append(line);
             }
         }catch (Exception e) {
             e.printStackTrace(); 
             return false;
         }
        }
         
         try {
            //创建输出文件
            String outFile = outFileDir + "\\" + fileName + ".txt";
            File file = new File(outFile);
            if (!file.exists()) {
                file.createNewFile();
            }
           //写入输出文件
            OutputStream os = new FileOutputStream(file);
            os.write(input.toString().getBytes());
            os.flush();
            os.close();
            return true;
            
        } catch (Exception e) {
           e.printStackTrace(); 
           return false;
        }
         
     }
     
     private boolean transferToDbf(String inputFilePath,String fileName,String outFileDir) throws DBFException {
        
      //定义DBF文件字段,字段长度不确定
        DBFField[] fields = new DBFField[6];
             
        fields[0] = new DBFField();
        fields[0].setName("ACCOUNTID");
        fields[0].setDataType(DBFField.FIELD_TYPE_C);
        fields[0].setFieldLength(6);
        
        fields[1] = new DBFField();
        fields[1].setName("PARTID");
        fields[1].setDataType(DBFField.FIELD_TYPE_C);
        fields[1].setFieldLength(4);
             
        fields[2] = new DBFField();
        fields[2].setName("CLIENTID");
        fields[2].setDataType(DBFField.FIELD_TYPE_C);
        fields[2].setFieldLength(8);
             
        fields[3] = new DBFField();
        fields[3].setName("AMOUNT");
        fields[3].setDataType(DBFField.FIELD_TYPE_N);
        fields[3].setFieldLength(12);                         
        fields[3].setDecimalCount(2);
             
        fields[4] = new DBFField();
        fields[4].setName("MONEYTYPE");
        fields[4].setDataType(DBFField.FIELD_TYPE_C);
        fields[4].setFieldLength(4);
             
        fields[5] = new DBFField();
        fields[5].setName("TYPEMEMO");
        fields[5].setDataType(DBFField.FIELD_TYPE_C);
        fields[5].setFieldLength(20);
        DBFWriter writer = new DBFWriter();
        try{
         writer.setFields(fields);
        }catch(Exception e){
           e.printStackTrace();
           return false;
        }
         //从excel中读取数据
         File inputFile = new File(inputFilePath);
         String inputFileName = inputFile.getName();
         String extension = inputFileName.lastIndexOf(".")==-1?"":inputFileName.substring(inputFileName.lastIndexOf(".")+1); 
         if("xls".equals(extension)){
              //JXL读取excel 2003文件，暂不支持xlsx格式
            try {
             //读取excel文件
             InputStream is = new FileInputStream(inputFilePath);
             jxl.Workbook rwb = jxl.Workbook.getWorkbook(is);
             jxl.Sheet rs = rwb.getSheet(0);
             int rsRows= rs.getRows();
             for( int i=1; i < rsRows; i++){
                 Object[] rowData = new Object[6];
                 rowData[0] = "000101";
                 rowData[1] = "0001";
                 rowData[4] = "A999";
                 String tradeCode = rs.getCell(4, i).getContents();
                 Double amount = null;
                 if(rs.getCell(6, i).getType() == CellType.NUMBER){
                     NumberCell numberCell = (NumberCell)rs.getCell(6, i); 
                     amount =numberCell.getValue();
                 }else{
                     if(rs.getCell(6,i).getContents().length() == 0){
                         amount = null;
                     }else{
                         amount =new DecimalFormat("0.00").parse(rs.getCell(6,i).getContents()).doubleValue();    //将String转换为Double 
                     }
                    
                 }
                 String typeMemo = rs.getCell(7, i).getContents();
                 rowData[2] = tradeCode;
                 rowData[3] = amount;
                 rowData[5] = typeMemo;
                 writer.addRecord(rowData); 
             }
          }catch (Exception e) {
             e.printStackTrace(); 
             return false;
          }
         }else if("xlsx".equals(extension)){
             //POI 读取excel 2007文件,不支持excel 2003
            try {
               InputStream fs = new FileInputStream(inputFilePath);
               XSSFWorkbook wb;
                wb = new XSSFWorkbook(fs);
                //wb = new XSSFWorkbook(inputFilePath);
                XSSFSheet sheet = wb.getSheetAt(0);
                int rows = sheet.getPhysicalNumberOfRows();
                for(int i = 1;i<rows;i++){
                    Object[] rowData = new Object[6];
                    rowData[0] = "000101";
                    rowData[1] = "0001";
                    rowData[4] = "A999";
                    Row row = sheet.getRow(i);
                    if(row == null){
                        continue;
                    }
                    if(row.getCell(4) == null){
                        row.createCell(4);
                        row.getCell(4).setCellValue("");
                    }
                    if(row.getCell(6) == null){
                        row.createCell(6);
                        row.getCell(6).setCellValue("");
                    }
                    if(row.getCell(7) == null){
                        row.createCell(7);
                        row.getCell(7).setCellValue("");
                    }
//                    row.getCell(4).setCellType(CELL_TYPE_STRING );
//                    row.getCell(6).setCellType(CELL_TYPE_NUMERIC );
//                    row.getCell(7).setCellType(CELL_TYPE_STRING);
                    String tradeCode = row.getCell(4).getStringCellValue();
                    Double amount = null;
                    if(row.getCell(6).getCellType() == CELL_TYPE_NUMERIC){
                         amount = row.getCell(6).getNumericCellValue();
                    }else{
                        row.getCell(6).setCellType(CELL_TYPE_STRING);
                        if(row.getCell(6).getStringCellValue().length() == 0){
                            amount = null;
                        }else{
                        amount = new DecimalFormat("0.00").parse(row.getCell(6).getStringCellValue()).doubleValue();
                        }
                    }
                    String typeMemo = row.getCell(7).getStringCellValue();
                    rowData[2] = tradeCode;
                    rowData[3] = amount;
                    rowData[5] = typeMemo;
                    writer.addRecord(rowData);
                }
            }catch (Exception e) {
                e.printStackTrace(); 
                return false;
            }
         }
         
        try{        
              //创建输出文件
            String outFile = outFileDir + "\\" + fileName + ".dbf";
            File file = new File(outFile);
            if (!file.exists()) {
                file.createNewFile();
            }
             OutputStream os = new FileOutputStream(file);

             //写入数据 
            writer.write(os); 
            os.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }     
     }
}