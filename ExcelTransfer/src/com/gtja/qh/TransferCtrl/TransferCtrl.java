/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtja.qh.TransferCtrl;

import com.gtja.qh.View.Frame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
       JFileChooser chooser = new JFileChooser();
       chooser.setDialogTitle("请选择导出目录");
       chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
       int returnVal = chooser.showOpenDialog(frame);
       if(returnVal == JFileChooser.APPROVE_OPTION) {
       String outPath = chooser.getSelectedFile().getAbsolutePath();
//           System.out.println(outPath);
       String fileName = "0004_00000001_" + frame.getDate() + "_DailyFundChg";
       }
     }
}
