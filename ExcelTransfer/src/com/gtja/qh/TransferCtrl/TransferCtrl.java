/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtja.qh.TransferCtrl;

import com.gtja.qh.View.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.*;

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
//       System.out.println("You chose to open this file: " +
//            chooser.getSelectedFile().getAbsolutePath());
       frame.getFilePath().setText(chooser.getSelectedFile().getAbsolutePath());
       }

      }  

    private void addGenerateButtonListener() {
        frame.getGenerateButton().addActionListener(new java.awt.event.ActionListener() {
                   public void actionPerformed(java.awt.event.ActionEvent evt) {
                   generateButtonActionPerformed(evt);
             }
          });
    }
    
     private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String date = frame.getDate().getText();
        String inputFilePath = frame.getFilePath().getText();
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
    //               System.out.println(outPath);
                    Boolean t = false;
                    Boolean d = false;
                    if(frame.getTxtCB().isSelected()){
                        t = transferToTxt(inputFilePath,fileName,outFileDir);
                        if(!t){
                            JOptionPane.showMessageDialog(null, "Txt文件生成失败", "注意", JOptionPane.ERROR_MESSAGE);
                        }else{
                        if(frame.getDbfCB().isSelected()){
                        // d = transferToDbf(inputFilePath,fileName,outFileDir); 
                            
                            if(!d){
                                JOptionPane.showMessageDialog(null, "Dbf文件生成失败", "注意", JOptionPane.ERROR_MESSAGE);
                            }else{
                                JOptionPane.showMessageDialog(null, "文件已生成", "注意", JOptionPane.PLAIN_MESSAGE);
                            }
                    }else if(!frame.getDbfCB().isSelected()){
                            JOptionPane.showMessageDialog(null, "Txt文件已生成", "注意", JOptionPane.PLAIN_MESSAGE);
                    }
                   }
                 }else{
                        // d = transferToDbf(inputFilePath,fileName,outFileDir);
                 
                         if(!d){
                                JOptionPane.showMessageDialog(null, "Dbf文件生成失败", "注意", JOptionPane.ERROR_MESSAGE);
                            }else{
                                JOptionPane.showMessageDialog(null, "Dbf文件已生成", "注意", JOptionPane.PLAIN_MESSAGE);
                            }
                    }
            }else{
           JOptionPane.showMessageDialog(null, "请输入有效日期", "注意", JOptionPane.ERROR_MESSAGE);
            }   
        }
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
         try {
            //创建输出文件
            String outFile = outFileDir + "\\" + fileName + ".txt";
            File file = new File(outFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            //读取excel文件
            InputStream is = new FileInputStream(inputFilePath);
            jxl.Workbook rwb = Workbook.getWorkbook(is);  
            Sheet rs = rwb.getSheet(0);
            int rsRows= rs.getRows();
            StringBuffer input = new StringBuffer();
            for( int i=1; i < rsRows; i++){
             String line  = "A999@" + rs.getCell(4, i).getContents() + "@" + rs.getCell(6,i).getContents() + "\r\n";
             input.append(line);
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
     
     private void transferToDbf(String inputFilePath,String fileName,String outFileDir){
         
     }
}
